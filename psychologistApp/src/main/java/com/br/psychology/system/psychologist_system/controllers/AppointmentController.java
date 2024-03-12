package com.br.psychology.system.psychologist_system.controllers;

import com.br.psychology.system.psychologist_system.models.Appointment;
import com.br.psychology.system.psychologist_system.models.Patient;
import com.br.psychology.system.psychologist_system.repositories.AppointmentRepository;
import com.br.psychology.system.psychologist_system.repositories.PatientRepository;
import com.br.psychology.system.psychologist_system.repositories.PsychologistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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


    public String consultPatients(@PathVariable Long id, Model model){
        Optional<Patient> patientOptional = patientRepository.findById(id);
        if(!patientOptional.isPresent()){
            return "redirect:/psychologists/";
        }

        Patient patient = patientOptional.get();
        List<Appointment> previousAppointments = appointmentRepository.findAllByPatient(patient);
        
    }


}
