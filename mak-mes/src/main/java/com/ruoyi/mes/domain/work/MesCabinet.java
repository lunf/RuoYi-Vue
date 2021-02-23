package com.ruoyi.mes.domain.work;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class MesCabinet implements Serializable {

    private Long cabinetId;

    private Long workOrderId;
    /** Predefined code for the cabinet **/
    private String code;

    /** Name of cabinet **/
    private String name;

    /** Description of the cabinet **/
    private String description;

    /** Assembly class of the cabinet (e.g: base, upper) **/
    private String asmClass;

    /** Type of them assembly (e.g: standard, corner, filler…) **/
    private String asmType;

    /** A value representing the Width of the Cabinet/Assembly */
    private Double width;

    /** A value representing the Depth of the Cabinet/Assembly */
    private Double depth;

    /** A value representing the Height of the Cabinet/Assembly. */
    private Double height;

    /** Product code which this assembly/cabinet belong to */
    private String productCode;

    /** Product name which this assembly/cabinet belong to */
    private String productName;

    /** Linking the Cabinet/Assembly to a Room */
    private String roomNum;

    /** Parts that are linked to the cabinet */
    private List<MesPart> parts;
}
