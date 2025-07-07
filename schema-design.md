# MySQL Database Schema Design

## Tables

### Doctor
- `id` BIGINT PRIMARY KEY AUTO_INCREMENT  
- `name` VARCHAR(100) NOT NULL  
- `speciality` VARCHAR(100) NOT NULL  
- `email` VARCHAR(100) UNIQUE NOT NULL  
- `phone` VARCHAR(20) NOT NULL  

### Patient
- `id` BIGINT PRIMARY KEY AUTO_INCREMENT  
- `name` VARCHAR(100) NOT NULL  
- `email` VARCHAR(100) UNIQUE NOT NULL  
- `phone` VARCHAR(20) NOT NULL  
- `address` VARCHAR(255)  

### Appointment
- `id` BIGINT PRIMARY KEY AUTO_INCREMENT  
- `doctor_id` BIGINT NOT NULL  
- `patient_id` BIGINT NOT NULL  
- `appointment_time` DATETIME NOT NULL  
- `status` VARCHAR(20) DEFAULT 'Scheduled'  

**Foreign Keys:**  
- `doctor_id` REFERENCES Doctor(`id`)  
- `patient_id` REFERENCES Patient(`id`)  

### Prescription
- `id` BIGINT PRIMARY KEY AUTO_INCREMENT  
- `appointment_id` BIGINT NOT NULL  
- `medication` VARCHAR(255) NOT NULL  
- `dosage` VARCHAR(255) NOT NULL  

**Foreign Key:**  
- `appointment_id` REFERENCES Appointment(`id`)  
