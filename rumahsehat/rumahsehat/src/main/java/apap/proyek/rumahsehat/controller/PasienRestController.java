package apap.proyek.rumahsehat.controller;

import apap.proyek.rumahsehat.model.Pasien;
import apap.proyek.rumahsehat.model.UserModel;
import apap.proyek.rumahsehat.service.PasienService;
import apap.proyek.rumahsehat.service.UserService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Base64;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PasienRestController {
    @Autowired
    private PasienService pasienService;

    @Autowired
    private UserService userService;

    @GetMapping(value = "/pasien-tagihan/{kode}")
    private Map getPasienByTagihan(@PathVariable("kode") String kode){
        return pasienService.getPasienByTagihan(kode);
    }

    @GetMapping(value = "/user" )
    private UserModel getUserById(@RequestHeader("Authorization") String token) {
        Map<String, String> decodedToken = decode(token);
        String uuid = decodedToken.get("uuid");
        UserModel user = userService.getUserById(uuid);
        return user;
    }

    @GetMapping(value = "/pasien" )
    private Pasien getPasienById(@RequestHeader("Authorization") String token) {
        Map<String, String> decodedToken = decode(token);
        String uuid = decodedToken.get("uuid");
        Pasien pasien = pasienService.getPasienById(uuid);
        return pasien;
    }

    private Map<String, String> decode(String token) {
        String[] chunks = token.split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String payload = new String(decoder.decode(chunks[1]));
        Gson gson = new Gson();
        Map<String, String> decodedToken = gson.fromJson(payload, new TypeToken<Map<String, String>>() {}.getType());
        return decodedToken;
    }

}
