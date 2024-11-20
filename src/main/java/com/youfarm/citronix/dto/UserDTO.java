package com.youfarm.citronix.dto;

import jakarta.validation.constraints.Email;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private Integer age;

    @Email
    private String email;

    private String password;

    private String phone;

}
