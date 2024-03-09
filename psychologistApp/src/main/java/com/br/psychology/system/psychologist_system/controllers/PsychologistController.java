package com.br.psychology.system.psychologist_system.controllers;

import com.br.psychology.system.psychologist_system.models.Patient;
import com.br.psychology.system.psychologist_system.models.Psychologist;
import com.br.psychology.system.psychologist_system.models.Secretary;
import com.br.psychology.system.psychologist_system.repositories.PatientRepository;
import com.br.psychology.system.psychologist_system.services.psychologist.PsychologistService;
import com.br.psychology.system.psychologist_system.services.secretary.SecretaryService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@SessionAttributes("session")
@Controller
@RequestMapping("/psychologists")
public class PsychologistController {

    @Autowired
    private PsychologistService psychologistService;

    @Autowired
    private SecretaryService secretaryService;

    @Autowired
    private PatientRepository patientRepository;

    /**
     * Método que retorna a página inicial para o psicologo
     * @return retornando a página psychologist/index
     */

    @GetMapping("/")
    public String menu(){
        return "psychologist/index";
    }

    /**
     * @param httpServletRequest requisição HTTP
     * @return para a página psychologist/register
     */

    @GetMapping("/register")
    public String register(@ModelAttribute HttpServletRequest httpServletRequest){
        return "psychologist/register";
    }

    @PostMapping("/createpsychologist")
    public String createUser(@ModelAttribute Psychologist user, HttpSession session, Secretary secretary){

        boolean p = psychologistService.checkEmail(user.getEmail());
        boolean s = secretaryService.checkEmail(user.getEmail(), secretary);

        if(p || s){
            session.setAttribute("msg", "E-mail já existe.");
        }else{
            Psychologist psychologist = psychologistService.createUser(user);
            if(psychologist != null){
                session.setAttribute("msg", "Registrado com êxito!");
            }else{
                session.setAttribute("msg", "Unexpected error");
            }
        }

        return "redirect:/psychologist/register";
    }
    /**
     *
     * @param model repassando a lista de pacientes para a view
     */

    private void index(Model model){
        List<Patient> patientList = patientRepository.findAll();
        model.addAttribute("listarPaciente", patientList);
    }

    /**
     *
     * @param name nome do paciente a ser pesquisado
     * @param model repassando uma lista de pacientes com o nome digitado
     * @return retornando a página inicial index dos psicologos
     */

    @GetMapping("/search")
    public String searchPatients(@RequestParam("name") String name, Model model){
        List<Patient> patients = patientRepository.findByName(name);
        model.addAttribute("listarPaciente", patients);
        return "psychologist/index";
    }
    /** Método que retorna a página para criar um novo paciente
     */
    @GetMapping("/newpatient")
    public String newPatient(){
        return "psychologist/newpatient";
    }

    /**Método para criar um novo Paciente
     *
     * @param pacient o objeto de tipo Paciente a ser criado
     * @return redirecionando para a página /doutores/
     *
     * try e catch para validar se o CPF contém 11 digitos se não tiver é dada uma mensagem de erro no front atráves
     * do model
     */

    @PostMapping("/newpatient")
    public String createPatient(Patient pacient, Model model){
        try{
            if(pacient.getCpf().length() != 11){
                throw new IllegalArgumentException("O CPF deve ter 11 dígitos.");
            }
            patientRepository.save(pacient);
            return "redirect:/psychologist";
        }catch(IllegalArgumentException e){
            model.addAttribute("cpfError", e.getMessage());
            return "psychologist/newpatient";
        }
    }

    /** Método que retorna a página de alteração de dados do paciente
     *
     * @param id para coletar o ID do paciente a ser alterado
     *
     * No primeiro trecho de código a primeira linha está retornando um objeto Optional;
     * ou seja que pode ou não existir um paciente, verificando se o ID é existente no banco de dados;
     * Em seguida na linha abaixo é verificado se o paciente não existe no banco de dados;
     * Se não for encontrado é efetuado um return para /doutores/
     *
     *No segundo trecho de código é obtido o paciente existente e na segunda linha o paciente;
     * É adicionado ao model e se torna disponível para a view com o nome "paciente"
     * E em seguida é retornado o hmtl de edição de paciente.
     */

    @GetMapping("/{id}/modifypatient")
    public String modifyPatient(@PathVariable Long id, Model model){
        Optional<Patient> patientOptional = patientRepository.findById(id);
        if(!patientOptional.isPresent()){
            return "redirect:/psychologists/";
        }

        Patient patient = patientOptional.get();
        model.addAttribute("pacient", patient);
        return "psychologist/modifypatient";
    }

    @PostMapping("/modifypatient/{id}")
    public String saveModifications(@PathVariable Long id, @ModelAttribute("patient") Patient patient){
        patient.setId(id);
        patientRepository.save(patient);
        return "redirect:/psychologists/";
    }

    @GetMapping("/{id}")
    public String deletePsychologist(@PathVariable Long id){
        patientRepository.deleteById(id);
        return "redirect:/psychologists/";
    }


}
