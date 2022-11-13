package apap.proyek.rumahsehat.repository;

import apap.proyek.rumahsehat.model.Apoteker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ApotekerDb extends JpaRepository<Apoteker, String> {
    @Query("select a from Apoteker a where a.uuid = ?1")
    Apoteker findByUuid(String uuid);

    @Override
    List<Apoteker> findAll();
}