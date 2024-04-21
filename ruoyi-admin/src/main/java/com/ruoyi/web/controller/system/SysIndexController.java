package com.ruoyi.web.controller.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.utils.StringUtils;

/**
 * The first page
 *
 * @author ruoyi
 */
@RestController
public class SysIndexController
{
    /** System Basic Configuration */
    @Autowired
    private RuoYiConfig ruoyiConfig;

    /**
     * Visit the first page.，Suggestions
     */
    @RequestMapping("/")
    public String index()
    {
        return StringUtils.format("Welcome to Use{}Background management framework，Current version：v{}，Please access through the front address.。", ruoyiConfig.getName(), ruoyiConfig.getVersion());
    }
}
