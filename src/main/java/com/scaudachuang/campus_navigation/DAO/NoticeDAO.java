package com.scaudachuang.campus_navigation.DAO;

import com.scaudachuang.campus_navigation.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @author best lu
 */
@Repository
public interface NoticeDAO extends JpaRepository<Notice,Integer> {

}
