package com.ruoyi.mes.business.reader;

import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.mes.business.CabinetVisionTask;
import com.ruoyi.mes.config.MesConfig;
import com.ruoyi.mes.domain.work.MesCabinet;
import com.ruoyi.mes.domain.work.MesPanelLayout;
import com.ruoyi.mes.domain.work.MesPart;
import com.ruoyi.mes.domain.work.MesPattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.ByteBuffer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

@Component
public class MdbReader {
    private static final Logger mdb_reader_logger = LoggerFactory.getLogger("mdb-reader");

    private static final double inch_millimeters = 25.4;
    private static final String SEPARATOR = "_";

    public Map<String, List> readCabinetFromMdb(String fileName) {
        try {

            File file = null;
            try {
                file = new File(fileName);
            } catch (Exception ex) {
                mdb_reader_logger.error("Fail to locate MDB file with name " + fileName, ex);
            }

            Connection conn = DriverManager.getConnection(
                    "jdbc:ucanaccess://" + file.getAbsolutePath());

            List<MesCabinet> cabinetEntities = parseAssemblies(conn);

            List<MesPattern> patternEntities = parsePattern(conn);
            List<MesPanelLayout> panelLayoutEntities = parsePanelLayout(conn);

            Map<String, List> data = new HashMap<>();
            data.put(CabinetVisionTask.CABINETS, cabinetEntities);

            data.put(CabinetVisionTask.PATTERNS, patternEntities);
            data.put(CabinetVisionTask.PANEL_LAYOUT, panelLayoutEntities);

            return  data;

        } catch (Exception ex) {
            mdb_reader_logger.error("!!!Program stop here!!! Fail to read cabinet from MDB with name " + fileName, ex);
        }

        return null;
    }

    private Double toMillimeters(double inputInches) {

        BigDecimal value = BigDecimal.valueOf(inputInches * inch_millimeters)
                .setScale(0, RoundingMode.HALF_EVEN);

        return value.doubleValue();
    }

    private Map convertOrientation(Integer decimal) {
        // Convert integer to bytes array
        ByteBuffer b = ByteBuffer.allocate(4);
        //b.order(ByteOrder.BIG_ENDIAN); // optional, the initial order of a byte buffer is always BIG_ENDIAN.
        b.putInt(decimal);

        byte[] result = b.array();

        // Convert bytes to hexadecimal (two's complement)
        StringBuffer buffer = new StringBuffer();

        for(int i=0; i < result.length; i++){
            buffer.append(Character.forDigit((result[i] >> 4) & 0xF, 16));
            buffer.append(Character.forDigit((result[i] & 0xF), 16));
        }

        Map orientation = new HashMap<String, String>();
        // Step = 2, use 2 characters to represent the value
        for (int i=0; i < buffer.length() - 1; i+=2) {
            // Edge
            String edge = buffer.substring(i,i + 2);
            if (!edge.equalsIgnoreCase("00")) {
                if(i == buffer.length() - 2){
                    // Last iteration
                    orientation.put(MesPart.ORIENTATION_BACK, edge);
                } else {
                    orientation.put(MesPart.ORIENTATION_FACE, edge);
                }
            }
        }
        return orientation;
    }

    private List<MesCabinet> parseAssemblies(Connection connection) throws Exception {
        Statement s = connection.createStatement();
        ResultSet rs = s.executeQuery("Select ID, Comment AS ProductCode, Type, Class, [Rooms].RoomName, [Assemblies].Description," +
                "[Assemblies].Width, [Assemblies].Height, [Assemblies].Depth, [Assemblies].Name, [Assemblies].Number " +
                " FROM [Assemblies] LEFT JOIN [Rooms] ON [Assemblies].RoomID = [Rooms].ID ORDER BY [Assemblies].ID");

        Map<Integer, MesCabinet> mesCabinetMap = new HashMap<>();

        while (rs.next()) {
            Integer asmId = rs.getInt("ID");
            String productCode = rs.getString("ProductCode");
            String description = rs.getString("Description");
            String roomName = rs.getString("RoomName");
            Integer asmType = rs.getInt("Type");
            Integer asmClass = rs.getInt("Class");
            String asmName = rs.getString("Name");
            Integer asmNumber = rs.getInt("Number");
            Double width = rs.getDouble("Width");
            Double height = rs.getDouble("Height");
            Double depth = rs.getDouble("Depth");

            MesCabinet cabinetEntity = new MesCabinet();
            cabinetEntity.setRoomNum(roomName);
            cabinetEntity.setProductCode(productCode);
            cabinetEntity.setName(asmName);
            cabinetEntity.setDescription(description);
            cabinetEntity.setAsmType(convertAsmTypeInt(asmType));
            cabinetEntity.setAsmClass(convertAsmClassInt(asmClass));
            cabinetEntity.setWidth(toMillimeters(width));
            cabinetEntity.setHeight(toMillimeters(height));
            cabinetEntity.setDepth(toMillimeters(depth));

            // Check if they have 3 digits, then remove the first one
            if (asmNumber.intValue() > 100 && asmNumber.intValue() < 1000) {
                String numStr = String.valueOf(asmNumber);
                numStr = numStr.substring(1);
                asmNumber = Integer.valueOf(numStr);
            }

            cabinetEntity.setCode(String.valueOf(asmNumber));

            // Insert into map
            if (!mesCabinetMap.containsKey(asmId)) {
                mesCabinetMap.put(asmId, cabinetEntity);
            }
        }

        // Find parts that associated with cabinet
        mesCabinetMap.forEach((key, value) -> {
            processPartData(connection, key, value);
        });


        return new ArrayList<>(mesCabinetMap.values());

    }

    private String convertAsmTypeInt(int type) {
        String asmType;
        switch (type) {
            case 1:
                asmType = "Standard";
                break;
            case 2:
                asmType = "Corner";
                break;
            case 3:
                asmType = "Corner 45";
                break;
            case 4:
                asmType = "Corner 90";
                break;
            case 5:
                asmType = "Peninsula";
                break;
            case 6:
                asmType = "Lazy Susan";
                break;
            case 7:
                asmType = "Pantry";
                break;
            case 8:
                asmType = "End";
                break;
            case 9:
                asmType = "Blind Left";
                break;
            case 10:
                asmType = "Blind Right";
                break;
            case 11:
                asmType = "Filler";
                break;
            default:
                asmType = "NA";
                break;
        }

        return asmType;
    }

    private String convertAsmClassInt(int asmClass) {
        String classString;
        switch (asmClass) {
            case 1:
                classString = "Base";
                break;
            case 2:
                classString = "Upper";
                break;
            case 3:
                classString = "Tall";
                break;
            case 4:
                classString = "Vanity";
                break;
            case 5:
                classString = "Sink";
                break;
            case 6:
                classString = "Split Level";
                break;
            default:
                classString = "NA";
                break;
        }

        return classString;
    }
    private void processPartData(Connection connection, Integer asmId, MesCabinet cabinet) {
        try {
            Map<Integer, MesPart> partData = parsePartInfoForAssembly(connection, asmId);

            cabinet.setParts(new ArrayList<>(partData.values()));

        } catch (Exception ex) {
            mdb_reader_logger.error("Fail to process assembly ID {} ", asmId, ex);
        }
    }
    private void parsePartPrimaryMachine(Connection connection, Map<Integer, MesPart> parts) throws Exception{
        Statement s = connection.createStatement();
        ResultSet rs = s.executeQuery("Select PartID, OutputPath " +
                "FROM [PartRunData] " +
                "LEFT JOIN [RunInfo] ON [PartRunData].RunID = [RunInfo].ID " +
                "WHERE [PartRunData].Output IN (3, 259) " +
                "GROUP BY PartID, OutputPath ORDER BY PartID");

        Map<Integer, String> machineMap = new HashMap<>();
        while (rs.next()) {
            String outputPath = rs.getString("OutputPath");
            Integer partID = rs.getInt("PartID");

            // Check output of for the machine name
            if (outputPath != null) {
                String machineName = MesConfig.getPrimaryMachineNames().stream()
                        .filter(str -> outputPath.contains(str))
                        .findFirst().orElse(null);

                if (machineName != null) {
                    if (!machineMap.containsKey(partID)) {
                        machineMap.put(partID, machineName);
                    }
                }
            }
        }

        // Update machine name for part
        machineMap.forEach((k,v) -> {
            if (parts.get(k) != null) {
                parts.get(k).setPrimaryMachineName(v);
            }
        });
    }
    private void parsePartEdgeData(Connection connection, Map<Integer, MesPart> parts) throws Exception {
        Statement s = connection.createStatement();
        ResultSet rs = s.executeQuery("Select PartID, Indicator, Material FROM [EdgeBanding] " +
                "LEFT JOIN [Parts] ON [EdgeBanding].PartID = [Parts].ID " +
                "GROUP BY PartId, Indicator, Material " +
                "ORDER BY [EdgeBanding].PartID");

        Map<Integer, List<String>> edgeMap = new HashMap<>();
        while (rs.next()) {
            Integer refPartID = rs.getInt("PartID");
            String indicator = rs.getString("Indicator");
            String material = rs.getString("Material");

            String edgeMaterial = indicator + SEPARATOR + material;

            if (!edgeMap.containsKey(refPartID)) {
                edgeMap.put(refPartID, new ArrayList<>(Arrays.asList(edgeMaterial)));
            } else {
                boolean found = edgeMap.get(refPartID).stream()
                        .anyMatch(data -> {
                            String edge = StringUtils.substringBefore(data, SEPARATOR);
                            return edge.equalsIgnoreCase(indicator);
                        });

                if (!found) {
                    edgeMap.get(refPartID).add(edgeMaterial);
                }
            }
        }

        // Set Edge Value to Part
        for(Integer refPartId : edgeMap.keySet()) {
            if (parts.containsKey(refPartId)) {
                List<String> edgeMaterialList = edgeMap.get(refPartId);
                String edgeMaterial = String.join(", ", edgeMaterialList);
                parts.get(refPartId).setEdgeMaterial(edgeMaterial);
            }
        }
    }
    private Map<Integer, MesPart> parsePartInfoForAssembly(Connection connection, Integer asmId) throws Exception {
        Statement s = connection.createStatement();
        ResultSet rs = s.executeQuery("SELECT Parts.Name, Parts.ID, " +
                "PartRunData.FrProgram, BandingFlags, InternalName, [Materials].Name AS MaterialName, " +
                "[Materials].Thick AS MaterialThickness, Parts.Qty, PrimaryEdge, AssemblyID, Orientation, " +
                "PartRunData.BaProgram, PartRunData.Image, PartRunData.CutWidth, PartRunData.CutLength, " +
                "PartRunData.BandedWidth, PartRunData.BandedLength, PartRunData.ImageName, PartWorkflow.Workflow" +
                " FROM [PartRunData] " +
                "LEFT JOIN [Parts] ON [Parts].ID = PartRunData.PartID " +
                "LEFT JOIN [PartWorkflow] ON PartRunData.PartID = [PartWorkflow].PartID " +
                "LEFT JOIN [Materials] ON [Parts].MatDBID = [Materials].MatDBID " +
                "WHERE AssemblyID = " + asmId +
                "ORDER BY Parts.ID, PartRunData.RunID");

        Map<Integer, MesPart> partMap = new HashMap<>();
        while (rs.next()) {
            Integer partID = rs.getInt("ID");
            String name = rs.getString("Name");
            String materialName = rs.getString("MaterialName");
            Double materialThickness = rs.getDouble("MaterialThickness");
            String internalName = rs.getString("InternalName");
            String bandingFlags = rs.getString("BandingFlags");

            String frProgram = rs.getString("FrProgram");
            String baProgram = rs.getString("BaProgram");
            Double cutWidth = rs.getDouble("CutWidth");
            Double cutLength = rs.getDouble("CutLength");
            Double bandedWidth = rs.getDouble("BandedWidth");
            Double bandedLength = rs.getDouble("BandedLength");
            String workflow = rs.getString("Workflow");
            Integer qty = rs.getInt("Qty");
            Integer primaryEdge = rs.getInt("PrimaryEdge");
            Integer assemblyID = rs.getInt("AssemblyID");
            Integer orientation = rs.getInt("Orientation");

            String imageName = rs.getString("ImageName");
            byte[] image = rs.getBytes("Image");


            MesPart partEntity = new MesPart();
            partEntity.setPartRefId(partID);
            partEntity.setQty(qty);
            partEntity.setPrimaryEdge(primaryEdge);
            partEntity.setBackProgram(baProgram);
            partEntity.setBandedLength(toMillimeters(bandedLength));
            partEntity.setBandedWidth(toMillimeters(bandedWidth));
            partEntity.setBandingFlags(bandingFlags);
            partEntity.setCutLength(toMillimeters(cutLength));
            partEntity.setCutWidth(toMillimeters(cutWidth));
            partEntity.setFrontProgram(frProgram);
            if (image != null) {
                partEntity.setImageInByte(image);
            }

            // Only take the name
            if (imageName != null) {
                String[] tokens = imageName.split("[\\\\|/]");
                imageName = tokens[tokens.length - 1];
                partEntity.setImageName(imageName);
            }

            partEntity.setMaterialThickness(toMillimeters(materialThickness));
            partEntity.setName(name);
            partEntity.setPanelMaterial(materialName);
            partEntity.setMaterialThickness(toMillimeters(materialThickness));
            partEntity.setWorkflow(workflow);
            partEntity.setShortName(internalName);


            Map<String, String> orientationMapping = convertOrientation(orientation);
            partEntity.setFrontOrientation(orientationMapping.get(MesPart.ORIENTATION_FACE));
            partEntity.setBackOrientation(orientationMapping.get(MesPart.ORIENTATION_BACK));

            // Insert them into the map
            if (!partMap.containsKey(partID)) {
                partMap.put(partID, partEntity);
            } else {
                // Need update the front program
                if (frProgram != null && frProgram.trim().length() > 1) {
                    partMap.get(partID).setFrontProgram(frProgram);
                }

                // Need update the back program
                if (baProgram != null && baProgram.trim().length() > 1) {
                    partMap.get(partID).setBackProgram(baProgram);
                }

                // Need update the orientation
                if (orientation > 0) {
                    orientationMapping = convertOrientation(orientation);
                    partMap.get(partID).setFrontOrientation(orientationMapping.get(MesPart.ORIENTATION_FACE));
                    partMap.get(partID).setBackOrientation(orientationMapping.get(MesPart.ORIENTATION_BACK));
                }
            }
        }

        // Do edge materials
        parsePartEdgeData(connection, partMap);
        // Do primary machine
        parsePartPrimaryMachine(connection, partMap);

        return partMap;
    }
    private List<MesPattern> parsePattern(Connection connection) throws Exception {
        Statement s = connection.createStatement();
        ResultSet rs = s.executeQuery("Select Distinct [Patterns].ID, [RunInfo].RunTag, PrimaryProgram, PrimaryBarcode, SixthFace, " +
                "SixthFaceProgram, SixthFaceBarcode, [Patterns].Image, [Materials].Name AS MaterialName " +
                " FROM [Patterns]" +
                " LEFT JOIN [Cuts] ON [Patterns].ID = [Cuts].PatternID" +
                " LEFT JOIN [Materials] ON [Cuts].MaterialID = [Materials].ID " +
                " LEFT JOIN [RunInfo] ON [Patterns].RunID = [RunInfo].ID");

        List<MesPattern> patternEntities = new ArrayList<>();
        while (rs.next()) {
            Integer refPatternId = rs.getInt("ID");
            String runTag = rs.getString("RunTag");
            String materialName = rs.getString("MaterialName");
            String primaryProgram = rs.getString("PrimaryProgram");
            String primaryBarcode = rs.getString("PrimaryBarcode");
            String sixthFaceProgram = rs.getString("SixthFaceProgram");
            String sixthFaceBarcode = rs.getString("SixthFaceBarcode");
            Boolean sixthFace = rs.getBoolean("SixthFace");

            byte[] image = rs.getBytes("Image");

            MesPattern patternEntity = new MesPattern();
            patternEntity.setSixthFace(sixthFace);
            patternEntity.setRunTag(runTag);
            patternEntity.setPatternRefId(refPatternId);
            patternEntity.setPrimaryProgram(primaryProgram);
            patternEntity.setPrimaryBarcode(primaryBarcode);
            patternEntity.setSixthFaceProgram(sixthFaceProgram);
            patternEntity.setSixthFaceBarcode(sixthFaceBarcode);

            patternEntity.setMaterialName(materialName);
            patternEntity.setImageInByte(image);

            patternEntities.add(patternEntity);

        }

        return patternEntities;
    }
    private List<MesPanelLayout> parsePanelLayout(Connection connection) throws Exception {
        Statement s = connection.createStatement();
        ResultSet rs = s.executeQuery("Select [PanelLayout].PartID, [Cuts].PatternID, PartIndex, FaceUp, 2ndProgram " +
                " FROM [PanelLayout]" +
                " LEFT JOIN [Cuts] ON [Cuts].ID = [PanelLayout].CutID" +
                " LEFT JOIN [Patterns] ON [Patterns].ID = [Cuts].PatternID" +
                " ORDER BY [Cuts].PatternID, PartIndex");

        List<MesPanelLayout> panelLayoutEntities = new ArrayList<>();
        while (rs.next()) {
            Integer refPartId = rs.getInt("PartID");
            Integer refPatternId = rs.getInt("PatternID");
            Integer partIndex = rs.getInt("PartIndex");
            Boolean faceUp = rs.getBoolean("FaceUp");
            Boolean secondProgram = rs.getBoolean("2ndProgram");

            MesPanelLayout panelLayoutEntity = new MesPanelLayout();

            panelLayoutEntity.setPartIndexOnPattern(partIndex);
            panelLayoutEntity.setPatternRefId(refPatternId);
            panelLayoutEntity.setPartRefId(refPartId);

            panelLayoutEntities.add(panelLayoutEntity);
        }

        return panelLayoutEntities;
    }
}
