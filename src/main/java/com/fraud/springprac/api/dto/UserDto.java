package com.fraud.springprac.api.dto;

import com.fraud.springprac.api.model.Role;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserDto {
    private int id;
    private String username;
    private String password;
    private List<Role> roles = new ArrayList<>();
}
