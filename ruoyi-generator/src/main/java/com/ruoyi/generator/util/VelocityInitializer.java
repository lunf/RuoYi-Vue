package com.ruoyi.generator.util;

import java.util.Properties;
import org.apache.velocity.app.Velocity;
import com.ruoyi.common.constant.Constants;

/**
 * VelocityEngineFactory
 * 
 * @author ruoyi
 */
public class VelocityInitializer
{
    /**
     * InitiationvmMethod
     */
    public static void initVelocity()
    {
        Properties p = new Properties();
        try
        {
            // loadedclasspathUnder the catalogue.vmDocuments
            p.setProperty("resource.loader.file.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
            // Definition of characters.
            p.setProperty(Velocity.INPUT_ENCODING, Constants.UTF8);
            // InitiationVelocityThe engine，Defined configuration.Properties
            Velocity.init(p);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
}
