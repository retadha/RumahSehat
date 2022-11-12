package apap.proyek.rumahsehat.repository;

import apap.proyek.rumahsehat.model.Dokter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DokterDb extends JpaRepository<Dokter, String> {


}