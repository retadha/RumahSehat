package apap.proyek.rumahsehat.controller;

import apap.proyek.rumahsehat.service.TagihanRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class TagihanRestController {
    @Autowired
    private TagihanRestService tagihanRestService;

    @GetMapping(value = "/list-tagihan/{username}")
    private Map retrieveListTagihan(@PathVariable("username") String username){
        return tagihanRestService.getListTagihan(username);
    }

}
