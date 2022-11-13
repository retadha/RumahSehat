package apap.proyek.rumahsehat.repository;

import apap.proyek.rumahsehat.model.Pasien;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PasienDb extends JpaRepository<Pasien, String> {

    @Override
    List<Pasien> findAll();
}