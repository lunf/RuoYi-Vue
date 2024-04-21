package com.ruoyi.framework.interceptor;

import java.lang.reflect.Method;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import com.alibaba.fastjson2.JSON;
import com.ruoyi.common.annotation.RepeatSubmit;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.ServletUtils;

/**
 * Avoid repeated submission of blockers.
 *
 * @author ruoyi
 */
@Component
public abstract class RepeatSubmitInterceptor implements HandlerInterceptor
{
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
    {
        if (handler instanceof HandlerMethod)
        {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            RepeatSubmit annotation = method.getAnnotation(RepeatSubmit.class);
            if (annotation != null)
            {
                if (this.isRepeatSubmit(request, annotation))
                {
                    AjaxResult ajaxResult = AjaxResult.error(annotation.message());
                    ServletUtils.renderString(response, JSON.toJSONString(ajaxResult));
                    return false;
                }
            }
            return true;
        }
        else
        {
            return true;
        }
    }

    /**
     * Verify whether repeated submission is implemented by subclass specific preventive repeated submission rules
     *
     * @param request Request information
     * @param annotation Prevention of Repeated Notes
     * @return Results
     * @throws Exception
     */
    public abstract boolean isRepeatSubmit(HttpServletRequest request, RepeatSubmit annotation);
}
