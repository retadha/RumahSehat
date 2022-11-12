package apap.proyek.rumahsehat.repository;

import apap.proyek.rumahsehat.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserDb extends JpaRepository<User, String> {

    @Query("select u from User u where u.username = ?1")
    User findByUsername(String username);

}