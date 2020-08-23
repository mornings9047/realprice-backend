package com.yourssu.realprice.service

import com.yourssu.realprice.dto.response.TodayRecordResponseDto
import com.yourssu.realprice.exception.RecordNotExistsException
import com.yourssu.realprice.repository.RecordRepository
import com.yourssu.realprice.service.function.PriceDifferenceFunction
import com.yourssu.realprice.service.function.RegisterRecordFunction
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter

@Service
class RecordService @Autowired constructor(val registerRecordFunction: RegisterRecordFunction,
                                           val priceDifferenceFunction: PriceDifferenceFunction,
                                           val recordRepository: RecordRepository) {

    fun registerRecord(keyword: String, startDay: String, endDay: String) {
        val start = LocalDate.parse(startDay, DateTimeFormatter.ISO_DATE)
        val end = LocalDate.parse(endDay, DateTimeFormatter.ISO_DATE)
        val period = Period.between(start, end)
        for (i in 0 until period.days + 1) {
            val date = start.plusDays(i.toLong()).toString()
            if (recordRepository.findByKindnameContainingAndDate(keyword, date).isPresent)
                continue
            val record = registerRecordFunction.getRecordFromApi(keyword, date)
            if (record != null)
                recordRepository.save(record)
        }
    }

    fun getTodayRecordCompareWithYesterday(keyword: String): TodayRecordResponseDto {
        // 이미 저장되어 있는 record에서 2개 꺼내서 사용할까
        val date = LocalDate.now()
        val today = LocalDate.parse(date.toString(), DateTimeFormatter.ISO_DATE).toString()
        val yesterday = LocalDate.parse(date.minusDays(1).toString(), DateTimeFormatter.ISO_DATE).toString()
        val todayRecord = recordRepository.findByKindnameContainingAndDate(keyword, today).orElseThrow { RecordNotExistsException(keyword, today) }
        val yesterdayRecord = recordRepository.findByKindnameContainingAndDate(keyword, yesterday).orElseThrow { RecordNotExistsException(keyword, yesterday) }

        return TodayRecordResponseDto(
                kindname = todayRecord.kindname,
                price = todayRecord.price,
                isExpensive = when {
                    todayRecord.price > yesterdayRecord.price -> 1
                    todayRecord.price == yesterdayRecord.price -> 0
                    else -> -1
                },
                diff = priceDifferenceFunction.getDiff(todayRecord, yesterdayRecord)
        )
    }

    fun getWeekRecords(keyword: String) {

    }

    fun getMonthRecords(keyword: String) {

    }

}
