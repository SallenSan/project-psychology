package com.br.psychology.system.psychologist_system.controllers;

import com.br.psychology.system.psychologist_system.models.Appointment;
import com.br.psychology.system.psychologist_system.models.Patient;
import com.br.psychology.system.psychologist_system.models.Psychologist;
import com.br.psychology.system.psychologist_system.repositories.AppointmentRepository;
import com.br.psychology.system.psychologist_system.repositories.PatientRepository;
import com.br.psychology.system.psychologist_system.repositories.PsychologistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/psychologists")
public class AppointmentController {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private PsychologistRepository psychologistRepository;

    @Autowired
    private PDFController pdfController;


    @GetMapping("/appointment/{id}")
    public String consultPatients(@PathVariable Long id, Model model){
        Optional<Patient> patientOptional = patientRepository.findById(id);
        if(!patientOptional.isPresent()){
            return "redirect:/psychologists/";
        }

        Patient patient = patientOptional.get();
        List<Appointment> previousAppointments = appointmentRepository.findAllByPatient(patient);

        model.addAttribute("patient", patient);
        model.addAttribute("previousAppointments", previousAppointments);

        Appointment appointment = new Appointment();
        appointment.setPatient(patient);
        model.addAttribute("appointment", appointment);

        return "psychologist/appointment";
    }
    @Secured("ROLE_ADMIN")
    @PostMapping("/appointment/{id}/save")
    public String saveAppointment(@PathVariable Long id, @ModelAttribute("appointment") Appointment appointment){
        Optional<Patient> patientOptional = patientRepository.findById(id);
        if(!patientOptional.isPresent()){
            return "redirect:/psychologists/";
        }

        Patient patient = patientOptional.get();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null || !authentication.isAuthenticated()){
            return "redirect:/login";
        }

        // obtém o nome do usuário autenticado, que é o email no  caso
        String userMail = authentication.getName();

        Psychologist psychologist = psychologistRepository.findByEmail(userMail);

    }


}
