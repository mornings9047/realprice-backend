package com.yourssu.realprice.task

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.quartz.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import javax.annotation.PostConstruct

@Configuration
class SchedulingConfiguration {
    val log: Logger = LogManager.getLogger()

    @Autowired
    lateinit var scheduler: Scheduler

    @PostConstruct
    fun start() {
        log.info("JobController start invoked")

        try {
            val jobDetail: JobDetail = buildJobDetail(
                    RecordInsertCronJob::class.java,
                    name = "Inserting Records",
                    desc = "새로운 가격 정보 저장"
            )

            if (scheduler.checkExists(jobDetail.key))
                scheduler.deleteJob(jobDetail.key)

            scheduler.scheduleJob(
                    jobDetail,
                    buildCronJobTrigger("0 30 14 * * ?")
            )
        } catch (e: SchedulerException) {
            e.printStackTrace()
        }
    }

    fun buildCronJobTrigger(scheduleExp: String): Trigger {
        return TriggerBuilder.newTrigger()
                .withSchedule(CronScheduleBuilder.cronSchedule(scheduleExp))
                .build()
    }

    fun buildJobDetail(job: Class<RecordInsertCronJob>, name: String?, desc: String?/*, params: Map<*, *>*/): JobDetail {
        val jobDataMap = JobDataMap()
        return JobBuilder
                .newJob(job)
                .withIdentity(name)
                .withDescription(desc)
                .usingJobData(jobDataMap)
                .build()
    }
}
