package com.microservices.User.repository;

import com.microservices.User.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository< User, Long> {

}
