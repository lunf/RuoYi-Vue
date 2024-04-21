package com.ruoyi.system.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.annotation.Excel.ColumnType;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * The Job Table sys_post
 * 
 * @author ruoyi
 */
public class SysPost extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** Number of position */
    @Excel(name = "Number of position", cellType = ColumnType.NUMERIC)
    private Long postId;

    /** Job Code */
    @Excel(name = "Job Code")
    private String postCode;

    /** Employment Name */
    @Excel(name = "Employment Name")
    private String postName;

    /** Ranking of jobs */
    @Excel(name = "Ranking of jobs")
    private Integer postSort;

    /** state of（0Normal 1stopped） */
    @Excel(name = "state of", readConverterExp = "0=Normal,1=stopped")
    private String status;

    /** Does the user have this job ID? There is no presumption. */
    private boolean flag = false;

    public Long getPostId()
    {
        return postId;
    }

    public void setPostId(Long postId)
    {
        this.postId = postId;
    }

    @NotBlank(message = "Job code cannot be empty.")
    @Size(min = 0, max = 64, message = "The length of the job code cannot exceed64A character.")
    public String getPostCode()
    {
        return postCode;
    }

    public void setPostCode(String postCode)
    {
        this.postCode = postCode;
    }

    @NotBlank(message = "The job name cannot be empty.")
    @Size(min = 0, max = 50, message = "The length of the job name shall not exceed50A character.")
    public String getPostName()
    {
        return postName;
    }

    public void setPostName(String postName)
    {
        this.postName = postName;
    }

    @NotNull(message = "Showing order cannot be empty.")
    public Integer getPostSort()
    {
        return postSort;
    }

    public void setPostSort(Integer postSort)
    {
        this.postSort = postSort;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public boolean isFlag()
    {
        return flag;
    }

    public void setFlag(boolean flag)
    {
        this.flag = flag;
    }
    
    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("postId", getPostId())
            .append("postCode", getPostCode())
            .append("postName", getPostName())
            .append("postSort", getPostSort())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
