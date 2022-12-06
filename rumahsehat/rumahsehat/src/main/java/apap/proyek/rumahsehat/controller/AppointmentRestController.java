package apap.proyek.rumahsehat.controller;

import apap.proyek.rumahsehat.model.Appointment;
import apap.proyek.rumahsehat.service.AppointmentRestService;
import apap.proyek.rumahsehat.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api")
public class AppointmentRestController {
    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private AppointmentRestService appointmentRestService;

    //viewall appointment
    @GetMapping(value = "/appointment")
    private List<Appointment> retrieveListAppointment() {
        List<Appointment> listAppointment = appointmentService.getListAppointment();
        return listAppointment;
    }

    //view detail appointment
    @GetMapping(value = "/appointment/{id}")
    private Map retrieveDetailAppointment(@PathVariable("id") String id) {
        try {
            return appointmentRestService.getAppointmentById(id);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Appointment dengan ID " + id + " not found"
            );
        }
    }

    //create appointment
    @PostMapping(value = "/create-appointment")
    private Appointment createAppointment(@Valid @RequestBody Appointment appointment, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Request body has invalid type or missing field."
            );
        } else {
            return appointmentRestService.createAppointment(appointment);
        }
    }
}
