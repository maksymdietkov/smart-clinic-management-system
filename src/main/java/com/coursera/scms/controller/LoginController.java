package com.coursera.scms.controller;

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
        // error из flash атрибутов, если был редирект с ошибкой
        return "login-admin";
    }

    @PostMapping("/login-admin")
    public String loginAdminSubmit(@RequestParam String username,
                                   @RequestParam String password,
                                   RedirectAttributes redirectAttributes) {
        if ("admin".equals(username) && "admin123".equals(password)) {
            return "redirect:/admin/dashboard";
        } else {
            redirectAttributes.addFlashAttribute("error", "Invalid username or password");
            return "redirect:/login-admin"; // редирект, чтобы избежать Circular view path
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
                                    RedirectAttributes redirectAttributes) {
        if ("doctor".equals(username) && "doctor123".equals(password)) {
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
                                     RedirectAttributes redirectAttributes) {
        if ("patient".equals(username) && "patient123".equals(password)) {
            return "redirect:/patient/dashboard";
        } else {
            redirectAttributes.addFlashAttribute("error", "Invalid username or password");
            return "redirect:/login-patient";
        }
    }
}
