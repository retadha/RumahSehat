package apap.proyek.rumahsehat.repository;

import apap.proyek.rumahsehat.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminDb extends JpaRepository<Admin, String> {
}