package apap.proyek.rumahsehat.controller;

import apap.proyek.rumahsehat.model.TagihanDto;
import apap.proyek.rumahsehat.service.TagihanRestService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class TagihanRestController {
    @Autowired
    private TagihanRestService tagihanRestService;

    @GetMapping(value = "/list-tagihan")
    private ResponseEntity retrieveListTagihan(@RequestHeader("Authorization") String token){
        Map<String, String> decodedToken = decode(token);
        ResponseEntity responseEntity;
        try{
            Map<String, List<TagihanDto>> listTagihan = tagihanRestService.getListTagihan(decodedToken.get("uuid"));
            responseEntity = ResponseEntity.ok(listTagihan);
        } catch (NoSuchElementException e){
            responseEntity = ResponseEntity.badRequest().body(HttpStatus.NOT_FOUND);
        }
        return responseEntity;
    }

    @GetMapping(value = "/tagihan/{kode}")
    private ResponseEntity detailTagihan(@PathVariable("kode") String kode){
        ResponseEntity responseEntity;
        try{
            TagihanDto tagihan = tagihanRestService.getDetailTagihan(kode);
            responseEntity = ResponseEntity.ok(tagihan);
        } catch (NoSuchElementException e){
            responseEntity = ResponseEntity.badRequest().body(HttpStatus.NOT_FOUND);
        }
        return responseEntity;
    }

    @GetMapping(value = "/bayar-tagihan/{kode}")
    private Map postBayarTagihan(@PathVariable("kode") String kode){
        return tagihanRestService.bayarTagihan(kode);
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
