package apap.proyek.rumahsehat.service;

import apap.proyek.rumahsehat.model.Dokter;
import apap.proyek.rumahsehat.model.UserModel;

import java.util.List;

public interface UserService {

    UserModel addUser(UserModel user);

    void deleteUser(UserModel user);

    String encrypt(String password);

    UserModel getUserByUsername(String username);

    UserModel getUserById(String id);

    Boolean isAdmin(String username);



}
