package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.SysConfig;

/**
 * The parameter configuration of service.
 * 
 * @author ruoyi
 */
public interface ISysConfigService
{
    /**
     * Ask for parameter configuration information
     * 
     * @param configId The parameter configurationID
     * @return parameter configuration information
     */
    public SysConfig selectConfigById(Long configId);

    /**
     * Based on the key name query parameter configuration information
     * 
     * @param configKey The parameter name.
     * @return The parameter value.
     */
    public String selectConfigByKey(String configKey);

    /**
     * Get the verification code.
     * 
     * @return trueopened，falseclosed
     */
    public boolean selectCaptchaEnabled();

    /**
     * Ask for the parameter configuration list
     * 
     * @param config parameter configuration information
     * @return The Parameters Collection
     */
    public List<SysConfig> selectConfigList(SysConfig config);

    /**
     * Additional parameters
     * 
     * @param config parameter configuration information
     * @return Results
     */
    public int insertConfig(SysConfig config);

    /**
     * Modification of parameters
     * 
     * @param config parameter configuration information
     * @return Results
     */
    public int updateConfig(SysConfig config);

    /**
     * Remove parameter information.
     * 
     * @param configIds The parameters need to be removed.ID
     */
    public void deleteConfigByIds(Long[] configIds);

    /**
     * Loading parameter cache data
     */
    public void loadingConfigCache();

    /**
     * Empty parameters cache data
     */
    public void clearConfigCache();

    /**
     * Repair parameter cache data
     */
    public void resetConfigCache();

    /**
     * Is the test parameter key name unique?
     * 
     * @param config parameter information
     * @return Results
     */
    public boolean checkConfigKeyUnique(SysConfig config);
}
