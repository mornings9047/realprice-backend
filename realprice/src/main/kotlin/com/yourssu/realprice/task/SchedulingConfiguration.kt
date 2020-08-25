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
                    name = "RecordInsertingJob",
                    desc = "새로운 가격 정보 저장",
                    params = HashMap<String, Any>()
            )

            if (scheduler.checkExists(jobDetail.key))
                scheduler.deleteJob(jobDetail.key)

            scheduler.scheduleJob(
                    jobDetail,
                    buildCronJobTrigger("0 0/1 * * * ?")
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

    fun buildSimpleJobTrigger(hour: Int): Trigger {
        return TriggerBuilder.newTrigger()
                .withSchedule(SimpleScheduleBuilder
                        .simpleSchedule()
                        .repeatForever()
                        .withIntervalInHours(hour))
                .build()
    }

    fun buildJobDetail(job: Class<RecordInsertCronJob>, name: String?, desc: String?, params: Map<*, *>): JobDetail {
        val jobDataMap = JobDataMap()
        jobDataMap.putAll(params as Map<out String, Any>)
        return JobBuilder
                .newJob(job)
                .withIdentity(name)
                .withDescription(desc)
                .usingJobData(jobDataMap)
                .build()
    }
}
