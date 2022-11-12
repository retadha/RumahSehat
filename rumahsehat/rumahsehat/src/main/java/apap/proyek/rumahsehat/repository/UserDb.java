package apap.proyek.rumahsehat.repository;

import apap.proyek.rumahsehat.model.UserModel;
import apap.proyek.rumahsehat.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserDb extends JpaRepository<UserModel, String> {

    @Query("select u from UserModel u where u.username = ?1")
    UserModel findByUsername(String username);

}