package com.coursera.scms.repository;

import com.coursera.scms.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findByDoctorId(Long doctorId);

    List<Appointment> findByDoctorIdAndAppointmentTimeBetween(Long doctorId, LocalDateTime start, LocalDateTime end);

    List<Appointment> findByDoctorIdOrderByAppointmentTimeAsc(Long doctorId);

    List<Appointment> findByPatientIdOrderByAppointmentTimeAsc(Long patientId);

    // Новый метод: получить список приёмов по дате (сравнение только по дате, без времени)
    @Query("SELECT a FROM Appointment a WHERE a.doctor.id = :doctorId AND FUNCTION('DATE', a.appointmentTime) = :date")
    List<Appointment> findByDoctorIdAndDate(@Param("doctorId") Long doctorId, @Param("date") String date);
}
