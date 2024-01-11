package com.example.slopemates.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SearchCriteria {
    private Integer minAge;
    private Integer maxAge;
    private String gender;
    private String city;
    private String state;
    private String skillLevel;
    private String snowSport;
    private String style;

}
