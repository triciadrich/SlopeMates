package com.example.slopemates.Models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.awt.*;
import java.util.Date;

@Entity
@Table(name = "user")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String userName;
    @Column(unique = true)
    @Email(message = "Email must be valid")
    @NotNull
    private String email;
    @NotNull
    private String gender;
    @NotNull
    private Date dob;
    @NotNull
    private String city;
    @NotNull
    private String state;
    @NotNull
    private String skillLevel;
    @NotNull
    private String snowSport;
    private String bio;

    private byte imageData;





}
