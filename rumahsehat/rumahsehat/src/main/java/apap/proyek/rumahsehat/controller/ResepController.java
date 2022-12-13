package apap.proyek.rumahsehat.controller;

import apap.proyek.rumahsehat.model.*;
import apap.proyek.rumahsehat.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.server.ResponseStatusException;


import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
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
        log.info("web get detail resep");
        try{
            String role = "";
            if( authentication.getAuthorities().contains(new SimpleGrantedAuthority("Apoteker"))){
                role="Apoteker";
            } else if( authentication.getAuthorities().contains(new SimpleGrantedAuthority("Pasien"))){
                return "error/403.html";
            }
            Resep resep = resepService.getResepById(idResep);
            List<Jumlah> listJumlah = jumlahService.findByResep(idResep);
            model.addAttribute("resep", resep);
            model.addAttribute("listJumlah", listJumlah);
            model.addAttribute("role", role);
            return "resep/detail-resep";
        } catch (NoSuchElementException e){
            log.error("Error in get detail resep!");
            return "resep/resep-not-found";
        }


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
            tagihan.setTanggalTerbuat(LocalDateTime.now());
            tagihan.setIsPaid(false);
            tagihan.setKodeAppointment(appointment);
            tagihan.setJumlahTagihan(jumlahService.calculatePrice(idResep) + appointment.getDokter().getTarif());
            tagihanService.saveTagihan(tagihan);

            List<Jumlah> listJumlah = jumlahService.findByResep(idResep);
            model.addAttribute("listJumlah", listJumlah);
            model.addAttribute("resep", resep);
            redirectAttrs.addFlashAttribute("sukses",
                    String.format("Konfirmasi berhasil dilakukan!"));
            return "redirect:/resep/{idResep}";
        }
        redirectAttrs.addFlashAttribute("gagal",
                String.format("Konfirmasi gagal dilakukan karena stok obat tidak mencukupi!"));

        return "redirect:/resep/{idResep}";
    }

    @GetMapping({"/daftar-resep"})
    public String listResep(Model model) {
        List<Resep> listResep = this.resepService.findAllResep();
        model.addAttribute("listResep", listResep);
        return "resep/viewall-resep";
    }

    @GetMapping("/create-resep/{idAppointment}")
    public String formCreateResep(@PathVariable String idAppointment, Model model) {
        Appointment appointment = appointmentService.findAppointmentById(idAppointment);
        if (!appointment.getIsDone()){
            Resep resep = new Resep();
            List<Jumlah> listJumlah = new ArrayList<>();
            resep.setListJumlah(listJumlah);

            Jumlah jumlah = new Jumlah();
            JumlahId jumlahId = new JumlahId();
            jumlah.setId(jumlahId);

            resep.getListJumlah().add(jumlah);
            List<Obat> listObat = obatService.getListObat();

            model.addAttribute("listObat", listObat);
            model.addAttribute("resep", resep);
            model.addAttribute("idAppointment", idAppointment);

            return "resep/form-create-resep";
        }
        return "resep/appointment-is-done";
    }

    @PostMapping(
            value = {"/create-resep/{idAppointment}"},
            params = {"addRow"}
    )
    private String addRowMultiple(@PathVariable String idAppointment, @ModelAttribute Resep resep, Model model) {
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
        model.addAttribute("idAppointment", idAppointment);
        return "resep/form-create-resep";
    }

    @PostMapping(
            value = {"/create-resep/{idAppointment}"},
            params = {"deleteRow"}
    )
    private String deleteRowMultiple(@PathVariable String idAppointment, @ModelAttribute Resep resep, @RequestParam("deleteRow") Integer row, Model model) {
        final Integer rowId = Integer.valueOf(row);
        resep.getListJumlah().remove(rowId.intValue());

        List<Obat> listObat = obatService.getListObat();

        model.addAttribute("resep", resep);
        model.addAttribute ( "listObat", listObat);
        model.addAttribute("idAppointment", idAppointment);

        return "resep/form-create-resep";
    }

    @PostMapping(
            value = {"/create-resep/{idAppointment}"},
            params = {"save"}
    )
    public String buatResepSubmit(@PathVariable String idAppointment, @ModelAttribute Resep resep, Model model) {
        if (resep.getListJumlah() == null) {
            resep.setListJumlah(new ArrayList<>());
        }
        Appointment kodeAppointment = appointmentService.findAppointmentById(idAppointment);

        resep.setKodeAppointment(kodeAppointment);
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
        model.addAttribute("idAppointment", idAppointment);
        return "resep/create-resep";
    }
}
