package apap.proyek.rumahsehat.repository;

import apap.proyek.rumahsehat.model.Pasien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PasienDb extends JpaRepository<Pasien, String> {
    @Query("select p from Pasien p where p.uuid = ?1")
    Pasien findByUuid(String uuid);

    @Override
    List<Pasien> findAll();

}