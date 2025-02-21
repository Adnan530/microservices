package com.microservices.User.controller;

import com.microservices.User.entity.User;
import com.microservices.User.exceptions.UserNotFoundException;
import com.microservices.User.service.IUserService;
import com.microservices.User.dto.UserDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final IUserService userService;

    // Constructor injection for IUserService
    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserDto> create(@RequestBody UserDto userDTO) {
        UserDto createdUser = userService.create(userDTO);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> update(@PathVariable Long id, @RequestBody UserDto userDTO) {
        UserDto updatedUser = userService.update(id, userDTO);
        return updatedUser != null ? ResponseEntity.ok(updatedUser) : ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        UserDto updatedUser = userService.update(id, userDto);
        return updatedUser != null ? ResponseEntity.ok(updatedUser) : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAll() {
        List<UserDto> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getById(@PathVariable Long id) {
        Optional<UserDto> userDTO = userService.getById(id);
        return userDTO.map(ResponseEntity::ok).orElseThrow(()-> new UserNotFoundException("User with id " + id + " not found"));
    }

    // This method is used for Circuit Breaker
    /*
    @GetMapping("/rating/{id}")
    @CircuitBreaker(name = "ratingHotelBreaker", fallbackMethod = "ratingHotelFallback" ) //fallBackMethod property is used to call that method when the current service is down
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserById(id));
    }
     */

    int retryCount=1;
    @GetMapping("/rating/{id}")
    @Retry(name = "ratingHotelRetry")
    @CircuitBreaker(name = "ratingHotelBreaker") //fallBackMethod property is used to call that method when the current service is down
    @RateLimiter(name = "userRateLimiter")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        logger.info("Get Single User Handler: UserController");
        logger.info("Retry count: {}",retryCount);
        retryCount++;
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserById(id));
    }

    //fallback method for circuit breakers and retry mechanism
    public ResponseEntity<User> ratingHotelFallback(Long id, Exception exception){
      //  logger.info("Fallback executed due to exception: {}", exception.getMessage());
        User user = User.builder()
                .name("adnan")
                .email("abc@gmail.com")
                .address("karachi").
                id(3L).build();
        return new ResponseEntity<>(user,HttpStatus.OK);
    }
    //fallback method for circuit breakers and retry mechanism

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
