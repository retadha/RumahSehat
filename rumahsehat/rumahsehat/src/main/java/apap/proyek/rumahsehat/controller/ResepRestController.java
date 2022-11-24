package apap.proyek.rumahsehat.controller;

import apap.proyek.rumahsehat.model.Resep;
import apap.proyek.rumahsehat.service.ResepRestService;
import apap.proyek.rumahsehat.service.ResepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    private ResepRestService resepService;

    @GetMapping(value = "/resep/{idResep}")
    private Map retrieveResep(@PathVariable("idResep") Long idResep){
        try{
            return resepService.getResepById(idResep);
        } catch (NoSuchElementException e){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Resep dengan ID " + idResep + " not found"
            );
        }
    }

}
