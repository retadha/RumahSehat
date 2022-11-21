package apap.proyek.rumahsehat.controller;

import apap.proyek.rumahsehat.model.Appointment;
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
    public String listAppointment(Model model, Authentication authentication) {
        String role = "";
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("Admin"))) {
            role = "Admin";
        }
        else if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("Dokter"))) {
            role = "Dokter";
        }
        String currentUsernameId = authentication.getName();
        List<Appointment> listAppointment = appointmentService.getListAppointment();
        List<Appointment> listAppointmentDokter = new ArrayList<>();
        for (Appointment i : listAppointment) {
            if (i.getDokter().getUser().getUuid() == currentUsernameId) {
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
        Appointment appointment = appointmentService.getAppointmentById(id);
        model.addAttribute("appointment", appointment);
        return "appointment/view-appointment";
    }
}
