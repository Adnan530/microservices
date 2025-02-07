package com.microservices.Rating.repository;

import com.microservices.Rating.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating,Long> {

    List<Rating> findByUserId(Long userId);

}
