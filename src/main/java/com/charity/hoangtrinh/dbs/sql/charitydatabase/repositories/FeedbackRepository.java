package com.charity.hoangtrinh.dbs.sql.charitydatabase.repositories;

import com.charity.hoangtrinh.dbs.sql.charitydatabase.entities.Feedback;
import com.charity.hoangtrinh.dbs.sql.charitydatabase.entities.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {
    List<Feedback> findByUserAccountEquals(UserAccount userAccount);
}