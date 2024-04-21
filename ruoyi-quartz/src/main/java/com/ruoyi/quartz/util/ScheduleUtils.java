package com.ruoyi.quartz.util;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.constant.ScheduleConstants;
import com.ruoyi.common.exception.job.TaskException;
import com.ruoyi.common.exception.job.TaskException.Code;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.quartz.domain.SysJob;

/**
 * timely task tools.
 * 
 * @author ruoyi
 *
 */
public class ScheduleUtils
{
    /**
     * GetquartzClass of tasks
     *
     * @param sysJob implementation plan
     * @return Execution of specific tasks.
     */
    private static Class<? extends Job> getQuartzJobClass(SysJob sysJob)
    {
        boolean isConcurrent = "0".equals(sysJob.getConcurrent());
        return isConcurrent ? QuartzJobExecution.class : QuartzDisallowConcurrentExecution.class;
    }

    /**
     * Create a task to trigger objects
     */
    public static TriggerKey getTriggerKey(Long jobId, String jobGroup)
    {
        return TriggerKey.triggerKey(ScheduleConstants.TASK_CLASS_NAME + jobId, jobGroup);
    }

    /**
     * Building the task key object.
     */
    public static JobKey getJobKey(Long jobId, String jobGroup)
    {
        return JobKey.jobKey(ScheduleConstants.TASK_CLASS_NAME + jobId, jobGroup);
    }

    /**
     * Create timely tasks.
     */
    public static void createScheduleJob(Scheduler scheduler, SysJob job) throws SchedulerException, TaskException
    {
        Class<? extends Job> jobClass = getQuartzJobClass(job);
        // BuildingjobInformation
        Long jobId = job.getJobId();
        String jobGroup = job.getJobGroup();
        JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(getJobKey(jobId, jobGroup)).build();

        // Expression Constructor
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
        cronScheduleBuilder = handleCronScheduleMisfirePolicy(job, cronScheduleBuilder);

        // according to newcronExpressionCreate a new expression.trigger
        CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(getTriggerKey(jobId, jobGroup))
                .withSchedule(cronScheduleBuilder).build();

        // Put in the parameters.，The operation method can be obtained.
        jobDetail.getJobDataMap().put(ScheduleConstants.TASK_PROPERTIES, job);

        // Determining whether there is
        if (scheduler.checkExists(getJobKey(jobId, jobGroup)))
        {
            // Avoid data problems when creating. Remove first.，Then perform the operation.
            scheduler.deleteJob(getJobKey(jobId, jobGroup));
        }

        // Deciding whether the task is over.
        if (StringUtils.isNotNull(CronUtils.getNextExecution(job.getCronExpression())))
        {
            // Implementation of measuring tasks
            scheduler.scheduleJob(jobDetail, trigger);
        }

        // Suspension of task
        if (job.getStatus().equals(ScheduleConstants.Status.PAUSE.getValue()))
        {
            scheduler.pauseJob(ScheduleUtils.getJobKey(jobId, jobGroup));
        }
    }

    /**
     * Set a timely task strategy.
     */
    public static CronScheduleBuilder handleCronScheduleMisfirePolicy(SysJob job, CronScheduleBuilder cb)
            throws TaskException
    {
        switch (job.getMisfirePolicy())
        {
            case ScheduleConstants.MISFIRE_DEFAULT:
                return cb;
            case ScheduleConstants.MISFIRE_IGNORE_MISFIRES:
                return cb.withMisfireHandlingInstructionIgnoreMisfires();
            case ScheduleConstants.MISFIRE_FIRE_AND_PROCEED:
                return cb.withMisfireHandlingInstructionFireAndProceed();
            case ScheduleConstants.MISFIRE_DO_NOTHING:
                return cb.withMisfireHandlingInstructionDoNothing();
            default:
                throw new TaskException("The task misfire policy '" + job.getMisfirePolicy()
                        + "' cannot be used in cron schedule tasks", Code.CONFIG_ERROR);
        }
    }

    /**
     * Check if the package name is configured to the white list.
     * 
     * @param invokeTarget Targeted characters
     * @return Results
     */
    public static boolean whiteList(String invokeTarget)
    {
        String packageName = StringUtils.substringBefore(invokeTarget, "(");
        int count = StringUtils.countMatches(packageName, ".");
        if (count > 1)
        {
            return StringUtils.containsAnyIgnoreCase(invokeTarget, Constants.JOB_WHITELIST_STR);
        }
        Object obj = SpringUtils.getBean(StringUtils.split(invokeTarget, ".")[0]);
        String beanPackageName = obj.getClass().getPackage().getName();
        return StringUtils.containsAnyIgnoreCase(beanPackageName, Constants.JOB_WHITELIST_STR)
                && !StringUtils.containsAnyIgnoreCase(beanPackageName, Constants.JOB_ERROR_STR);
    }
}
