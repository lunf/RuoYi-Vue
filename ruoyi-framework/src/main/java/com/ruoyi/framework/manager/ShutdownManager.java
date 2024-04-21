package com.ruoyi.framework.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import javax.annotation.PreDestroy;

/**
 * Make sure the app can close the back line when it comes out.
 *
 * @author ruoyi
 */
@Component
public class ShutdownManager
{
    private static final Logger logger = LoggerFactory.getLogger("sys-user");

    @PreDestroy
    public void destroy()
    {
        shutdownAsyncManager();
    }

    /**
     * Stop performing tasks.
     */
    private void shutdownAsyncManager()
    {
        try
        {
            logger.info("====Closing the task line.====");
            AsyncManager.me().shutdown();
        }
        catch (Exception e)
        {
            logger.error(e.getMessage(), e);
        }
    }
}
