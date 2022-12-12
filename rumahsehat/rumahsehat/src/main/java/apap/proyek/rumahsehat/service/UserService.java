package apap.proyek.rumahsehat.service;

import apap.proyek.rumahsehat.model.Dokter;
import apap.proyek.rumahsehat.model.UserModel;
import java.util.HashMap;
import java.util.List;

public interface UserService {

    UserModel addUser(UserModel user);

    void deleteUser(UserModel user);

    String encrypt(String password);

    UserModel getUserByUsername(String username);

    UserModel getUserById(String id);

    Boolean isAdmin(String username);

    boolean userExists(String username);

    boolean validateUsername(String username);

    boolean validateName(String name);

    boolean validateEmailFormat(String email);

    boolean validatePassword(String password);

    boolean validateCredentials(String username, String name, String email, String password);

    HashMap<String, Boolean> credentialsStatus(String username, String name, String email, String password);



}
