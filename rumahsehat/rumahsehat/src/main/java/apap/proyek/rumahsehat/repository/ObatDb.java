package apap.proyek.rumahsehat.repository;

import apap.proyek.rumahsehat.model.Obat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ObatDb extends JpaRepository<Obat, String> {
    @Override
    List<Obat> findAll();

    Optional<Obat> findByIdObat(String idObat);
}