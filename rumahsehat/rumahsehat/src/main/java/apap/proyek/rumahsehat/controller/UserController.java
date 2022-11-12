package apap.proyek.rumahsehat.controller;

import apap.proyek.rumahsehat.model.Apoteker;
import apap.proyek.rumahsehat.model.Dokter;
import apap.proyek.rumahsehat.model.Pasien;
import apap.proyek.rumahsehat.service.ApotekerService;
import apap.proyek.rumahsehat.service.DokterService;
import apap.proyek.rumahsehat.service.PasienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private PasienService pasienService;

    @Autowired
    private DokterService dokterService;

    @Autowired
    private ApotekerService apotekerService;

    @GetMapping(value = "")
    private String manageUsers() {
        return "user/users-management";
    }

    @GetMapping(value = "/pasien")
    private String listPasien(Model model) {
        List<Pasien> listPasien = pasienService.findAll();
        model.addAttribute("listPasien", listPasien);
        return "user/viewall-pasien";
    }

    @GetMapping(value = "/doctors")
    private String listDoctors(Model model) {
        List<Dokter> listDokter = dokterService.findAll();
        model.addAttribute("listDokter", listDokter);
        return "user/viewall-doctors";
    }

    @GetMapping(value = "/apoteker")
    private String listApoteker(Model model) {
        List<Apoteker> listApoteker = apotekerService.findAll();
        model.addAttribute("listApoteker", listApoteker);
        return "user/viewall-apoteker";
    }



}
