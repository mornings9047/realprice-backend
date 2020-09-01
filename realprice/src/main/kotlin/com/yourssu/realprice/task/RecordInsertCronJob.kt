package com.yourssu.realprice.task

import com.yourssu.realprice.service.record.RecordService
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.quartz.InterruptableJob
import org.quartz.JobExecutionContext
import org.quartz.JobKey
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.quartz.QuartzJobBean
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Component
class RecordInsertCronJob : QuartzJobBean(), InterruptableJob {
    @Autowired
    lateinit var recordService: RecordService
    private var isInterrupted: Boolean = false
    private var jobKey: JobKey? = null
    private val log: Logger = LogManager.getLogger()

    override fun executeInternal(context: JobExecutionContext) {
        jobKey = context.jobDetail.key
        log.info("Date: ${LocalDate.now()} / Inserting Records Starts...")
        if (isInterrupted) {
            log.info("jobKey: $jobKey is interrupted")
            return
        }
        val today = LocalDate.parse(LocalDate.now().toString(), DateTimeFormatter.ISO_DATE).toString()
        recordService.registerAllRecords(today, today)
    }

    override fun interrupt() {
        log.info("$jobKey -- INTERTUPTING --")
        isInterrupted = true
    }
}
