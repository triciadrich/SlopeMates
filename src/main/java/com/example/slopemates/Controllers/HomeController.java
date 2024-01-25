package com.example.slopemates.Controllers;

import com.example.slopemates.Models.SearchCriteria;
import com.example.slopemates.Models.User;
import com.example.slopemates.Repositories.UserRepository;
import com.example.slopemates.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Controller
public class HomeController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/")
    public String index(Model model){
        model.addAttribute("user", new User());
        return"index";
    }

    @RequestMapping ("/join")
    public String signUp(@ModelAttribute("user")User user, Model model){
    model.addAttribute("user", user);

        return "signUp";
    }

    @PostMapping("/reg")
    public String register(@Valid @ModelAttribute("user") User user, BindingResult result, HttpSession session, @RequestParam("data") MultipartFile file){
        User u;

//        todo: move to userservice
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        try {

            byte [] fileData = file.getBytes();

                    user.setData(fileData);
                    user.setFileName(fileName);
                    user.setFileType(file.getContentType());
                    user.setId(user.getId());
                   user.setUserName(user.getUserName());
                   user.setEmail(user.getEmail());
                   user.setPassword(user.getPassword());
                   user.setGender(user.getGender());
                   user.setDob(user.getDob());
                   user.setAge(user.getAge());
                   user.setCity(user.getCity());
                   user.setState(user.getState());
                   user.setSkillLevel((user.getSkillLevel()));
                   user.setSnowSport(user.getSnowSport());
                   user.setStyle(user.getStyle());
                   user.setBio(user.getBio());





          u=  userService.registerUser(user);
            u.setDownloadUrl(ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/images/")
                    .path(String.valueOf(u.getId()))
                    .toUriString());
            session.setAttribute("userId", u.getId());
        } catch (IOException e) {
            if(result.hasErrors()){
                System.out.println(result);
                return "redirect:/join";
            }
            throw new RuntimeException(e);


        }





        return "redirect:/userProfile";
    }

    @GetMapping("/userProfile")
    public String UserProfile(@ModelAttribute("user") User user, HttpSession session, Model model){
        if(session.getAttribute("userId") == null){//get the user in session
            return "index";
        }
        Long userId =(Long) session.getAttribute("userId");//adds user to modelattribute to be view in template
        User u = userService.findByUserId(userId);
        model.addAttribute("user", u);




        return "profile";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("user")User user, Model model, HttpSession session){

        String error="Invalid";
        boolean isAuthenticated = userService.authUser(user.getEmail(), user.getPassword());
        if (isAuthenticated){
            User u = userService.findByEmail(user.getEmail());
            session.setAttribute("userId", u.getId());
            return "redirect:/userProfile";
        }else{
            model.addAttribute("error", error);
            System.out.println(error);
            return "redirect:/";
        }
    }

    @GetMapping("/searchForm")
    public String showSearch(Model model, User u, HttpSession session, SearchCriteria criteria){

        if(session.getAttribute("userId") == null){
            return "index";
        }
        Long userId =(Long) session.getAttribute("userId");
        u = userService.findByUserId(userId);
        model.addAttribute("loggedUser", u);
        model.addAttribute("criteria", criteria);
        return "searchForm";
    }

    @PostMapping("/search")
    public String searchUsers(Model model, User u, HttpSession session, SearchCriteria criteria){
        if(session.getAttribute("userId") == null){
            return "index";
        }
        Long userId =(Long) session.getAttribute("userId");
        User loggedUser = userService.findByUserId(userId);
        model.addAttribute("criteria", criteria);
        model.addAttribute("loggedUser", loggedUser);
        model.addAttribute("users",userRepository.findAll(userRepository.buildQuery(criteria)));

        return "searchForm";
    }

    @PostMapping("/sendRequest")
    public ResponseEntity<String> sendConnectionRequest(@RequestParam User requester, @RequestParam User recipient){
        userService.sendConnectionRequest(requester,recipient);
        return ResponseEntity.ok("Connection request sent");
    }

    @PostMapping("/acceptRequest")
    public ResponseEntity<String> acceptConnectionRequest(@RequestParam User req, @RequestParam User rec){
        userService.addConnection(req,rec);
        return ResponseEntity.ok("Connection Request Accepted");
    }

    @PostMapping("/declineRequest")
    public ResponseEntity<String> declineConnectionRequest(@RequestParam User rec ,@RequestParam User req){
        userService.declineConnection(req,rec);

        return ResponseEntity.ok("Connection Request declined");
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/";
    }



}
