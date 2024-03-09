package com.br.psychology.system.psychologist_system.controllers;

import com.br.psychology.system.psychologist_system.models.Secretary;
import com.br.psychology.system.psychologist_system.services.psychologist.PsychologistService;
import com.br.psychology.system.psychologist_system.services.secretary.SecretaryService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("session")
public class MenuController {

    @Autowired
    private PsychologistService psychologistService;
    @Autowired
    private SecretaryService secretaryService;

    @GetMapping("/")
    public String index(){
        return "index";
    }
    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/user")
    public String logado(){
        return "novo";
    }

    @GetMapping("/register")
    public String register(@ModelAttribute HttpServletRequest httpServletRequest){
    return "register";
    }

    @PostMapping("/logout")
    public String logout(){
        System.out.println("Logout");
        return "redirect:/login";
    }

    @PostMapping("/createuser")
    public String createUser(@ModelAttribute Secretary user, HttpSession session){

        boolean p = psychologistService.checkEmail(user.getEmail());
        boolean s = secretaryService.checkEmail(user.getEmail(), user);

        if(p || s){
            session.setAttribute("msg", "E-mail já existe.");
        }else {
            Secretary secretary = secretaryService.createUser(user);

        if(secretary != null){
            session.setAttribute("msg", "Registrado com êxito!");
        }else{
            session.setAttribute("msg", "Unexpected error");
        }
    }

        return "redirect:/register";
    }
}


