package com.br.psychology.system.psychologist_system.controllers;

import com.br.psychology.system.psychologist_system.models.Appointment;
import com.br.psychology.system.psychologist_system.models.Patient;
import com.br.psychology.system.psychologist_system.models.Psychologist;
import com.br.psychology.system.psychologist_system.repositories.AppointmentRepository;
import com.br.psychology.system.psychologist_system.repositories.PatientRepository;
import com.br.psychology.system.psychologist_system.repositories.PsychologistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
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
        if(psychologist == null){
            return "redirect:/psychologists/";
        }

        Appointment newAppointment = new Appointment();
        newAppointment.setPatient(patient);
        newAppointment.setReceita(appointment.getReceita());
        newAppointment.setPsychologist(psychologist);

        appointmentRepository.save(newAppointment);

        // Redireciona para o método que realiza o download do PDF
        return "redirect:/psychologists/appointment" + newAppointment.getId() + "/download";
    }

        // metodo que irá gerar o pdf da consulta e fará o download
    @GetMapping("/appointment/{id}/download")
    public ResponseEntity<byte[]> downloadPDFAppointment(@PathVariable Long id){
        Optional<Appointment> appointmentOptional = appointmentRepository.findById(id);
        if(!appointmentOptional.isPresent()){
            return ResponseEntity.notFound().build();
        }

        Appointment appointment = appointmentOptional.get();
        Psychologist psychologist = appointment.getPsychologist();

        if(psychologist == null){
            return ResponseEntity.badRequest().body("O psicólogo associado a consulta é nulo!".getBytes());
        }

        try{
            byte[] pdfBytes = pdfController.generatePDFAppointment(appointment);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_PDF);
            httpHeaders.setContentDispositionFormData("attachment", "Receita" + appointment.getPatient().getName()+ "pdf");

            return ResponseEntity.ok().header(httpHeaders).body(pdfBytes);
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


}
