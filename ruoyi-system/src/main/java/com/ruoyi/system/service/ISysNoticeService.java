package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.SysNotice;

/**
 * announcement of service.
 * 
 * @author ruoyi
 */
public interface ISysNoticeService
{
    /**
     * Ask for announcement information
     * 
     * @param noticeId announcementID
     * @return announcement information
     */
    public SysNotice selectNoticeById(Long noticeId);

    /**
     * List of inquiries
     * 
     * @param notice announcement information
     * @return Advertising collection
     */
    public List<SysNotice> selectNoticeList(SysNotice notice);

    /**
     * Added announcements
     * 
     * @param notice announcement information
     * @return Results
     */
    public int insertNotice(SysNotice notice);

    /**
     * Modified announcement
     * 
     * @param notice announcement information
     * @return Results
     */
    public int updateNotice(SysNotice notice);

    /**
     * Delete the announcement information.
     * 
     * @param noticeId announcementID
     * @return Results
     */
    public int deleteNoticeById(Long noticeId);
    
    /**
     * Remove publication information.
     * 
     * @param noticeIds Notifications to be deleted.ID
     * @return Results
     */
    public int deleteNoticeByIds(Long[] noticeIds);
}
