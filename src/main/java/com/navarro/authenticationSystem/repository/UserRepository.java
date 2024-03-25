package com.navarro.authenticationSystem.repository;

import com.navarro.authenticationSystem.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    User findByUserName(String userName);
}
