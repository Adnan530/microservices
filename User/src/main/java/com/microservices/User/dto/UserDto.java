package com.microservices.User.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UserDto {

    private Long id;

    private String name;

    private String address;

    private String email;

}
