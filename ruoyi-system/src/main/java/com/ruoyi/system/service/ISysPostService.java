package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.SysPost;

/**
 * Job Information of service.
 * 
 * @author ruoyi
 */
public interface ISysPostService
{
    /**
     * Question of Job Information Collection
     * 
     * @param post Job Information
     * @return List of jobs
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
     * Examination Job Name
     * 
     * @param post Job Information
     * @return Results
     */
    public boolean checkPostNameUnique(SysPost post);

    /**
     * Training job code.
     * 
     * @param post Job Information
     * @return Results
     */
    public boolean checkPostCodeUnique(SysPost post);

    /**
     * through the job.IDQuestion of employment number
     * 
     * @param postId The jobID
     * @return Results
     */
    public int countUserPostById(Long postId);

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
     * Additional storage information.
     * 
     * @param post Job Information
     * @return Results
     */
    public int insertPost(SysPost post);

    /**
     * Modification of employment information
     * 
     * @param post Job Information
     * @return Results
     */
    public int updatePost(SysPost post);
}
