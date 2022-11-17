package apap.proyek.rumahsehat.controller;

import apap.proyek.rumahsehat.model.*;
import apap.proyek.rumahsehat.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class ResepController {
    @Qualifier("resepServiceImpl")
    @Autowired
    private ResepService resepService;

    @Qualifier("jumlahServiceImpl")
    @Autowired
    private JumlahService jumlahService;

    @Qualifier("appointmentServiceImpl")
    @Autowired
    private AppointmentService appointmentService;

    @Qualifier("userServiceImpl")
    @Autowired
    private UserService userService;

    @Qualifier("apotekerServiceImpl")
    @Autowired
    private ApotekerService apotekerService;

    @Qualifier("tagihanServiceImpl")
    @Autowired
    private TagihanService tagihanService;

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
    public String konfirmasiResep(@PathVariable long idResep, Model model, Authentication authentication, RedirectAttributes redirectAttrs){
        String role = "";
        if( authentication.getAuthorities().contains(new SimpleGrantedAuthority("Apoteker"))){
            role="Apoteker";
        }
        Resep resep = resepService.getResepById(idResep);
        boolean checkStatus = jumlahService.checkStok(idResep);
        if (checkStatus == true){
            resep.setIsDone(true);
            Appointment appointment = resep.getKodeAppointment();
            appointment.setIsDone(true);
            User user = (User) authentication.getPrincipal();
            UserModel userModel = userService.getUserByUsername(user.getUsername());
            Apoteker apoteker = apotekerService.getApotekerById(userModel.getUuid());
            resep.setConfirmerUuid(apoteker);
            resepService.saveResep(resep);
            appointmentService.save(appointment);

            Tagihan tagihan = new Tagihan();
            tagihan.setKode("BILL-");
            tagihan.setTanggalTerbuat(LocalDateTime.now());
            tagihan.setIsPaid(false);
            tagihan.setKodeAppointment(appointment);
            tagihan.setJumlahTagihan(jumlahService.calculatePrice(idResep) + appointment.getDokter().getTarif());
            tagihanService.saveTagihan(tagihan);

            List<Jumlah> listJumlah = jumlahService.findByResep(idResep);
            model.addAttribute("listJumlah", listJumlah);
            model.addAttribute("resep", resep);
            return "resep/detail-resep";
        }
        redirectAttrs.addFlashAttribute("gagal",
                String.format("Konfirmasi gagal dilakukan karena stok obat tidak mencukupi"));

        return "redirect:/resep/{idResep}";
    }

}
