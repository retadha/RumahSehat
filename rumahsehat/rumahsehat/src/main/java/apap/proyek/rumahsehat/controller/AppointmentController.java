package apap.proyek.rumahsehat.controller;

import apap.proyek.rumahsehat.model.Appointment;
import apap.proyek.rumahsehat.model.Tagihan;
import apap.proyek.rumahsehat.model.UserModel;
import apap.proyek.rumahsehat.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AppointmentController {
    @Qualifier("appointmentServiceImpl")
    @Autowired
    private AppointmentService appointmentService;

    @Qualifier("resepServiceImpl")
    @Autowired
    private ResepService resepService;

    @Qualifier("userServiceImpl")
    @Autowired
    private UserService userService;

    @Qualifier("jumlahServiceImpl")
    @Autowired
    private JumlahService jumlahService;

    @Qualifier("tagihanServiceImpl")
    @Autowired
    private TagihanService tagihanService;

    //melihat semua jadwal appointment
    @GetMapping("/appointment")
    public String listAppointment(Model model, Authentication authentication, Principal principal) {
        String role = "";
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("Admin"))) {
            role = "Admin";
        }
        else if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("Dokter"))) {
            role = "Dokter";
        }
        else if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("Pasien"))) {
            role = "Pasien";
        }

        List<Appointment> listAppointment = appointmentService.getListAppointment();
        if (role.equals("Dokter")) {
            UserModel user = userService.getUserByUsername(principal.getName());
            List<Appointment> listAppointmentDokter = new ArrayList<>();
            for (Appointment i : listAppointment) {
                if (i.getDokter().getUser().getUsername().equals(user.getUsername())) {
                    listAppointmentDokter.add(i);
                }
            }
            model.addAttribute("listAppointmentDokter", listAppointmentDokter);
        }
        else if (role.equals("Pasien")) {
            UserModel user = userService.getUserByUsername(principal.getName());
            List<Appointment> listAppointmentPasien = new ArrayList<>();
            for (Appointment e : listAppointment) {
                if (e.getPasien().getUser().getUsername().equals(user.getUsername())) {
                    listAppointmentPasien.add(e);
                }
            }
            model.addAttribute("listAppointmentPasien", listAppointmentPasien);
        }

        model.addAttribute("role", role);
        model.addAttribute("listAppointment", listAppointment);
        return "appointment/viewall-appointment";
    }

    //melihat detail appointment
    @GetMapping("/appointment/{id}")
    public String viewDetailAppointment(@PathVariable String id, Model model, Authentication authentication) {
        String role = "";
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("Dokter"))) {
            role = "Dokter";
        }

        Appointment appointment = appointmentService.getAppointmentById(id);

        model.addAttribute("appointment", appointment);
        model.addAttribute("role", role);
        return "appointment/view-appointment";
    }

    @PostMapping("/appointment/{id}")
    public String selesaikanAppointment(@PathVariable String id, Model model, Authentication authentication, RedirectAttributes redirectAttributes) {
        String role = "";
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("Dokter"))) {
            role = "Dokter";
        }

        Appointment appointment = appointmentService.getAppointmentById(id);
        //jika resep tidak ada
        if (appointment.getResep() == null) {
            model.addAttribute("appointment", appointment);
//            return "appointment/finish-appointment-without-resep";
        }
        else {
            //jika status resep sudah selesai
            if (appointment.getResep().getIsDone() == true) {
                appointment.setIsDone(true);

                Tagihan tagihan = new Tagihan();
                int jumlahTagihan = tagihanService.getListTagihan().size() + 1;
                tagihan.setKode("BILL-" + Integer.toString(jumlahTagihan));
                tagihan.setTanggalTerbuat(LocalDateTime.now());
                tagihan.setIsPaid(false);
                tagihan.setKodeAppointment(appointment);
                tagihan.setJumlahTagihan(appointment.getDokter().getTarif());
                tagihanService.saveTagihan(tagihan);

                model.addAttribute("role", role);
                model.addAttribute("appointment", appointment);
                return "appointment/view-appointment";
            }
            //jika status resep belum selesai
            redirectAttributes.addFlashAttribute("gagal",
                    String.format("Resep masih belum selesai sehingga appointment tidak dapat diselesaikan"));
        }

        return "redirect:/appointment/{id}";
    }

    //get mappring konfirmasi
    @GetMapping("/appointment/konfirmasi/{id}")
    public String getAppointment(@PathVariable String id, Model model) {
        Appointment appointment = appointmentService.getAppointmentById(id);
        model.addAttribute("appointment", appointment);
        return "appointment/finish-appointment-without-resep";
    }

    //post mapping konfirmasi
    @PostMapping("/appointment/konfirmasi/{id}")
    public String selesaikanAppointment2(@PathVariable String id, Model model) {
        Appointment appointment = appointmentService.getAppointmentById(id);
        appointment.setIsDone(true);

        Tagihan tagihan = new Tagihan();
        int jumlahTagihan = tagihanService.getListTagihan().size() + 1;
        tagihan.setKode("BILL-" + Integer.toString(jumlahTagihan));
        tagihan.setTanggalTerbuat(LocalDateTime.now());
        tagihan.setIsPaid(false);
        tagihan.setKodeAppointment(appointment);
        tagihan.setJumlahTagihan(appointment.getDokter().getTarif());
        tagihanService.saveTagihan(tagihan);

        model.addAttribute("appointment", appointment);
        return "appointment/view-appointment";
    }

    //get mapping membuat appointment
    @GetMapping("/create-appointment")
    public String addAppointmentFormPage(Model model, Authentication authentication) {
        String role = "";
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("Pasien"))) {
            role = "Pasien";
        }
        Appointment appointment = new Appointment();

        model.addAttribute("appointment", appointment);
        model.addAttribute("role", role);
        return "appointment/form-add-appointment";
    }

    //post mapping membuat appointment
    @PostMapping("/create-appointment")
    public String addAppointmentSubmit(@ModelAttribute Appointment appointment, Model model, RedirectAttributes redirectAttributes) {
        //cek apakah waktu appointment tabrakan atau tidak
        boolean statusDokter = false;
        //pake isbefore isafter
//        for (Appointment i : appointment.getDokter().getListAppointment()) {
//
//        }

        //waktu appointment dengan dokter tidak tabrakan
        if (statusDokter == true) {
            appointmentService.save(appointment);

            appointment.setIsDone(false);
            int jumlahAppointment = appointmentService.getListAppointment().size() + 1;
            appointment.setId("APT-" + Integer.toString(jumlahAppointment));

            model.addAttribute("kodeAppointment", appointment.getId());
            return "appointment/add-appointment";
        }
        //waktu dokter tabrakan
        redirectAttributes.addFlashAttribute("gagal",
                String.format("Waktu appointment dengan dokter yang dipilih bertabrakan dengan jadwal appointment lain"));

        return "appointment/viewall-appointment";
    }
}
