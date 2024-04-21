package com.ruoyi.framework.config;

import java.util.Properties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import static com.google.code.kaptcha.Constants.*;

/**
 * Configuration of verification code
 * 
 * @author ruoyi
 */
@Configuration
public class CaptchaConfig
{
    @Bean(name = "captchaProducer")
    public DefaultKaptcha getKaptchaBean()
    {
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        Properties properties = new Properties();
        // Is there a Framework? I thinktrue We can set ourselves.yes，no
        properties.setProperty(KAPTCHA_BORDER, "yes");
        // Verify the color of text characters I thinkColor.BLACK
        properties.setProperty(KAPTCHA_TEXTPRODUCER_FONT_COLOR, "black");
        // Verify the image width. I think200
        properties.setProperty(KAPTCHA_IMAGE_WIDTH, "160");
        // Verification of image height. I think50
        properties.setProperty(KAPTCHA_IMAGE_HEIGHT, "60");
        // Verify the text character size. I think40
        properties.setProperty(KAPTCHA_TEXTPRODUCER_FONT_SIZE, "38");
        // KAPTCHA_SESSION_KEY
        properties.setProperty(KAPTCHA_SESSION_CONFIG_KEY, "kaptchaCode");
        // Verify the length of text characters I think5
        properties.setProperty(KAPTCHA_TEXTPRODUCER_CHAR_LENGTH, "4");
        // Verification of text font style. I thinknew Font("Arial", 1, fontSize), new Font("Courier", 1, fontSize)
        properties.setProperty(KAPTCHA_TEXTPRODUCER_FONT_NAMES, "Arial,Courier");
        // The image style Water Tattooscom.google.code.kaptcha.impl.WaterRipple The Fish Eyecom.google.code.kaptcha.impl.FishEyeGimpy The Shadowcom.google.code.kaptcha.impl.ShadowGimpy
        properties.setProperty(KAPTCHA_OBSCURIFICATOR_IMPL, "com.google.code.kaptcha.impl.ShadowGimpy");
        Config config = new Config(properties);
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }

    @Bean(name = "captchaProducerMath")
    public DefaultKaptcha getKaptchaBeanMath()
    {
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        Properties properties = new Properties();
        // Is there a Framework? I thinktrue We can set ourselves.yes，no
        properties.setProperty(KAPTCHA_BORDER, "yes");
        // The Frame Color I thinkColor.BLACK
        properties.setProperty(KAPTCHA_BORDER_COLOR, "105,179,90");
        // Verify the color of text characters I thinkColor.BLACK
        properties.setProperty(KAPTCHA_TEXTPRODUCER_FONT_COLOR, "blue");
        // Verify the image width. I think200
        properties.setProperty(KAPTCHA_IMAGE_WIDTH, "160");
        // Verification of image height. I think50
        properties.setProperty(KAPTCHA_IMAGE_HEIGHT, "60");
        // Verify the text character size. I think40
        properties.setProperty(KAPTCHA_TEXTPRODUCER_FONT_SIZE, "35");
        // KAPTCHA_SESSION_KEY
        properties.setProperty(KAPTCHA_SESSION_CONFIG_KEY, "kaptchaCodeMath");
        // Verification of text generator.
        properties.setProperty(KAPTCHA_TEXTPRODUCER_IMPL, "com.ruoyi.framework.config.KaptchaTextCreator");
        // Verify the code of text character distance. I think2
        properties.setProperty(KAPTCHA_TEXTPRODUCER_CHAR_SPACE, "3");
        // Verify the length of text characters I think5
        properties.setProperty(KAPTCHA_TEXTPRODUCER_CHAR_LENGTH, "6");
        // Verification of text font style. I thinknew Font("Arial", 1, fontSize), new Font("Courier", 1, fontSize)
        properties.setProperty(KAPTCHA_TEXTPRODUCER_FONT_NAMES, "Arial,Courier");
        // Verification of noise color. I thinkColor.BLACK
        properties.setProperty(KAPTCHA_NOISE_COLOR, "white");
        // Interference in implementation.
        properties.setProperty(KAPTCHA_NOISE_IMPL, "com.google.code.kaptcha.impl.NoNoise");
        // The image style Water Tattooscom.google.code.kaptcha.impl.WaterRipple The Fish Eyecom.google.code.kaptcha.impl.FishEyeGimpy The Shadowcom.google.code.kaptcha.impl.ShadowGimpy
        properties.setProperty(KAPTCHA_OBSCURIFICATOR_IMPL, "com.google.code.kaptcha.impl.ShadowGimpy");
        Config config = new Config(properties);
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }
}
