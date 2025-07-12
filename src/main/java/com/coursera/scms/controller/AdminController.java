package com.coursera.scms.controller;

import com.coursera.scms.model.Doctor;
import com.coursera.scms.repository.DoctorRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminController {

    private final DoctorRepository doctorRepository;

    public AdminController(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @GetMapping("/admin/dashboard")
    public String adminDashboard(HttpSession session, Model model) {
        String role = (String) session.getAttribute("userRole");
        if ("admin".equals(role)) {
            model.addAttribute("doctors", doctorRepository.findAll());
            return "admin-dashboard";  // admin-dashboard.html
        } else {
            return "redirect:/login-admin";
        }
    }

    @PostMapping("/admin/add-doctor")
    public String addDoctor(HttpSession session,
                            @RequestParam String name,
                            @RequestParam String speciality,
                            @RequestParam String email,
                            @RequestParam String phone,
                            @RequestParam String password) {
        String role = (String) session.getAttribute("userRole");
        if (!"admin".equals(role)) {
            return "redirect:/login-admin";
        }

        Doctor doctor = new Doctor();
        doctor.setName(name);
        doctor.setSpeciality(speciality);
        doctor.setEmail(email);
        doctor.setPhone(phone);
        doctor.setPassword(password);

        doctorRepository.save(doctor);

        return "redirect:/admin/dashboard";
    }
}
