package com.scaudachuang.campus_navigation.service.impl;

import com.scaudachuang.campus_navigation.DAO.NoticeDAO;
import com.scaudachuang.campus_navigation.entity.Notice;
import com.scaudachuang.campus_navigation.service.NoticeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class NoticeServerImpl implements NoticeService {

    @Resource
    private NoticeDAO noticeDAO;

    @Override
    public void addNotice(String string) {
        Notice notice = new Notice();
        notice.setString(string);
        notice.setTime(new Date());
        noticeDAO.save(notice);
    }
}
