package apap.proyek.rumahsehat.controller;

import apap.proyek.rumahsehat.model.Apoteker;
import apap.proyek.rumahsehat.model.Dokter;
import apap.proyek.rumahsehat.model.Pasien;
import apap.proyek.rumahsehat.model.UserModel;
import apap.proyek.rumahsehat.service.ApotekerService;
import apap.proyek.rumahsehat.service.DokterService;
import apap.proyek.rumahsehat.service.PasienService;
import apap.proyek.rumahsehat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

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

    @GetMapping(value = "/pasien/delete/{id}")
    private ModelAndView deletePasien(@PathVariable String id) {
        Pasien pasien = pasienService.getPasienById(id);
        pasienService.deletePasien(pasien);
        return  new ModelAndView("redirect:/users/pasien");


    }

    @GetMapping(value = "/doctors")
    private String listDoctors(Model model) {
        List<Dokter> listDokter = dokterService.findAll();
        model.addAttribute("listDokter", listDokter);
        return "user/viewall-doctors";
    }

    @GetMapping(value = "/doctors/add")
    private String addDoctorFormPage(Model model) {
        UserModel user = new UserModel();
        model.addAttribute("user",user);
        return "user/form-add-doctor";

    }

    @PostMapping(value = "/doctors/add")
    private String addDoctorSubmitPage(@ModelAttribute UserModel user, @RequestParam(value = "tarif") Integer tarif, Model model) {
        UserModel savedUser = userService.addUser(user);

        Dokter dokter = new Dokter();
        Dokter savedDokter = dokterService.addDokter(dokter, savedUser, tarif);

        model.addAttribute("role", savedUser.getRole());
        model.addAttribute("nama", savedUser.getNama());
        model.addAttribute("username", savedUser.getUsername());

        return "/user/add-web-user";

    }

    @GetMapping(value = "/doctors/delete/{id}")
    private ModelAndView deleteDokter(@PathVariable String id) {
        Dokter dokter = dokterService.getDokterById(id);
        dokterService.deleteDokter(dokter);
        return  new ModelAndView("redirect:/users/doctors");


    }

    @GetMapping(value = "/apoteker")
    private String listApoteker(Model model) {
        List<Apoteker> listApoteker = apotekerService.findAll();
        model.addAttribute("listApoteker", listApoteker);
        return "user/viewall-apoteker";
    }

    @GetMapping(value = "/apoteker/add")
    private String addApotekerFormPage(Model model) {
        UserModel user = new UserModel();
        model.addAttribute("user",user);
        return "user/form-add-apoteker";

    }

    @PostMapping(value = "/apoteker/add")
    private String addApotekerSubmitPage(@ModelAttribute UserModel user, Model model) {
        UserModel savedUser = userService.addUser(user);

        Apoteker apoteker = new Apoteker();
        apotekerService.addApoteker(apoteker, savedUser);

        model.addAttribute("role", savedUser.getRole());
        model.addAttribute("nama", savedUser.getNama());
        model.addAttribute("username", savedUser.getUsername());

        return "user/add-web-user";

    }

    @GetMapping(value = "/apoteker/delete/{id}")
    private ModelAndView deleteApoteker(@PathVariable String id) {
        Apoteker apoteker = apotekerService.getApotekerById(id);
        apotekerService.deleteApoteker(apoteker);
        return  new ModelAndView("redirect:/users/apoteker");


    }
}
