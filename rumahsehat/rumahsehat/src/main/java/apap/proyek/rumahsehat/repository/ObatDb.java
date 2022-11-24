package apap.proyek.rumahsehat.repository;

import apap.proyek.rumahsehat.model.Obat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface ObatDb extends JpaRepository<Obat, String> {
    Optional<Obat> findByIdObat(String idObat);
}
