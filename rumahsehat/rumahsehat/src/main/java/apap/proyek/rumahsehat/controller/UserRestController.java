package apap.proyek.rumahsehat.controller;

import apap.proyek.rumahsehat.model.Pasien;
import apap.proyek.rumahsehat.model.UserModel;
import apap.proyek.rumahsehat.service.PasienService;
import apap.proyek.rumahsehat.service.UserService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Base64;
import java.util.Map;
import java.util.NoSuchElementException;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/users")
public class UserRestController {
    @Autowired
    private UserService userService;

    @Autowired
    private PasienService pasienService;

    @PostMapping(value = "/pasien/register")
    private ResponseEntity<String> createPasien(@RequestBody String userData, BindingResult bindingResult){
        Gson gson = new Gson();
        Map<String, String> userDataMap = gson.fromJson(userData, new TypeToken<Map<String, String>>() {}.getType());


        String username = userDataMap.get("username");
        if (userService.userExists(username)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Username telah digunakan"
            );
        }

        try {

            String nama = userDataMap.get("nama");
            String email = userDataMap.get("email");
            String role = userDataMap.get("role");
            String password = userDataMap.get("password");
            Integer saldo = Integer.valueOf(userDataMap.get("saldo"));
            Integer umur = Integer.valueOf(userDataMap.get("umur"));

            UserModel user = new UserModel();
            user.setUsername(username);
            user.setNama(nama);
            user.setEmail(email);
            user.setRole(role);
            user.setPassword(password);

            UserModel savedUser = userService.addUser(user);

            pasienService.addPasien(new Pasien(), savedUser, saldo, umur);

            return ResponseEntity.ok("Pasien dengan username " + username + " berhasil dibuat");

        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Request body has invalid type or missing field"
            );
        }



    }


}
