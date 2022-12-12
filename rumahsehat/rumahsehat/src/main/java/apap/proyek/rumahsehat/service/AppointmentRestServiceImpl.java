package apap.proyek.rumahsehat.service;

import apap.proyek.rumahsehat.model.*;
import apap.proyek.rumahsehat.repository.AppointmentDb;
import apap.proyek.rumahsehat.repository.DokterDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Transactional
public class AppointmentRestServiceImpl implements AppointmentRestService {
    @Autowired
    private AppointmentDb appointmentDb;

    @Autowired
    private DokterDb dokterDb;

    @Override
    public Map<String, List<AppointmentDto>> getListAppointment(String uuid) {
        List<Appointment> listAppointment = appointmentDb.findByUuid(uuid);
        Map<String, List<AppointmentDto>> map = new HashMap<>();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");

        List<AppointmentDto> list = new ArrayList<>();
        for (Appointment appointment : listAppointment) {
            AppointmentDto appointmentDto = new AppointmentDto();
            appointmentDto.setId(appointment.getId());
            appointmentDto.setDokter(appointment.getDokter().getUser().getNama());
            appointmentDto.setPasien(appointment.getPasien().getUser().getNama());
            appointmentDto.setWaktuAwal(appointment.getWaktuAwal().format(dateTimeFormatter));
            appointmentDto.setStatus(appointment.getIsDone());
            if (appointment.getResep() == null) {
                appointmentDto.setResep(null);
            }
            else {
                appointmentDto.setResep(appointment.getResep().getId());
            }
            list.add(appointmentDto);
        }

        map.put("appointment", list);
        return map;
    }

    @Override
    public Map<String, Integer> getListDokterTarif() {
        List<Dokter> listDokter = dokterDb.findAll();
        Map<String, Integer> map = new HashMap<>();

        for (Dokter dokter : listDokter) {
            map.put(dokter.getUser().getNama(), dokter.getTarif());
        }
        return map;
    }

    @Override
    public AppointmentDto getAppointmentById(String id) {
        Optional<Appointment> appointment = appointmentDb.findById(id);
        Appointment appointmentPilihan = null;
        if (appointment.isPresent()) {
            appointmentPilihan = appointment.get();
        } else {
            throw new NoSuchElementException();
        }

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        AppointmentDto appointmentDto = new AppointmentDto();
        appointmentDto.setId(appointmentPilihan.getId());
        appointmentDto.setWaktuAwal(appointmentPilihan.getWaktuAwal().format(dateTimeFormatter));
        appointmentDto.setStatus(appointmentPilihan.getIsDone());
        appointmentDto.setDokter(appointmentPilihan.getDokter().getUser().getNama());
        appointmentDto.setPasien(appointmentPilihan.getPasien().getUser().getNama());
        if (appointmentPilihan.getResep() == null) {
            appointmentDto.setResep(null);
        } else {
            appointmentDto.setResep(appointmentPilihan.getResep().getId());
        }
        return appointmentDto;
    }

    @Override
    public Appointment createAppointment(Appointment appointment, String id, LocalDateTime waktuAwal, Pasien pasien, Dokter dokter) {
        //set id
        appointment.setId(id);
        //set waktuAwal
        appointment.setWaktuAwal(waktuAwal);
        //set isDone
        appointment.setIsDone(false);
        //set pasien
        appointment.setPasien(pasien);
        //set dokter
        appointment.setDokter(dokter);
        //tagihan
        appointment.setTagihan(null);
        //resep
        appointment.setResep(null);
        return appointmentDb.save(appointment);
    }
}
