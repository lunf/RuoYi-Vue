package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.domain.SysNotice;
import com.ruoyi.system.mapper.SysNoticeMapper;
import com.ruoyi.system.service.ISysNoticeService;

/**
 * announcement Service level achieved
 * 
 * @author ruoyi
 */
@Service
public class SysNoticeServiceImpl implements ISysNoticeService
{
    @Autowired
    private SysNoticeMapper noticeMapper;

    /**
     * Ask for announcement information
     * 
     * @param noticeId announcementID
     * @return announcement information
     */
    @Override
    public SysNotice selectNoticeById(Long noticeId)
    {
        return noticeMapper.selectNoticeById(noticeId);
    }

    /**
     * List of inquiries
     * 
     * @param notice announcement information
     * @return Advertising collection
     */
    @Override
    public List<SysNotice> selectNoticeList(SysNotice notice)
    {
        return noticeMapper.selectNoticeList(notice);
    }

    /**
     * Added announcements
     * 
     * @param notice announcement information
     * @return Results
     */
    @Override
    public int insertNotice(SysNotice notice)
    {
        return noticeMapper.insertNotice(notice);
    }

    /**
     * Modified announcement
     * 
     * @param notice announcement information
     * @return Results
     */
    @Override
    public int updateNotice(SysNotice notice)
    {
        return noticeMapper.updateNotice(notice);
    }

    /**
     * Remove the advertising object.
     * 
     * @param noticeId announcementID
     * @return Results
     */
    @Override
    public int deleteNoticeById(Long noticeId)
    {
        return noticeMapper.deleteNoticeById(noticeId);
    }

    /**
     * Remove publication information.
     * 
     * @param noticeIds Notifications to be deleted.ID
     * @return Results
     */
    @Override
    public int deleteNoticeByIds(Long[] noticeIds)
    {
        return noticeMapper.deleteNoticeByIds(noticeIds);
    }
}
