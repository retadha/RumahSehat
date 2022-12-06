package apap.proyek.rumahsehat.repository;

import apap.proyek.rumahsehat.model.UserModel;
import apap.proyek.rumahsehat.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserDb extends JpaRepository<UserModel, String> {

    @Query("select u from UserModel u where u.username = ?1")
    UserModel findByUsername(String username);

    @Query("select u from UserModel u where u.uuid = ?1")
    UserModel findByUuid(String uuid);

    @Query("select u from UserModel u where u.username = ?1 and u.role = ?2")
    UserModel findByUsernameAndRole(String username, String role);





}