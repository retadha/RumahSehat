package apap.proyek.rumahsehat.repository;

import apap.proyek.rumahsehat.model.Tagihan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagihanDb extends JpaRepository<Tagihan, String> {
}
