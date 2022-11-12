package apap.proyek.rumahsehat.repository;

import apap.proyek.rumahsehat.model.Apoteker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApotekerDb extends JpaRepository<Apoteker, String> {

    @Override
    List<Apoteker> findAll();
}