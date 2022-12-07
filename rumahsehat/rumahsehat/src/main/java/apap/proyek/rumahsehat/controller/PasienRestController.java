package apap.proyek.rumahsehat.controller;

import apap.proyek.rumahsehat.model.Pasien;
import apap.proyek.rumahsehat.service.PasienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class PasienRestController {
    @Autowired
    private PasienService pasienService;

    @GetMapping(value = "/pasien-tagihan/{kode}")
    private Map getPasienByTagihan(@PathVariable("kode") String kode){
        return pasienService.getPasienByTagihan(kode);
    }

}
