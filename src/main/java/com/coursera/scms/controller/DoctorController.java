package com.coursera.scms.controller;

import com.coursera.scms.model.Appointment;
import com.coursera.scms.model.Doctor;
import com.coursera.scms.service.AppointmentService;
import com.coursera.scms.service.DoctorService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Controller
public class DoctorController {

    private static final Logger logger = Logger.getLogger(DoctorController.class.getName());

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private DoctorService doctorService;

    @GetMapping("/doctor/dashboard")
    public String doctorDashboard(Model model, HttpSession session) {
        String email = (String) session.getAttribute("doctorEmail");

        if (email == null || email.isEmpty()) {
            logger.warning("Doctor email is missing in session");
            return "redirect:/login-doctor";
        }

        Optional<Doctor> doctorOpt;
        try {
            doctorOpt = doctorService.findByEmail(email);
        } catch (Exception e) {
            logger.severe("Error finding doctor by email: " + e.getMessage());
            model.addAttribute("error", "Internal server error while fetching doctor");
            return "login-doctor";
        }

        if (doctorOpt.isPresent()) {
            Doctor doctor = doctorOpt.get();
            Long doctorId = doctor.getId();

            List<Appointment> appointments = appointmentService.getAppointmentsForDoctorId(doctorId);

            model.addAttribute("doctor", doctor);
            model.addAttribute("appointments", appointments);

            return "doctor-dashboard";
        } else {
            logger.warning("Doctor not found for email: " + email);
            model.addAttribute("error", "Doctor not found");
            return "login-doctor";
        }
    }
}
