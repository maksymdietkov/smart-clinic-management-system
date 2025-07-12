package com.coursera.scms.controller;

import com.coursera.scms.model.Doctor;
import com.coursera.scms.service.DoctorService;
import com.coursera.scms.service.PatientService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class LoginController {

    private final PatientService patientService;
    private final DoctorService doctorService;

    public LoginController(PatientService patientService, DoctorService doctorService) {
        this.patientService = patientService;
        this.doctorService = doctorService;
    }

    // --- Admin login ---

    @GetMapping("/login-admin")
    public String loginAdminForm(Model model) {
        return "login-admin";
    }

    @PostMapping("/login-admin")
    public String loginAdminSubmit(@RequestParam String username,
                                   @RequestParam String password,
                                   HttpSession session,
                                   RedirectAttributes redirectAttributes) {
        if ("admin".equals(username) && "admin123".equals(password)) {
            session.setAttribute("userRole", "admin");
            return "redirect:/admin/dashboard";
        } else {
            redirectAttributes.addFlashAttribute("error", "Invalid username or password");
            return "redirect:/login-admin";
        }
    }

    // --- Doctor login ---

    @GetMapping("/login-doctor")
    public String loginDoctorForm(Model model) {
        return "login-doctor";
    }

    @PostMapping("/login-doctor")
    public String loginDoctorSubmit(@RequestParam String username,
                                    @RequestParam String password,
                                    HttpSession session,
                                    RedirectAttributes redirectAttributes) {
        Optional<Doctor> doctorOpt = doctorService.findByEmail(username);

        if (doctorOpt.isPresent()) {
            Doctor doctor = doctorOpt.get();
            if (doctor.getPassword().equals(password)) {
                session.setAttribute("userRole", "doctor");
                session.setAttribute("doctorEmail", username);
                return "redirect:/doctor/dashboard";
            }
        }
        redirectAttributes.addFlashAttribute("error", "Invalid username or password");
        return "redirect:/login-doctor";
    }

    // --- Patient login ---

    @GetMapping("/login-patient")
    public String loginPatientForm(Model model) {
        return "login-patient";
    }

    @PostMapping("/login-patient")
    public String loginPatientSubmit(@RequestParam String email,
                                     @RequestParam String password,
                                     HttpSession session,
                                     RedirectAttributes redirectAttributes) {
        if (patientService.validateLogin(email, password)) {
            session.setAttribute("userRole", "patient");
            session.setAttribute("userEmail", email);
            return "redirect:/patient/dashboard";
        } else {
            redirectAttributes.addFlashAttribute("error", "Invalid email or password");
            return "redirect:/login-patient";
        }
    }
}
