package com.example.slopemates.Models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.awt.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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
    @Size(min = 5, message = "Password must be greater than 5 characters")
    private String password;
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
    private String style;
    private String bio;

    private byte imageData;


    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_connection",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "connection_id")
    )
    private Set<User> connections = new HashSet<>();





}
