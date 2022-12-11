package apap.proyek.rumahsehat.controller;

import apap.proyek.rumahsehat.model.Pasien;
import apap.proyek.rumahsehat.model.UserModel;
import apap.proyek.rumahsehat.service.PasienService;
import apap.proyek.rumahsehat.service.UserService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/profil")
public class PasienRestController {
    @Autowired
    private PasienService pasienService;

    @Autowired
    private UserService userService;

    @GetMapping(value = "/user" )
    private ResponseEntity getUserById(@RequestHeader("Authorization") String token) {
        log.info("api get user by id");
        ResponseEntity responseEntity = null;
        try {
            Map<String, String> decodedToken = decode(token);
            String uuid = decodedToken.get("uuid");
            UserModel user = userService.getUserById(uuid);
            responseEntity = ResponseEntity.ok(user);
        } catch (Exception e) {
            log.error("Error in get user by id!");
            responseEntity = ResponseEntity.badRequest().body(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @GetMapping(value = "/pasien" )
    private ResponseEntity getPasienById(@RequestHeader("Authorization") String token) {
        log.info("api get pasien by id");
        ResponseEntity responseEntity = null;
        try {
            Map<String, String> decodedToken = decode(token);
            String uuid = decodedToken.get("uuid");
            Pasien pasien = pasienService.getPasienById(uuid);
            responseEntity = ResponseEntity.ok(pasien);
        } catch (Exception e) {
            log.error("Error in get pasien by id!");
            responseEntity = ResponseEntity.badRequest().body(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    private Map<String, String> decode(String token) {
        String[] chunks = token.split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String payload = new String(decoder.decode(chunks[1]));
        Gson gson = new Gson();
        Map<String, String> decodedToken = gson.fromJson(payload, new TypeToken<Map<String, String>>() {}.getType());
        return decodedToken;
    }

    @PostMapping(value = "/topupsaldo")
    private ResponseEntity topUpSaldo(@RequestHeader("Authorization") String token, @RequestBody Pasien pasien) {
        log.info("api top up saldo");
        ResponseEntity responseEntity = null;
        try {
            Map<String, String> decodedToken = decode(token);
            String uuid = decodedToken.get("uuid");
            Pasien pasienlama = pasienService.getPasienById(uuid);
            pasienlama.setSaldo(pasienlama.getSaldo()+pasien.getSaldo());
            pasien = pasienlama;
            pasienService.topUpSaldo(pasien);
            responseEntity = ResponseEntity.ok(pasien);
        } catch (Exception e) {
            log.error("Error in top up saldo!");
            responseEntity = ResponseEntity.badRequest().body(HttpStatus.INTERNAL_SERVER_ERROR);
        }


        return responseEntity;
    }

}
