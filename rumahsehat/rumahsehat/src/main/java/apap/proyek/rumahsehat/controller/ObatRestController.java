package apap.proyek.rumahsehat.controller;

import apap.proyek.rumahsehat.model.Obat;
import apap.proyek.rumahsehat.service.ObatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
public class ObatRestController {
    @Autowired
    private ObatService obatService;

    @RequestMapping(value = "/obat/view-all", method = RequestMethod.GET)
    private ResponseEntity getAllObat() {
        log.info("api get all obat");
        ResponseEntity responseEntity = null;
        try {
            List<Obat> listObat = obatService.getListObat();
            responseEntity = ResponseEntity.ok(listObat);
        } catch (Exception e) {
            log.error("Error in get all obat");
            responseEntity = ResponseEntity.badRequest().body(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;

    }
}
