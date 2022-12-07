package apap.proyek.rumahsehat.repository;

import apap.proyek.rumahsehat.model.Tagihan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TagihanDb extends JpaRepository<Tagihan, String> {
    @Query("select t from Tagihan t where t.kodeAppointment.pasien.user.username = :username")
    List<Tagihan> findByUsername(String username);

    @Query("select t from Tagihan t where t.kodeAppointment.pasien.user.uuid = :uuid")
    List<Tagihan> findByUuid(String uuid);
}
