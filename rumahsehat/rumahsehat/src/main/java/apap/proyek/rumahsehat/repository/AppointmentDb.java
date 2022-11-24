package apap.proyek.rumahsehat.repository;

import apap.proyek.rumahsehat.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppointmentDb extends JpaRepository<Appointment, String> {
<<<<<<< HEAD
    Optional<Appointment> findAppointmentById(String id);
=======
    Optional<Appointment> findById(String id);
>>>>>>> 0f9f4b776734f27ffa01e7668dd305213a4bcf97
}
