package apap.proyek.rumahsehat.controller;

import apap.proyek.rumahsehat.model.Appointment;
import apap.proyek.rumahsehat.model.AppointmentDto;
import apap.proyek.rumahsehat.model.Dokter;
import apap.proyek.rumahsehat.model.Pasien;
import apap.proyek.rumahsehat.service.AppointmentRestService;
import apap.proyek.rumahsehat.service.AppointmentService;
import apap.proyek.rumahsehat.service.DokterService;
import apap.proyek.rumahsehat.service.PasienService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.List;
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
            Map<String, List<AppointmentDto>> listAppointment = appointmentRestService.getListAppointment(decodedToken.get("uuid"));
            responseEntity = ResponseEntity.ok(listAppointment);
        } catch (NoSuchElementException e) {
            responseEntity = ResponseEntity.badRequest().body(HttpStatus.NOT_FOUND);
        }
        return  responseEntity;
    }

    //view detail appointment
    @GetMapping(value = "/appointment/{id}")
    private ResponseEntity retrieveDetailAppointment(@PathVariable("id") String id) {
        ResponseEntity responseEntity;
        try {
            AppointmentDto appointment = appointmentRestService.getAppointmentById(id);
            responseEntity = ResponseEntity.ok(appointment);
        } catch (NoSuchElementException e) {
            responseEntity = ResponseEntity.badRequest().body(HttpStatus.NOT_FOUND);
        }
        return responseEntity;
    }

    //create appointment
    @PostMapping(value = "/create-appointment")
    private ResponseEntity<String> createAppointment(@RequestHeader("Authorization") String token, @Valid @RequestBody String appointment) {
        Gson gson = new Gson();
        Map<String, String> appointmentMap = gson.fromJson(appointment, new TypeToken<Map<String, String>>() {}.getType());

        try {
            //cek apakah waktu appointment tabrakan atau tidak
            boolean statusDokter = false;
            //waktu awal
            String waktuAwalStr = appointmentMap.get("waktuAwal");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            LocalDateTime waktuAwal = LocalDateTime.parse(waktuAwalStr, formatter);
            //dokter
            String dokterStr = appointmentMap.get("dokter");
            Dokter dokter = dokterService.getDokterById(dokterStr);
            for (Appointment i : dokter.getListAppointment()) {
                if (waktuAwal.isBefore(i.getWaktuAwal().plusHours(1)) && waktuAwal.isAfter(i.getWaktuAwal())) {
                    statusDokter = true;
                }
                else {
                    statusDokter = false;
                }
            }
            //tidak tabrakan
            if (statusDokter == true) {
                Appointment appointmentNew = new Appointment();
                //id
                String id = appointmentMap.get("id");
                //pasien
                String pasienStr = appointmentMap.get("pasien");
                Pasien pasien = pasienService.getPasienById(pasienStr);
                appointmentRestService.createAppointment(appointmentNew, waktuAwal, pasien, dokter);
                return ResponseEntity.ok("Appointment dengan ID " + id + " berhasil dibuat");
            }
            //tabrakan
            else {
                throw new ResponseStatusException(
                        HttpStatus.CONFLICT, "Waktu appointment dengan dokter yang dipilih bertabrakan dengan jadwal appointment lain"
                );
            }
        }
        catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Request body has invalid type or missing field"
            );
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
