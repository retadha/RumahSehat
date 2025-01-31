package apap.proyek.rumahsehat.controller;

import apap.proyek.rumahsehat.model.TagihanDto;
import apap.proyek.rumahsehat.service.TagihanRestService;
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

@Slf4j
@RestController
@RequestMapping("/api")
public class TagihanRestController {
    @Autowired
    private TagihanRestService tagihanRestService;

    @GetMapping(value = "/list-tagihan")
    private ResponseEntity retrieveListTagihan(@RequestHeader("Authorization") String token){
        log.info("api get all tagihan");
        Map<String, String> decodedToken = decode(token);
        ResponseEntity responseEntity;

        try{
            Map<String, List<TagihanDto>> listTagihan = tagihanRestService.getListTagihan(decodedToken.get("uuid"));
            responseEntity = ResponseEntity.ok(listTagihan);
        } catch (Exception e) {
            log.error("Error in add book!");
            responseEntity = ResponseEntity.badRequest().body(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @GetMapping(value = "/tagihan/{kode}")
    private ResponseEntity retrieveDetailTagihan(@PathVariable("kode") String kode){
        log.info("api get detail tagihan");
        ResponseEntity responseEntity;

        try{
            TagihanDto tagihan = tagihanRestService.getDetailTagihan(kode);
            responseEntity = ResponseEntity.ok(tagihan);
        } catch (Exception e) {
            log.error("Error in get detail tagihan!");
            responseEntity = ResponseEntity.badRequest().body(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @GetMapping(value = "/bayar-tagihan/{kode}")
    private ResponseEntity bayarTagihan(@PathVariable("kode") String kode){
        log.info("api get status bayar tagihan");
        ResponseEntity responseEntity;

        try{
            Map statusMap = tagihanRestService.bayarTagihan(kode);
            responseEntity = ResponseEntity.ok(statusMap);
        } catch (Exception e) {
            log.error("Error in payment process!");
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
}
