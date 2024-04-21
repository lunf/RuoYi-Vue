package com.ruoyi.framework.manager;

import java.util.TimerTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import com.ruoyi.common.utils.Threads;
import com.ruoyi.common.utils.spring.SpringUtils;

/**
 * Extraterrestrial task manager
 * 
 * @author ruoyi
 */
public class AsyncManager
{
    /**
     * Delayed operation.10Mills of seconds.
     */
    private final int OPERATE_DELAY_TIME = 10;

    /**
     * Extraterrestrial Operation Task Modification Linear Pool
     */
    private ScheduledExecutorService executor = SpringUtils.getBean("scheduledExecutorService");

    /**
     * The single model.
     */
    private AsyncManager(){}

    private static AsyncManager me = new AsyncManager();

    public static AsyncManager me()
    {
        return me;
    }

    /**
     * performing the task.
     * 
     * @param task The task
     */
    public void execute(TimerTask task)
    {
        executor.schedule(task, OPERATE_DELAY_TIME, TimeUnit.MILLISECONDS);
    }

    /**
     * Stop the task line.
     */
    public void shutdown()
    {
        Threads.shutdownAndAwaitTermination(executor);
    }
}
