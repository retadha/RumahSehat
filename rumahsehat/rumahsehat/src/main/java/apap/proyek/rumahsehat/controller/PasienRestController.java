package apap.proyek.rumahsehat.controller;

import apap.proyek.rumahsehat.model.Pasien;
import apap.proyek.rumahsehat.service.PasienService;
import org.springframework.beans.factory.annotation.Autowired;
<<<<<<< HEAD
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
=======
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pasien")
>>>>>>> 20780cd (membuat fitur lihat profil pasien pada flutter)
public class PasienRestController {
    @Autowired
    private PasienService pasienService;

<<<<<<< HEAD
    @GetMapping(value = "/pasien-tagihan/{kode}")
    private Map getPasienByTagihan(@PathVariable("kode") String kode){
        return pasienService.getPasienByTagihan(kode);
    }

}
=======
    @GetMapping(value = "/api/viewall")
    private List<Pasien> getListPasien() {
        List<Pasien> listPasien = pasienService.findAll();
        return listPasien;
    }

    @GetMapping(value = "/api/{idPasien}")
    private Pasien getPasienById(String idPasien) {
        Pasien pasien = pasienService.getPasienById(idPasien);
        return pasien;
    }
}
>>>>>>> 20780cd (membuat fitur lihat profil pasien pada flutter)
