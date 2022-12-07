package apap.proyek.rumahsehat.controller;

import apap.proyek.rumahsehat.service.TagihanRestService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class TagihanRestController {
    @Autowired
    private TagihanRestService tagihanRestService;

    @GetMapping(value = "/list-tagihan")
    private Map retrieveListTagihan(@RequestHeader("Authorization") String token){
        Map<String, String> decodedToken = decode(token);
        return tagihanRestService.getListTagihan(decodedToken.get("uuid"));
    }

    @GetMapping(value = "/tagihan/{kode}")
    private Map detailTagihan(@PathVariable("kode") String kode){
        return tagihanRestService.getDetailTagihan(kode);
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
