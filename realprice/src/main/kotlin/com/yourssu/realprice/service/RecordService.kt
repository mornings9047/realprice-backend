package com.yourssu.realprice.service

import com.yourssu.realprice.repository.RecordRepository
import com.yourssu.realprice.service.function.RegisterRecordFunction
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter

@Service
class RecordService @Autowired constructor(val registerRecordFunction: RegisterRecordFunction,
                                           val recordRepository: RecordRepository) {

    fun registerRecord(keyword: String, startDay: String, endDay: String) {
        val start = LocalDate.parse(startDay, DateTimeFormatter.ISO_DATE)
        val end = LocalDate.parse(endDay, DateTimeFormatter.ISO_DATE)
        val period = Period.between(start, end)
        for (i in 0 until period.days + 1) {
            val date = start.plusDays(i.toLong()).toString()
            if (recordRepository.findByKindnameContainingAndDate(keyword, date).isPresent)
                continue
            val record = registerRecordFunction.getRecord(keyword, date)
            if (record != null)
                recordRepository.save(record)
        }
    }

}
