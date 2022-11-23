package apap.proyek.rumahsehat.controller;

import apap.proyek.rumahsehat.model.Appointment;
import apap.proyek.rumahsehat.model.UserModel;
import apap.proyek.rumahsehat.service.AppointmentService;
import apap.proyek.rumahsehat.service.ResepService;
import apap.proyek.rumahsehat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
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
        UserModel user = userService.getUserByUsername(principal.getName());
        List<Appointment> listAppointment = appointmentService.getListAppointment();
        List<Appointment> listAppointmentDokter = new ArrayList<>();
        for (Appointment i : listAppointment) {
            if (i.getDokter().getUser().getUsername().equals(user.getUsername())) {
                listAppointmentDokter.add(i);
            }
        }
        model.addAttribute("role", role);
        model.addAttribute("listAppointmentDokter", listAppointmentDokter);
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

//        boolean resepAda = false;
//        if (appointment.getResep() != null) {
//            resepAda = true;
//        }

        model.addAttribute("appointment", appointment);
        model.addAttribute("role", role);
//        model.addAttribute("resepAda", resepAda);
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
            return "";
        }
        else {
            //jika status resep sudah selesai
            if (appointment.getResep().getIsDone() == true) {

            }
            //jika status resep belum selesai
            redirectAttributes.addFlashAttribute("gagal", String.format("Resep masih belum selesai sehingga tidak appointment tidak dapat diselesaikan"));
        }

        return "redirect:/appointment/{id}";
    }
}
