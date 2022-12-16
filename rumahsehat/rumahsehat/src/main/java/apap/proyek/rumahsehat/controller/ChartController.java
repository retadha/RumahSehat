package apap.proyek.rumahsehat.controller;

import apap.proyek.rumahsehat.model.Dokter;
import apap.proyek.rumahsehat.service.DokterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/chart")
public class ChartController {
    @Autowired
    DokterService dokterService;

    @GetMapping(value = "")
    private String getStatistics(Model model) {
        List<Dokter> listDokter = dokterService.findAll();
        model.addAttribute("listDokter", listDokter);
        return "chart/chart";
    }
}
