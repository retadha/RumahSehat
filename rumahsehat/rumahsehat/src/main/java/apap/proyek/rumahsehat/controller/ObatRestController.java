package apap.proyek.rumahsehat.controller;

import apap.proyek.rumahsehat.model.Obat;
import apap.proyek.rumahsehat.service.ObatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/obat")
public class ObatRestController {
    @Autowired
    private ObatService obatService;

    @GetMapping(value = "/api/view-all")
    private List<Obat> retrieveObat() {
        List<Obat> listObat = obatService.getListObat();
        return listObat;
    }
}
