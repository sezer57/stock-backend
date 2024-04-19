package com.example.demo.Dto;

import lombok.*;

@Data
@Getter
@Setter
public class AuthRequestDto {

    private String username;
    private String password;

}