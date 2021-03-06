package com.ruoyi.med.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * Material object med_material
 *
 * @author cuong
 * @date 2021-03-06
 */
public class MedMaterial extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** Primary key */
    private Long materialId;

    /** Name */
    @Excel(name = "Name")
    private String name;

    /** Description */
    @Excel(name = "Description")
    private String description;

    /** Barcode */
    private String barcode;

    /** Material supplier */
    @Excel(name = "Material supplier")
    private String supplier;

    /** Stock status */
    @Excel(name = "Stock status")
    private Integer status;

    /** Qty */
    @Excel(name = "Qty")
    private Double qty;

    /** Unit */
    @Excel(name = "Unit")
    private String unit;

    public void setMaterialId(Long materialId)
    {
        this.materialId = materialId;
    }

    public Long getMaterialId()
    {
        return materialId;
    }
    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getDescription()
    {
        return description;
    }
    public void setBarcode(String barcode)
    {
        this.barcode = barcode;
    }

    public String getBarcode()
    {
        return barcode;
    }
    public void setSupplier(String supplier)
    {
        this.supplier = supplier;
    }

    public String getSupplier()
    {
        return supplier;
    }
    public void setStatus(Integer status)
    {
        this.status = status;
    }

    public Integer getStatus()
    {
        return status;
    }
    public void setQty(Double qty)
    {
        this.qty = qty;
    }

    public Double getQty()
    {
        return qty;
    }
    public void setUnit(String unit)
    {
        this.unit = unit;
    }

    public String getUnit()
    {
        return unit;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("materialId", getMaterialId())
                .append("name", getName())
                .append("description", getDescription())
                .append("barcode", getBarcode())
                .append("supplier", getSupplier())
                .append("status", getStatus())
                .append("qty", getQty())
                .append("unit", getUnit())
                .toString();
    }
}