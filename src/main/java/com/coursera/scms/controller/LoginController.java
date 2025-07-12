package com.coursera.scms.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

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
            session.setAttribute("userRole", "admin");  // Сохраняем роль в сессию
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
        if ("doctor".equals(username) && "doctor123".equals(password)) {
            session.setAttribute("userRole", "doctor");
            return "redirect:/doctor/dashboard";
        } else {
            redirectAttributes.addFlashAttribute("error", "Invalid username or password");
            return "redirect:/login-doctor";
        }
    }

    // --- Patient login ---

    @GetMapping("/login-patient")
    public String loginPatientForm(Model model) {
        return "login-patient";
    }

    @PostMapping("/login-patient")
    public String loginPatientSubmit(@RequestParam String username,
                                     @RequestParam String password,
                                     HttpSession session,
                                     RedirectAttributes redirectAttributes) {
        if ("patient".equals(username) && "patient123".equals(password)) {
            session.setAttribute("userRole", "patient");
            return "redirect:/patient/dashboard";
        } else {
            redirectAttributes.addFlashAttribute("error", "Invalid username or password");
            return "redirect:/login-patient";
        }
    }
}
