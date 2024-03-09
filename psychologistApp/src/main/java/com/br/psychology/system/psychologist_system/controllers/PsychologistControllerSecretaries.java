package com.br.psychology.system.psychologist_system.controllers;

import com.br.psychology.system.psychologist_system.models.Secretary;
import com.br.psychology.system.psychologist_system.repositories.SecretaryRepository;
import com.br.psychology.system.psychologist_system.services.psychologist.PsychologistService;
import com.br.psychology.system.psychologist_system.services.secretary.SecretaryService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@SessionAttributes("session")
@Controller
@RequestMapping("/psychologists/secretary")
public class PsychologistControllerSecretaries {

    @Autowired
    private SecretaryRepository secretaryRepository;

    @Autowired
    private SecretaryService secretaryService;

    @Autowired
    private PsychologistService psychologistService;

    @GetMapping("/")
    public String menu(){
        return "psychologist/indexSecretary";
    }

    @ModelAttribute
    private void index(Model model){
        List<Secretary> secretaries = secretaryRepository.findAll();
        model.addAttribute("listarSecretarias", secretaries);

    }

    @GetMapping("/search")
    public String searchPatients(@RequestParam("name") String name, Model model){
        List<Secretary> secretaries = secretaryRepository.findByName(name);
        model.addAttribute("listarSecretarias", secretaries);
        return "psychologist/indexSecretary";
    }

    @GetMapping("/newsecretary")
    public String newSecretary(){
        return "psychologist/newsecretary";
    }

    @PostMapping("/newsecretary")
    public String createSecretary(@ModelAttribute Secretary user, HttpSession session){
        boolean p = psychologistService.checkEmail(user.getEmail());
        boolean s = secretaryService.checkEmail(user.getEmail(), user);

        if(s || p){
            session.setAttribute("msg", "E-mail já existente!");
            return "psychologist/newsecretary";
        }else{
            Secretary createdSecretary = secretaryService.createUser(user);
            secretaryRepository.save(createdSecretary);
            session.setAttribute("msg", "Registrado com êxito!");
            return "redirect:/psychologists/secretary/";
        }

        }
    @GetMapping("/{id}/editsecretary")
    public String editSecretary(@PathVariable Long id, Model model){
        Optional<Secretary> secretaryOptional = secretaryRepository.findById(id);
        if(!secretaryOptional.isPresent()){
            return "redirect:/psychologists/secretary/";
        }
        Secretary secretary = secretaryOptional.get();
        model.addAttribute("secretary", secretary);
        return "psychologist/editsecretary";
    }

    @PostMapping("/editsecretary/{id}")
    public String saveEditions(@PathVariable Long id, @ModelAttribute("secretary")Secretary user, HttpSession session){
        Secretary secretary = secretaryService.getSecretaryById(id);

        boolean p = psychologistService.checkEmail(user.getEmail());
        boolean s = secretaryService.checkEmail(user.getEmail(), secretary);

        if(s || p){
            session.setAttribute("msg", "E-mail já existe para outra secretária." );
            return "psychologist/editsecretary";
        }else{
            secretaryService.createUser(user);
            session.setAttribute("msg", "Atualizado Com Sucesso!");
            return "redirect:/psychologists/secretary/";
        }
    }

    @GetMapping("/{id}")
    public String deleteSecretary(@PathVariable Long id){
        secretaryRepository.deleteById(id);
        return "redirect:/psychologits/secretary/";
    }

}
