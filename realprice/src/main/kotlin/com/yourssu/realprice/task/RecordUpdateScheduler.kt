package com.yourssu.realprice.task

import com.yourssu.realprice.repository.ProductRepository
import com.yourssu.realprice.service.RecordService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.scheduling.quartz.SchedulerFactoryBean
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Component
class RecordUpdateScheduler @Autowired constructor(val schedulerFactoryBean: SchedulerFactoryBean,
                                                   val productRepository: ProductRepository,
                                                   val recordService: RecordService) {

    @Scheduled(cron = "0 0/2 * * * *")
    fun updateRecords() {
        println("update starts")
        val products = productRepository.findAll()
        val today = LocalDate.parse(LocalDate.now().toString(), DateTimeFormatter.ISO_DATE).toString()
        println("today: $today")
        for (product in products) {
            println("inserting ${product.name} in record DB")
            recordService.registerRecord(product.name, today, today)
        }
    }

}
