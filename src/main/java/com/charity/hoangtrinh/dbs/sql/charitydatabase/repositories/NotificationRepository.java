package com.charity.hoangtrinh.dbs.sql.charitydatabase.repositories;

import com.charity.hoangtrinh.dbs.sql.charitydatabase.entities.Notification;
import com.charity.hoangtrinh.dbs.sql.charitydatabase.entities.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    List<Notification> findByUserEquals(UserAccount user);
}