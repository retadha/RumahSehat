package apap.proyek.rumahsehat.repository;

import apap.proyek.rumahsehat.model.Dokter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DokterDb extends JpaRepository<Dokter, String> {


    @Override
    List<Dokter> findAll();
}