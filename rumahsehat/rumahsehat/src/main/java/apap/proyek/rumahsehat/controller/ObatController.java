package apap.proyek.rumahsehat.controller;

import apap.proyek.rumahsehat.model.Obat;
import apap.proyek.rumahsehat.service.ObatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/obat")
public class ObatController {

    @Autowired
    private ObatService obatService;

    //Fitur Melihat Daftar Obat #12
    @GetMapping("/viewall")
    public String listObat(Model model) {
        List<Obat> listObat = obatService.getListObat();
        model.addAttribute("listObat", listObat);
        return "obat/viewall-obat";
    }

    //Fitur Mengubah Stok Obat #13
    @GetMapping("/update-stok/{id}")
    public String updateStokeObatFormPage(@PathVariable String id, Model model) {
        Obat obat = obatService.getObatByIdObat(id);
        model.addAttribute("obat",obat);
        return "obat/form-update-stok-obat";
    }

    @PostMapping("/update-stok")
    public String updateCourseSubmitPage(@ModelAttribute Obat obat, Model model) {
        Obat updatedObat = obatService.updateStok(obat);
        model.addAttribute("nama", updatedObat.getNamaObat());
        return "obat/update-stoke-obat";
    }
}
