package com.youfarm.citronix.dto;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class UserReturnDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private Integer age;
    private String roleName;
}
