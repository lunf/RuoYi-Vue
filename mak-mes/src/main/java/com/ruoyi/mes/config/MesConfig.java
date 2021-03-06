package com.ruoyi.mes.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties
@PropertySource(value = { "classpath:mes.yml" })
public class MesConfig {

    // List of primary machine name
    private static List<String> primaryMachineNames;

    // Bevel edge indicator
    private static String bevelEdgeIndicator;

    // Jasper report label template
    private static Resource jasperLabelTemplate;
    // Default compiled label template
    private static Resource jasperCompiledLabelTemplate;
    // Store compiled label template
    private static String jasperCompiledStoreLocation;

    // Label for each working station
    private static String shopFloorStationCutLabel;
    private static String shopFloorStationPackLabel;

    // Arrow images
    private static Resource resourceDownArrow;
    private static Resource resourceLeftArrow;
    private static Resource resourceUpArrow;
    private static Resource resourceRightArrow;
    // Flip of arrow images
    private static Resource resourceFlipDownArrow;
    private static Resource resourceFlipLeftArrow;
    private static Resource resourceFlipUpArrow;
    private static Resource resourceFlipRightArrow;

    public static List<String> getPrimaryMachineNames() {
        return primaryMachineNames;
    }

    @Value("#{${primary-machines}}")
    public void setPrimaryMachineNames(List<String> primaryMachineNames) {
        MesConfig.primaryMachineNames = primaryMachineNames;
    }

    public static String getBevelEdgeIndicator() {
        return bevelEdgeIndicator;
    }

    @Value("${edge-indicator-bevel}")
    public void setBevelEdgeIndicator(String bevelEdgeIndicator) {
        MesConfig.bevelEdgeIndicator = bevelEdgeIndicator;
    }

    public static Resource getJasperLabelTemplate() {
        return jasperLabelTemplate;
    }

    @Value("classpath:baseLabelTemplate.jrxml")
    public void setJasperLabelTemplate(Resource jasperLabelTemplate) {
        MesConfig.jasperLabelTemplate = jasperLabelTemplate;
    }

    public static Resource getJasperCompiledLabelTemplate() {
        return jasperCompiledLabelTemplate;
    }

    @Value("${report-compiled-path}")
    public void setJasperCompiledLabelTemplate(Resource jasperCompiledLabelTemplate) {
        MesConfig.jasperCompiledLabelTemplate = jasperCompiledLabelTemplate;
    }

    public static String getJasperCompiledStoreLocation() {
        return jasperCompiledStoreLocation;
    }

    @Value("${report-compiled-location}")
    public void setJasperCompiledStoreLocation(String jasperCompiledStoreLocation) {
        MesConfig.jasperCompiledStoreLocation = jasperCompiledStoreLocation;
    }

    public static String getShopFloorStationCutLabel() {
        return shopFloorStationCutLabel;
    }

    @Value("${shopfloor-station-cut}")
    public void setShopFloorStationCutLabel(String shopFloorStationCutLabel) {
        MesConfig.shopFloorStationCutLabel = shopFloorStationCutLabel;
    }

    public static String getShopFloorStationPackLabel() {
        return shopFloorStationPackLabel;
    }

    @Value("${shopfloor-station-pack}")
    public void setShopFloorStationPackLabel(String shopFloorStationPackLabel) {
        this.shopFloorStationPackLabel = shopFloorStationPackLabel;
    }

    public static Resource getResourceDownArrow() {
        return resourceDownArrow;
    }

    @Value("classpath:images/arrow-down.png")
    public void setResourceDownArrow(Resource resourceDownArrow) {
        MesConfig.resourceDownArrow = resourceDownArrow;
    }

    public static Resource getResourceLeftArrow() {
        return resourceLeftArrow;
    }

    @Value("classpath:images/arrow-left.png")
    public void setResourceLeftArrow(Resource resourceLeftArrow) {
        MesConfig.resourceLeftArrow = resourceLeftArrow;
    }

    public static Resource getResourceUpArrow() {
        return resourceUpArrow;
    }

    @Value("classpath:images/arrow-up.png")
    public void setResourceUpArrow(Resource resourceUpArrow) {
        MesConfig.resourceUpArrow = resourceUpArrow;
    }

    public static Resource getResourceRightArrow() {
        return resourceRightArrow;
    }

    @Value("classpath:images/arrow-right.png")
    public void setResourceRightArrow(Resource resourceRightArrow) {
        MesConfig.resourceRightArrow = resourceRightArrow;
    }

    public static Resource getResourceFlipDownArrow() {
        return resourceFlipDownArrow;
    }

    @Value("classpath:images/arrow-flip-down.png")
    public void setResourceFlipDownArrow(Resource resourceFlipDownArrow) {
        MesConfig.resourceFlipDownArrow = resourceFlipDownArrow;
    }

    public static Resource getResourceFlipLeftArrow() {
        return resourceFlipLeftArrow;
    }

    @Value("classpath:images/arrow-flip-left.png")
    public void setResourceFlipLeftArrow(Resource resourceFlipLeftArrow) {
        MesConfig.resourceFlipLeftArrow = resourceFlipLeftArrow;
    }

    public static Resource getResourceFlipUpArrow() {
        return resourceFlipUpArrow;
    }

    @Value("classpath:images/arrow-flip-up.png")
    public void setResourceFlipUpArrow(Resource resourceFlipUpArrow) {
        MesConfig.resourceFlipUpArrow = resourceFlipUpArrow;
    }

    public static Resource getResourceFlipRightArrow() {
        return resourceFlipRightArrow;
    }

    @Value("classpath:images/arrow-flip-right.png")
    public void setResourceFlipRightArrow(Resource resourceFlipRightArrow) {
        MesConfig.resourceFlipRightArrow = resourceFlipRightArrow;
    }
}
