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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @Qualifier("obatServiceImpl")
    @Autowired
    private ObatService obatService;

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

    @GetMapping({"/daftar-resep"})
    public String listResep(Model model) {
        List<Resep> listResep = this.resepService.findAllResep();
        model.addAttribute("listResep", listResep);
        return "resep/viewall-resep";
    }

    @GetMapping("/create-resep/{idAppoinment}")
    public String formCreateResep(@PathVariable String idAppoinment, Model model) {
        Resep resep = new Resep();
        Appointment appointment = appointmentService.findAppointmentById(idAppoinment);
        List<Jumlah> listJumlah = new ArrayList<>();
        if (!appointment.getIsDone()){
//            appointment.setResep(resep);
            resep.setListJumlah(listJumlah);

            Jumlah jumlah = new Jumlah();
            JumlahId jumlahId = new JumlahId();
            jumlah.setId(jumlahId);

            resep.getListJumlah().add(jumlah);
            List<Obat> listObat = obatService.getListObat();

            model.addAttribute("listObat", listObat);
            model.addAttribute("resep", resep);
            model.addAttribute("idAppoinment", idAppoinment);

            return "resep/form-create-resep";
        }
        return "appointment-not-found";
    }

    @PostMapping(
            value = {"/create-resep/{idAppoinment}"},
            params = {"addRow"}
    )
    private String addRowMultiple(@PathVariable String idAppoinment, @ModelAttribute Resep resep, Model model) {
        if (resep.getListJumlah() == null || resep.getListJumlah().size() == 0) {
            resep.setListJumlah(new ArrayList<>());
        }

        Jumlah jumlah = new Jumlah();
        JumlahId jumlahId = new JumlahId();
        jumlah.setId(jumlahId);

        resep.getListJumlah().add(jumlah);

        List<Obat> listObat = obatService.getListObat();

        model.addAttribute("resep", resep);
        model.addAttribute ( "listObat", listObat);
        model.addAttribute("idAppoinment", idAppoinment);
        return "resep/form-create-resep";
    }

    @PostMapping(
            value = {"/create-resep/{idAppoinment}"},
            params = {"deleteRow"}
    )
    private String deleteRowMultiple(@PathVariable String idAppoinment, @ModelAttribute Resep resep, @RequestParam("deleteRow") Integer row, Model model) {
        final Integer rowId = Integer.valueOf(row);
        resep.getListJumlah().remove(rowId.intValue());

        List<Obat> listObat = obatService.getListObat();

        model.addAttribute("resep", resep);
        model.addAttribute ( "listObat", listObat);
        model.addAttribute("idAppoinment", idAppoinment);

        return "resep/form-create-resep";
    }

    @PostMapping(
            value = {"/create-resep/{idAppoinment}"},
            params = {"save"}
    )
    public String buatResepSubmit(@PathVariable String idAppoinment, @ModelAttribute Resep resep, Model model) {
        if (resep.getListJumlah() == null) {
            resep.setListJumlah(new ArrayList<>());
        }
        Appointment kodeAppoinment = appointmentService.findAppointmentById(idAppoinment);

        resep.setKodeAppointment(kodeAppoinment);
        resep.setCreatedAt(LocalDateTime.now());
        resep.setIsDone(false);
        Resep savedResep = resepService.saveResep(resep);

        for (Jumlah jumlah: resep.getListJumlah()) {
            Jumlah newJumlah = new Jumlah();
            JumlahId jumlahId = new JumlahId();
            newJumlah.setId(jumlahId);
            newJumlah.setResep(savedResep);
            String idObat = jumlah.getObat().getIdObat();
            Obat obat = obatService.getObatByIdObat(idObat);
            newJumlah.setObat(obat);
            Integer kuantitas = jumlah.getKuantitas();
            newJumlah.setKuantitas(kuantitas);

            jumlahService.addJumlah(newJumlah);
        }
        model.addAttribute("resep", resep);
        model.addAttribute("idAppoinment", idAppoinment);
        return "resep/create-resep";
    }
}
