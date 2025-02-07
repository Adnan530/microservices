package com.microservices.User.service;

import com.microservices.User.externalService.RatingService;
import com.microservices.User.repository.UserRepository;
import com.microservices.User.dto.UserDto;
import com.microservices.User.entity.User;
import com.microservices.User.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final RestTemplate restTemplate;
    private final RatingService ratingService;

    //private Logger logger= (Logger) LoggerFactory.getLogger(UserService.class);

    // Constructor injection for UserRepository
    @Autowired
    public UserService(UserRepository userRepository, RestTemplate restTemplate, RatingService
                       ratingService) {
        this.userRepository = userRepository;
        this.restTemplate=restTemplate;
        this.ratingService=ratingService;
    }

    @Override
    public UserDto create(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setAddress(userDto.getAddress());
        user.setEmail(userDto.getEmail());
        user = userRepository.save(user);
        return new UserDto(user.getId(), user.getName(), user.getAddress(), user.getEmail());
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> new UserDto(user.getId(), user.getName(), user.getAddress(), user.getEmail()))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<UserDto> getById(Long id) {
        return userRepository.findById(id)
                .map(user -> new UserDto(user.getId(), user.getName(), user.getAddress(), user.getEmail()));
    }

    /*
    /* Using Rest Template
     */


    @Override
    public User getUserById(Long id) {
        User user=userRepository.findById(id).orElseThrow(()-> new UserNotFoundException("User with id " + id + " not found"));
        ArrayList forObject = restTemplate.getForObject("http://RATING-SERVICE/api/ratings/users/"+user.getId(), ArrayList.class);
        user.setRatings(forObject);
        return user;
    }

    /*
        Using Feign Client
     */
    /*
    @Override
    public User getUserById(Long id) {
        User user=userRepository.findById(id).orElseThrow(()-> new UserNotFoundException("User with id " + id + " not found"));
        ArrayList forObject = restTemplate.getForObject("http://RATING-SERVICE/api/ratings/users/"+user.getId(), ArrayList.class);
        ArrayList for Object= ratingService.getRating(user.getRatings(), ArrayList.class);
        user.setRatings(forObject);
        return user;
    }
    */


    @Override
    public UserDto update(Long id, UserDto userDTO) {
        User userOpt = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found"));

        // Update only the fields that are not null in the DTO
        if (userDTO.getName() != null) {
            userOpt.setName(userDTO.getName());
        }
        if (userDTO.getAddress() != null) {
            userOpt.setAddress(userDTO.getAddress());
        }
        if (userDTO.getEmail() != null) {
            userOpt.setEmail(userDTO.getEmail());
        }
        userOpt = userRepository.save(userOpt);
            return new UserDto(userOpt.getId(), userOpt.getName(), userOpt.getAddress(), userOpt    .getEmail());

    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
