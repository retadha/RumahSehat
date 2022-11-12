package apap.proyek.rumahsehat.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PasienDb extends JpaRepository<Pasien, String> {

    @Override
    List<Pasien> findAll();
}