package apap.proyek.rumahsehat.controller;

import apap.proyek.rumahsehat.model.Jumlah;
import apap.proyek.rumahsehat.model.Resep;
import apap.proyek.rumahsehat.service.JumlahService;
import apap.proyek.rumahsehat.service.ResepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ResepController {
    @Qualifier("resepServiceImpl")
    @Autowired
    private ResepService resepService;

    @Qualifier("jumlahServiceImpl")
    @Autowired
    private JumlahService jumlahService;

    @GetMapping("/resep/{idResep}")
    public String viewDetailResep(@PathVariable long idResep, Model model, Authentication authentication){
        String role = "";
        if( authentication.getAuthorities().contains(new SimpleGrantedAuthority("Apoteker"))){
            role="Apoteker";
        }
        Resep resep = resepService.getResepById(idResep);
        List<Jumlah> listJumlah = jumlahService.findByResep(idResep);
        model.addAttribute("resep", resep);
        model.addAttribute("listJumlah", listJumlah);
        model.addAttribute("role", role);
        return "resep/detail-resep";
    }

    @PostMapping("/resep/{idResep}")
    public String konfirmasiResep(@PathVariable long idResep, Model model, Authentication authentication){
        String role = "";
        if( authentication.getAuthorities().contains(new SimpleGrantedAuthority("Apoteker"))){
            role="Apoteker";
        }
        Resep resep = resepService.getResepById(idResep);
        List<Jumlah> listJumlah = jumlahService.findByResep(idResep);
        model.addAttribute("resep", resep);
        model.addAttribute("listJumlah", listJumlah);
        model.addAttribute("role", role);
        return "resep/detail-resep";
    }

}
