package com.ruoyi.mes.domain.work;

import lombok.Data;

import java.io.Serializable;

@Data
public class MesPanelLayout implements Serializable {

    /** Primary key */
    private Long panelLayoutId;

    /** Reference to Work Order table */
    private Long workOrderId;

    /** Reference to Part table */
    private Long partId;

    /** Original reference to Part - no used in db */
    private Integer partRefId;

    /** Reference to Pattern table */
    private Long patternId;

    /** Original reference to Pattern - no used in db */
    private Integer patternRefId;

    /** Position of part which is on the pattern */
    private Integer partIndexOnPattern;
}
