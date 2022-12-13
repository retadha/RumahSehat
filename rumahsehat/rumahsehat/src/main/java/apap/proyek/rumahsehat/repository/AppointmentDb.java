package apap.proyek.rumahsehat.repository;

import apap.proyek.rumahsehat.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AppointmentDb extends JpaRepository<Appointment, String> {
    Optional<Appointment> findById(String id);

    @Query("select a from Appointment a where a.pasien.user.uuid = :uuid")
    List<Appointment> findByUuid(String uuid);
}
