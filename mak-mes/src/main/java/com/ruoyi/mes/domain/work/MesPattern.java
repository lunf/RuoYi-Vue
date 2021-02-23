package com.ruoyi.mes.domain.work;

import lombok.Data;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

@Data
public class MesPattern implements Serializable {
    /** Primary key */
    private Long patternId;

    /** This is reference between Pattern and Pattern Layout - not save to db */
    private Integer patternRefId;

    /** Reference to work order */
    private Long workOrderId;

    /** Each optimizer will generate run number, pattern for which run */
    private String runTag;

    /** Material for this pattern */
    private String materialName;

    /** Primary program */
    private String primaryProgram;

    /** Primary barcode */
    private String primaryBarcode;

    private byte[] imageInByte;

    /** Image for this pattern */
    private BufferedImage image;

    /** Is this 6th face */
    private boolean sixthFace;

    /** 6th face program */
    private String sixthFaceProgram;

    /** 6th face barcode */
    private String sixthFaceBarcode;

    public void setImage(BufferedImage image) {
        this.image = image;

        if (image != null) {
            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(image, "jpg", baos);
                baos.flush();
                this.imageInByte = baos.toByteArray();
                baos.close();
            } catch (IOException ex) {
                //TODO: need to log this or handle it better
                ex.printStackTrace();
            }
        }
    }

    public BufferedImage getImage() {
        if (imageInByte != null) {
            try {
                InputStream in = new ByteArrayInputStream(imageInByte);
                this.image = ImageIO.read(in);
                in.close();
            } catch (IOException ex) {
                //TODO: need to log this or handle it better
                ex.printStackTrace();
            }
        }

        return image;
    }
}
