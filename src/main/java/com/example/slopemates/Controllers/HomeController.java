package com.example.slopemates.Controllers;

import com.example.slopemates.Models.User;
import com.example.slopemates.Services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class HomeController {
    private UserService userService;

    @RequestMapping("/")
    public String index(){
        return"index";
    }

    @GetMapping("/join")
    public String signUp(@ModelAttribute("User")User user, Model model){

        return "signUp";
    }

    @PostMapping("/reg")
    public String register(@Valid @ModelAttribute("user") User user, BindingResult result, HttpSession session){
        if(result.hasErrors()){
            return "signUp";
        }
        User u = userService.registerUser(user);
        session.setAttribute("userId", u.getId());
        return "dashBoard";
    }

    @GetMapping("/userProfile")
    public String UserProfile(@ModelAttribute("user") User user, HttpSession session, Model model){
        if(session.getAttribute("userId") == null){
            return "index";
        }
        Long userId =(Long) session.getAttribute("userId");
        User u = userService.findByUserId(userId);
        model.addAttribute("user", u);
        return "profile";
    }


}
