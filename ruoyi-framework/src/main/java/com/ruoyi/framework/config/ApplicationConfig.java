package com.ruoyi.framework.config;

import java.util.TimeZone;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * The program notes configuration.
 *
 * @author ruoyi
 */
@Configuration
// stated passing.aopThe framework reveals the agent's object,AopContextPossible access.
@EnableAspectJAutoProxy(exposeProxy = true)
// Designed to scan.MapperRoute of Class Package
@MapperScan("com.ruoyi.**.mapper")
public class ApplicationConfig
{
    /**
     * The time area.
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jacksonObjectMapperCustomization()
    {
        return jacksonObjectMapperBuilder -> jacksonObjectMapperBuilder.timeZone(TimeZone.getDefault());
    }
}
