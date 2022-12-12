package apap.proyek.rumahsehat.controller;

import apap.proyek.rumahsehat.model.Resep;
import apap.proyek.rumahsehat.model.ResepDto;
import apap.proyek.rumahsehat.service.ResepRestService;
import apap.proyek.rumahsehat.service.ResepService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;
import java.util.NoSuchElementException;

@Slf4j
@RestController
@RequestMapping("/api")
public class ResepRestController {
    @Autowired
    private ResepRestService resepRestService;

    @GetMapping(value = "/resep/{idResep}")
    private ResponseEntity retrieveDetailResep(@PathVariable("idResep") Long idResep){
        log.info("api get detail resep");
        ResponseEntity responseEntity;
        try{
            ResepDto resep = resepRestService.getResepById(idResep);
            responseEntity = ResponseEntity.ok(resep);
        } catch (Exception e) {
            log.error("Error in get detail reesp!");
            responseEntity = ResponseEntity.badRequest().body(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

}
