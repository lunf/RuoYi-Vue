package com.ruoyi.quartz.util;

import org.quartz.JobExecutionContext;
import com.ruoyi.quartz.domain.SysJob;

/**
 * timely tasks.（Enable the execution.）
 * 
 * @author ruoyi
 *
 */
public class QuartzJobExecution extends AbstractQuartzJob
{
    @Override
    protected void doExecute(JobExecutionContext context, SysJob sysJob) throws Exception
    {
        JobInvokeUtil.invokeMethod(sysJob);
    }
}
