package com.charity.hoangtrinh.dbs.sql.charitydatabase.repositories;

import com.charity.hoangtrinh.dbs.sql.charitydatabase.entities.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAccountRepository extends JpaRepository<UserAccount, Integer> {
}