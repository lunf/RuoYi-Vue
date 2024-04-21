package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.SysNotice;

/**
 * Notification form The Data Layer
 * 
 * @author ruoyi
 */
public interface SysNoticeMapper
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
     * Remove the announcement.
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
