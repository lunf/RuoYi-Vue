package com.ruoyi.common.utils.ip;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.http.HttpUtils;

/**
 * Obtaining Address Classes
 * 
 * @author ruoyi
 */
public class AddressUtils
{
    private static final Logger log = LoggerFactory.getLogger(AddressUtils.class);

    // IPAddress requests
    public static final String IP_URL = "http://whois.pconline.com.cn/ipJson.jsp";

    // Unknown Address
    public static final String UNKNOWN = "XX XX";

    public static String getRealAddressByIP(String ip)
    {
        // The Internet does not search.
        if (IpUtils.internalIp(ip))
        {
            return "The internal networkIP";
        }
        if (RuoYiConfig.isAddressEnabled())
        {
            try
            {
                String rspStr = HttpUtils.sendGet(IP_URL, "ip=" + ip + "&json=true", Constants.GBK);
                if (StringUtils.isEmpty(rspStr))
                {
                    log.error("Unusual geographical location. {}", ip);
                    return UNKNOWN;
                }
                JSONObject obj = JSON.parseObject(rspStr);
                String region = obj.getString("pro");
                String city = obj.getString("city");
                return String.format("%s %s", region, city);
            }
            catch (Exception e)
            {
                log.error("Unusual geographical location. {}", ip);
            }
        }
        return UNKNOWN;
    }
}
