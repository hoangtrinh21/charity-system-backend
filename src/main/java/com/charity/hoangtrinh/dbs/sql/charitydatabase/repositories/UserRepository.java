package com.charity.hoangtrinh.dbs.sql.charitydatabase.repositories;

import com.charity.hoangtrinh.dbs.sql.charitydatabase.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findByRole_IdEquals(int i);
}