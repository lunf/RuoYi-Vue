package com.ruoyi.mes.business;

import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.file.FileUtils;
import com.ruoyi.mes.domain.MesWorkOrder;
import com.ruoyi.mes.service.IMesWorkOrderService;
import net.lingala.zip4j.exception.ZipException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.nio.file.FileSystems;
import java.nio.file.PathMatcher;
import java.util.TimerTask;

/**
 * This class will help convert CV database
 */
public class CabinetVisionTask extends TimerTask {

    private static final Logger cabinet_vision_logger = LoggerFactory.getLogger("cabinet-vision");

    private final PathMatcher MDB_PATH_MATCHER = FileSystems.getDefault()
            .getPathMatcher("glob:*.mdb");

    public static final String CABINETS = "CABINETS";
    public static final String PATTERNS = "PATTERNS";
    public static final String PANEL_LAYOUT = "PANEL_LAYOUT";

    private final Long workOrderId;

    @Autowired
    private IMesWorkOrderService mesWorkOrderService;

    public CabinetVisionTask(Long workOrderId) {
        this.workOrderId = workOrderId;
    }

    @Override
    public void run() {

        MesWorkOrder mesWorkOrder =  mesWorkOrderService.selectMesWorkOrderById(workOrderId);

        if (mesWorkOrder == null) {
            cabinet_vision_logger.warn("Program will stop due to not found work order {} for running Cabinet Vision task", workOrderId);
            return;
        }

        String uploadFileUrl = mesWorkOrder.getProcessFilePath();

        // Local resource path
        String localPath = RuoYiConfig.getProfile();
        // Database resource address
        String downloadPath = localPath + StringUtils.substringAfter(uploadFileUrl, Constants.RESOURCE_PREFIX);
        // Download name
        String downloadName = StringUtils.substringAfterLast(downloadPath, "/");

        // Folder path
        String downloadFolderPath = StringUtils.substringBefore(downloadPath, downloadName);

        try {
            FileUtils.unzip(downloadPath, downloadFolderPath, null);
        } catch (ZipException ex) {
            cabinet_vision_logger.warn("Program will stop due to fail to unzip {}", downloadPath, ex);
            return;
        }

        String unzipFolder = downloadFolderPath + "/" + downloadName;
        String mdbFilePath = FileUtils.lookupFileTypeInFolder(unzipFolder, MDB_PATH_MATCHER);

        if (StringUtils.isEmpty(mdbFilePath)) {
            cabinet_vision_logger.error("Program will stop due to no MDB file found in path: {}", unzipFolder);
            return;
        }




    }
}
