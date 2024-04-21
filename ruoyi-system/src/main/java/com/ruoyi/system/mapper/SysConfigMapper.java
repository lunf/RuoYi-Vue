package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.SysConfig;

/**
 * The parameter configuration The Data Layer
 * 
 * @author ruoyi
 */
public interface SysConfigMapper
{
    /**
     * Ask for parameter configuration information
     * 
     * @param config parameter configuration information
     * @return parameter configuration information
     */
    public SysConfig selectConfig(SysConfig config);

    /**
     * ThroughIDQuestion Configuration
     * 
     * @param configId ParametersID
     * @return parameter configuration information
     */
    public SysConfig selectConfigById(Long configId);

    /**
     * Ask for the parameter configuration list
     * 
     * @param config parameter configuration information
     * @return The Parameters Collection
     */
    public List<SysConfig> selectConfigList(SysConfig config);

    /**
     * Based on the key name query parameter configuration information
     * 
     * @param configKey The parameter name.
     * @return parameter configuration information
     */
    public SysConfig checkConfigKeyUnique(String configKey);

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
     * Remove the parameters.
     * 
     * @param configId ParametersID
     * @return Results
     */
    public int deleteConfigById(Long configId);

    /**
     * Remove parameter information.
     * 
     * @param configIds The parameters need to be removed.ID
     * @return Results
     */
    public int deleteConfigByIds(Long[] configIds);
}
