package com.br.psychology.system.psychologist_system.controllers;

import com.br.psychology.system.psychologist_system.services.psychologist.PsychologistService;
import com.br.psychology.system.psychologist_system.services.secretary.SecretaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@SessionAttributes("session")
@Controller
@RequestMapping("/psychologists")
public class PsychologistController {

    @Autowired
    private PsychologistService psychologistService;

    @Autowired
    private SecretaryService secretaryService;
}
