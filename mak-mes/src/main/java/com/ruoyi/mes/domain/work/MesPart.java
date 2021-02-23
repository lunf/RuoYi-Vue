package com.ruoyi.mes.domain.work;

import lombok.Data;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

@Data
public class MesPart implements Serializable {

    public static final String ORIENTATION_FACE = "FACE";
    public static final String ORIENTATION_BACK = "BACK";

    // No face
    public static final String LEFT = "01";
    public static final String RIGHT = "02";
    public static final String UP = "04";
    public static final String DOWN = "08";

    public static final String FACE_LEFT = "21";
    public static final String FACE_RIGHT = "22";
    public static final String FACE_UP = "24";
    public static final String FACE_DOWN = "28";

    public static final String FLIP_LEFT = "11";
    public static final String FLIP_RIGHT = "12";
    public static final String FLIP_UP = "14";
    public static final String FLIP_DOWN = "18";

    /** Primary key */
    public Long partId;

    /** This is the original reference between parts and panel layout (not store in db) */
    public Integer partRefId;

    /** FK to work order table */
    public Long workOrderId;

    /** In some case, part do not reference to Cabinet table */
    public Long cabinetId;

    /** Part sequence */
    public Integer partSequence;

    /** Part generated barcode */
    public String barcode;

    /** Part name */
    public String name;

    /** Part short name */
    public String shortName;

    /** Part width as cut on machine */
    public Double cutWidth;

    /** Part length as cut on machine */
    public Double cutLength;

    /** Finished part width after banding */
    public Double bandedWidth;

    /** Finished part length after banding */
    public Double bandedLength;

    /** A value representing the Quantity of the part - default '1' */
    public int qty;

    /** Primary part edge that rests against the point-to-point pins (1 = Left, 2 = Top, 3 = Right, 4 = Bottom) */
    public Integer primaryEdge;

    /** The work stations that part need to go through */
    public String workflow;

    /** The name for the sheet material */
    public String panelMaterial;

    /** A value representing the Thickness for this sheet of Material */
    public Double materialThickness;

    /** E.g: 6NNENEN 6 sided part from face clockwise stroke(N = None, I = Interior, E = Exterior, D = Door)*/
    public String bandingFlags;

    /** Program name for the part front face operations */
    public String frontProgram;

    /** Program name for the part back face operations */
    public String backProgram;

    /** Indicates the arrow position for face program (e.g: arrow flip and Face Up/Down flag) */
    public String frontOrientation;

    /** Indicates the arrow position for back program (e.g: arrow flip and Face Up/Down flag) */
    public String backOrientation;

    /** Machine used for primary output */
    public String primaryMachineName;

    /** Edge material (e.g: D:1m23_101T, E:1m23_388EV) */
    public String edgeMaterial;

    /** File title of part/label image */
    public String imageName;

    /** Binary graphic for this part */
    public byte[] imageInByte;

    public BufferedImage image;


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
