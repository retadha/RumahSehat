package apap.proyek.rumahsehat.service;

import apap.proyek.rumahsehat.model.UserModel;
import apap.proyek.rumahsehat.repository.UserDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDb userDb;

    @Qualifier("userDetailsServiceImpl")
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public UserModel addUser(UserModel user) {
        String pass = encrypt(user.getPassword());
        user.setPassword(pass);
        return userDb.save(user);
    }

    @Override
    public void deleteUser(UserModel user) {
        userDb.delete(user);
    }

    @Override
    public String encrypt(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);

        return hashedPassword;
    }

    @Override
    public UserModel getUserByUsername(String username) {
        return userDb.findByUsername(username);
    }

    @Override
    public UserModel getUserById(String id) {
        return userDb.findByUuid(id);
    }

    @Override
    public Boolean isAdmin(String username) {
        UserModel user = getUserByUsername(username);
        return user.getRole().equals("Admin");
    }

    @Override
    public boolean userExists(String username) {
        UserModel user = getUserByUsername(username);
        return user != null;
    }

    @Override
    public boolean validateUsername(String username) {
        return username.matches("^[A-Za-z]\\w{5,29}$");
    }

    @Override
    public boolean validateName(String name) {
        boolean validLength = name.length() >= 2 && name.length() <= 300;
        boolean validFormat = name.matches("^[a-zA-Z\\s]+");
        return validLength && validFormat;

    }

    @Override
    public boolean validateEmailFormat(String email) {
        String validEmail = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        return email.matches(validEmail);
    }

    // Password minimal 8 karakter, alfanumerik, mengandung huruf besar dan huruf kecil, dan karakter spesial
    @Override
    public boolean validatePassword(String password) {
        return password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[#$@!%&*?])[A-Za-z\\d#$@!%&*?]{8,}$");

    }

    @Override
    public boolean validateCredentials(String username, String name, String email, String password) {
        return !userExists(username)
                && validateUsername(username)
                && validateName(name)
                && validateEmailFormat(email)
                && validatePassword(password);



    }

    @Override
    public HashMap<String, Boolean> credentialsStatus(String username, String name, String email, String password) {
        HashMap<String, Boolean> status = new HashMap<>();
        status.put("userExists", userExists(username));
        status.put("validUsername", validateUsername(username));
        status.put("validName",validateName(name));
        status.put("validEmail", validateEmailFormat(email));
        status.put("validPassword", validatePassword(password));

        return status;
    }


}
