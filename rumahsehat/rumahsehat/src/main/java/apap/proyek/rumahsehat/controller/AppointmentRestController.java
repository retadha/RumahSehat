package apap.proyek.rumahsehat.controller;

import apap.proyek.rumahsehat.model.Appointment;
import apap.proyek.rumahsehat.model.Pasien;
import apap.proyek.rumahsehat.model.Dokter;
import apap.proyek.rumahsehat.service.AppointmentRestService;
import apap.proyek.rumahsehat.service.AppointmentService;
import apap.proyek.rumahsehat.service.DokterService;
import apap.proyek.rumahsehat.service.PasienService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Base64;
import java.util.Map;
import java.util.NoSuchElementException;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class AppointmentRestController {
    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private AppointmentRestService appointmentRestService;

    @Autowired
    private PasienService pasienService;

    @Autowired
    private DokterService dokterService;

    //viewall appointment
    @GetMapping(value = "/appointment")
    private ResponseEntity retrieveListAppointment(@RequestHeader("Authorization") String token) {
        Map<String, String> decodedToken = decode(token);
        ResponseEntity responseEntity;
        try {
            
        }
        return appointmentRestService.getListAppointment(decodedToken.get("uuid"));
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
    private Appointment createAppointment(@RequestHeader("Authorization") String token, @Valid @RequestBody Appointment appointment, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Request body has invalid type or missing field."
            );
        } else {
            //pasien
            Map<String, String> decodedToken = decode(token);
            String uuid = decodedToken.get("uuid");
            Pasien pasien = pasienService.getPasienById(uuid);
            appointment.setPasien(pasien);
            //dokter
            return appointmentRestService.createAppointment(appointment);
        }
    }

    private Map<String, String> decode(String token) {
        String[] chunks = token.split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String payload = new String(decoder.decode(chunks[1]));
        Gson gson = new Gson();
        Map<String, String> decodedToken = gson.fromJson(payload, new TypeToken<Map<String, String>>() {}.getType());
        return decodedToken;
    }
}
