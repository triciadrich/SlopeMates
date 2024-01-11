package com.example.slopemates.Models;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.*;
import java.util.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

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
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Setter
    private Date dob;
    @Setter
    private Integer age;
    @NotNull
    private String city;
    @NotNull
    @Size(min = 2, max = 2, message = "Please enter 2 character state abbreviation")
    private String state;
    @NotNull
    private String skillLevel;
    @NotNull
    private String snowSport;
    private String style;
    private String bio;

    private String fileName;
    private String fileType;
    @Lob
    private byte[] data;
    @Transient
    private String downloadUrl;



    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_connection",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "connection_id")
    )
    private List<User> connections;



    @Transient
    public String getBase64Image() {
        if (data != null && data.length > 0) {
            return Base64.getEncoder().encodeToString(data);
        }
        return null;
    }


    public void setAge(Integer age) {

        Calendar today = Calendar.getInstance();
        Calendar birth = Calendar.getInstance();
        birth.setTime(dob);
    this.age = today.get(Calendar.YEAR) - birth.get(Calendar.YEAR);

    }



}