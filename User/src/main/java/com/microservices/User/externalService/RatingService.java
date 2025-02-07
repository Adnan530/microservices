package com.microservices.User.externalService;
import com.microservices.User.entity.Rating;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name ="RATING-SERVICE")
public interface RatingService {

    //GET API
    @GetMapping("/ratings/{ratingId}")
    Rating getRating(@PathVariable Long ratingId);

    //POST API
    @PostMapping("/ratings")
    Rating createRating(Rating values);

    //PUT API
    @PutMapping("/ratings/{id}")
    Rating putRating(@PathVariable Long id,Rating rating);

    //DELETE API
    @DeleteMapping("/ratings/{id}")
    void deleteRating(@PathVariable Long id);

}
