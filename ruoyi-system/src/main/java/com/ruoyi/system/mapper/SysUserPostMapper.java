package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.SysUserPost;

/**
 * User and Job Relationships The Data Layer
 * 
 * @author ruoyi
 */
public interface SysUserPostMapper
{
    /**
     * through the userIDRemove User and Job Relationships
     * 
     * @param userId UsersID
     * @return Results
     */
    public int deleteUserPostByUserId(Long userId);

    /**
     * through the job.IDQuestion of employment number
     * 
     * @param postId The jobID
     * @return Results
     */
    public int countUserPostById(Long postId);

    /**
     * Mass removal of users and job connections
     * 
     * @param ids Data needed to be deleted.ID
     * @return Results
     */
    public int deleteUserPost(Long[] ids);

    /**
     * Additional User Job Information
     * 
     * @param userPostList User Job List
     * @return Results
     */
    public int batchUserPost(List<SysUserPost> userPostList);
}
