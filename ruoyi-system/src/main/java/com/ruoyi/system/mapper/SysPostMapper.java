package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.SysPost;

/**
 * Job Information The Data Layer
 * 
 * @author ruoyi
 */
public interface SysPostMapper
{
    /**
     * Question of job data collection
     * 
     * @param post Job Information
     * @return Data collection of jobs
     */
    public List<SysPost> selectPostList(SysPost post);

    /**
     * Find all posts.
     * 
     * @return List of jobs
     */
    public List<SysPost> selectPostAll();

    /**
     * through the job.IDAsk for job information.
     * 
     * @param postId The jobID
     * @return The role object information
     */
    public SysPost selectPostById(Long postId);

    /**
     * According to UsersIDSelect the job list.
     * 
     * @param userId UsersID
     * @return Choose a position.IDList of
     */
    public List<Long> selectPostListByUserId(Long userId);

    /**
     * Ask for the employment group of users.
     * 
     * @param userName User Name
     * @return Results
     */
    public List<SysPost> selectPostsByUserName(String userName);

    /**
     * Delete Job Information
     * 
     * @param postId The jobID
     * @return Results
     */
    public int deletePostById(Long postId);

    /**
     * Delete the job information.
     * 
     * @param postIds Posts to be removed.ID
     * @return Results
     */
    public int deletePostByIds(Long[] postIds);

    /**
     * Modification of Job Information
     * 
     * @param post Job Information
     * @return Results
     */
    public int updatePost(SysPost post);

    /**
     * Additional Job Information
     * 
     * @param post Job Information
     * @return Results
     */
    public int insertPost(SysPost post);

    /**
     * Examination Job Name
     * 
     * @param postName Employment Name
     * @return Results
     */
    public SysPost checkPostNameUnique(String postName);

    /**
     * Training job code.
     * 
     * @param postCode Job Code
     * @return Results
     */
    public SysPost checkPostCodeUnique(String postCode);
}
