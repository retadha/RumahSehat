package apap.proyek.rumahsehat.repository;

import apap.proyek.rumahsehat.model.Resep;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ResepDb extends JpaRepository<Resep, Long> {
    Optional<Resep> findById(Long idResep);
}
