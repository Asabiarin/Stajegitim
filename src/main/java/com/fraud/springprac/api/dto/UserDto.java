package com.fraud.springprac.api.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

@Data
public class UserDto {
    @JsonAlias({"tckn", "tc", "tcno"})
    private int id;
    private String firstName;
    private String lastName;
    private int age;
    private String email;

}
