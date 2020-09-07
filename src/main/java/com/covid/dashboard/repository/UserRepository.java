package com.covid.dashboard.repository;

import com.covid.dashboard.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,String> {

    User findUserByVerificationCode(String verificationCode);

    List<User> findFirst10UserByFirstNameStartsWith(String name);

}
