package com.ruoyi.framework.config.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import com.alibaba.druid.pool.DruidDataSource;

/**
 * druid Configuration of Properties
 * 
 * @author ruoyi
 */
@Configuration
public class DruidProperties
{
    @Value("${spring.datasource.druid.initialSize}")
    private int initialSize;

    @Value("${spring.datasource.druid.minIdle}")
    private int minIdle;

    @Value("${spring.datasource.druid.maxActive}")
    private int maxActive;

    @Value("${spring.datasource.druid.maxWait}")
    private int maxWait;

    @Value("${spring.datasource.druid.connectTimeout}")
    private int connectTimeout;

    @Value("${spring.datasource.druid.socketTimeout}")
    private int socketTimeout;

    @Value("${spring.datasource.druid.timeBetweenEvictionRunsMillis}")
    private int timeBetweenEvictionRunsMillis;

    @Value("${spring.datasource.druid.minEvictableIdleTimeMillis}")
    private int minEvictableIdleTimeMillis;

    @Value("${spring.datasource.druid.maxEvictableIdleTimeMillis}")
    private int maxEvictableIdleTimeMillis;

    @Value("${spring.datasource.druid.validationQuery}")
    private String validationQuery;

    @Value("${spring.datasource.druid.testWhileIdle}")
    private boolean testWhileIdle;

    @Value("${spring.datasource.druid.testOnBorrow}")
    private boolean testOnBorrow;

    @Value("${spring.datasource.druid.testOnReturn}")
    private boolean testOnReturn;

    public DruidDataSource dataSource(DruidDataSource datasource)
    {
        /** Set up the initial size.、The smallest、The greatest */
        datasource.setInitialSize(initialSize);
        datasource.setMaxActive(maxActive);
        datasource.setMinIdle(minIdle);

        /** Configuration to get connected waiting overtime time */
        datasource.setMaxWait(maxWait);
        
        /** Configuration of the drive connection overtime，Detection of data base connectivity overtime，The unit is millisecond. */
        datasource.setConnectTimeout(connectTimeout);
        
        /** Network overtime.，Network overtime waiting for the database operation to be completed，The unit is millisecond. */
        datasource.setSocketTimeout(socketTimeout);

        /** How long is the interval for one testing?，Detection of free connections that need to be closed，The unit is millisecond. */
        datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);

        /** Set the smallest connection in the pool.、Maximum time of survival.，The unit is millisecond. */
        datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        datasource.setMaxEvictableIdleTimeMillis(maxEvictableIdleTimeMillis);

        /**
         * Identify whether the connection is effective.sql，Request is a query.，commonly usedselect 'x'。IfvalidationQueryfornull，testOnBorrow、testOnReturn、testWhileIdleNothing works.。
         */
        datasource.setValidationQuery(validationQuery);
        /** It is recommended totrue，does not affect performance.，and guarantees safety.。Check when requesting connection.，The free time is greater thantimeBetweenEvictionRunsMillis，ExecutionvalidationQueryCheck whether the connection is effective.。 */
        datasource.setTestWhileIdle(testWhileIdle);
        /** Application for connectionvalidationQueryCheck whether the connection is effective.，Doing this configuration will reduce performance.。 */
        datasource.setTestOnBorrow(testOnBorrow);
        /** Return to connection.validationQueryCheck whether the connection is effective.，Doing this configuration will reduce performance.。 */
        datasource.setTestOnReturn(testOnReturn);
        return datasource;
    }
}
