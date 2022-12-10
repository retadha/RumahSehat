package apap.proyek.rumahsehat.controller;

import apap.proyek.rumahsehat.model.Resep;
import apap.proyek.rumahsehat.model.ResepDto;
import apap.proyek.rumahsehat.service.ResepRestService;
import apap.proyek.rumahsehat.service.ResepService;
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

@RestController
@RequestMapping("/api")
public class ResepRestController {
    @Autowired
    private ResepRestService resepRestService;

    @GetMapping(value = "/resep/{idResep}")
    private ResponseEntity retrieveResep(@PathVariable("idResep") Long idResep){
        ResponseEntity responseEntity;
        try{
            ResepDto resep = resepRestService.getResepById(idResep);
            responseEntity = ResponseEntity.ok(resep);
        } catch (NoSuchElementException e){
            responseEntity = ResponseEntity.badRequest().body(HttpStatus.NOT_FOUND);
        }
        return responseEntity;
    }

}
