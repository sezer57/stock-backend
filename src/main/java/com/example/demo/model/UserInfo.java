package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Data
@Getter
@Setter
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String email;
    private String password;
    private String roles;

}
