package apap.proyek.rumahsehat.repository;

import apap.proyek.rumahsehat.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentDb extends JpaRepository<Appointment, String> {
}
