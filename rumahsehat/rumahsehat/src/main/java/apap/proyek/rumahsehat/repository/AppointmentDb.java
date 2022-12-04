package apap.proyek.rumahsehat.repository;

import apap.proyek.rumahsehat.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface AppointmentDb extends JpaRepository<Appointment, String> {

    Optional<Appointment> findById(String id);

}
