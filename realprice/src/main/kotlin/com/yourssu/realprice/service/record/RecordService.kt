package com.yourssu.realprice.service.record

import com.yourssu.realprice.dto.response.MonthRecordsResponseDto
import com.yourssu.realprice.dto.response.TodayRecordResponseDto
import com.yourssu.realprice.dto.response.WeekRecordsResponseDto
import com.yourssu.realprice.exception.ProductNotExistsException
import com.yourssu.realprice.exception.RecordNotExistsException
import com.yourssu.realprice.model.Record
import com.yourssu.realprice.repository.ProductRepository
import com.yourssu.realprice.repository.RecordRepository
import com.yourssu.realprice.service.record.function.PriceDifferenceFunction
import com.yourssu.realprice.service.record.function.RegisterRecordFunction
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@Service
class RecordService @Autowired constructor(val registerRecordFunction: RegisterRecordFunction,
                                           val priceDifferenceFunction: PriceDifferenceFunction,
                                           val recordRepository: RecordRepository,
                                           val productRepository: ProductRepository) {
    fun registerAllRecords(startDay: String, endDay: String) {
        for (product in productRepository.findAll())
            registerRecord(product.name, startDay, endDay)
    }

    fun registerRecord(keyword: String, startDay: String, endDay: String) {
        val start = LocalDate.parse(startDay, DateTimeFormatter.ISO_DATE)
        val end = LocalDate.parse(endDay, DateTimeFormatter.ISO_DATE)
        val days = end.dayOfYear - start.dayOfYear
        for (i in 0 until days + 1) {
            val date = start.plusDays(i.toLong()).toString()
            if (recordRepository.findByKindnameContainingAndDate(keyword, date).isPresent)
                continue
            val record = registerRecordFunction.getRecordFromApi(keyword, date)
            if (record != null)
                recordRepository.save(record)
        }
    }

    fun findRecentRecords(productId: Long): List<Record> {
        return recordRepository.findTop2ByProductIdOrderByDateDesc(productId)
    }

    fun getTodayRecordCompareWithYesterday(keyword: String): TodayRecordResponseDto {
        val product = productRepository.findByName(keyword).orElseThrow { ProductNotExistsException(keyword) }
        val records = recordRepository.findTop2ByProductIdOrderByDateDesc(product.id!!)

        val today = LocalDate.parse(records[0].date, DateTimeFormatter.ISO_DATE).toString()
        val yesterday = LocalDate.parse(records[1].date, DateTimeFormatter.ISO_DATE).toString()
        val todayRecord = recordRepository.findByKindnameContainingAndDate(keyword, today).orElseThrow { RecordNotExistsException(keyword, today) }
        val yesterdayRecord = recordRepository.findByKindnameContainingAndDate(keyword, yesterday).orElseThrow { RecordNotExistsException(keyword, yesterday) }
        return TodayRecordResponseDto(
                kindname = todayRecord.kindname,
                price = todayRecord.price,
                riseOrFall = todayRecord.price >= yesterdayRecord.price,
                diff = priceDifferenceFunction.getDiff(todayRecord, yesterdayRecord)
        )
    }

    fun getWeekRecords(keyword: String): List<WeekRecordsResponseDto> {
        val product = productRepository.findByName(keyword).orElseThrow { ProductNotExistsException(keyword) }
        val weekRecords = recordRepository.findTop7ByProductIdOrderByDateDesc(product.id!!)
        return weekRecords.asReversed().map { WeekRecordsResponseDto(it) }
    }

    fun getMonthRecords(keyword: String): List<MonthRecordsResponseDto> {
        val product = productRepository.findByName(keyword).orElseThrow { ProductNotExistsException(keyword) }
        val records = recordRepository.findTop31ByProductIdOrderByDateDesc(product.id!!)
        val monthRecords = mutableListOf<Record>()
        for (i in 0 until 31 step 5)
            monthRecords.add(records[i])
        return monthRecords.asReversed().map { MonthRecordsResponseDto(it) }
    }
}
