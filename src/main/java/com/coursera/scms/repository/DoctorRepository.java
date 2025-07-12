package com.coursera.scms.repository;

import com.coursera.scms.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;                 // ✅ Добавь это
import java.util.Optional;            // ✅ И это

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    Optional<Doctor> findByEmail(String email);

    List<Doctor> findByNameContainingIgnoreCase(String name);
}
