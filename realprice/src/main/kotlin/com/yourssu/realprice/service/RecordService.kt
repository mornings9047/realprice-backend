package com.yourssu.realprice.service

import com.yourssu.realprice.model.Record
import com.yourssu.realprice.repository.RecordRepository
import com.yourssu.realprice.service.function.RegisterRecordFunction
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class RecordService @Autowired constructor(val registerRecordFunction: RegisterRecordFunction,
                                           val recordRepository: RecordRepository) {

    fun registerRecord(keyword: String, date: String) {
        val record = registerRecordFunction.getRecord(keyword, date)
        recordRepository.save(record)
    }

}
