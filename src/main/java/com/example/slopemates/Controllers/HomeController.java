package com.example.slopemates.Controllers;

import com.example.slopemates.Services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    private UserService userService;

    @RequestMapping("/")
    public String index(){
        return"index";
    }
}
