package apap.proyek.rumahsehat.service;

import apap.proyek.rumahsehat.dto.chart.Pendapatan;
import apap.proyek.rumahsehat.dto.chart.PendapatanBulanan;
import apap.proyek.rumahsehat.dto.chart.PendapatanKumulatif;
import apap.proyek.rumahsehat.dto.chart.PendapatanTahunan;
import apap.proyek.rumahsehat.model.Dokter;
import apap.proyek.rumahsehat.repository.DokterDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ChartService {
    @Autowired
    DokterDb dokterDb;

    private List<Pendapatan> getAllSalary() {
        List<Dokter> listDokter = dokterDb.findAll();
        List<Pendapatan> pendapatanDokter = new ArrayList<>();
        for (Dokter dokter : listDokter)
            pendapatanDokter.add(new Pendapatan(dokter, dokter.getListAppointment()));
        return pendapatanDokter;
    }

    private List<Pendapatan> getRelatedSalary(List<String> listId) {
        List<Dokter> doctors = dokterDb.findAll();
        List<Pendapatan> doctorSalaries = new ArrayList<>();
        List<Dokter> filteredDoctors = new ArrayList<>();
        for (Dokter doctor : doctors)
            if (listId.contains(doctor.getUuid()))
                filteredDoctors.add(doctor);
        for (Dokter doctor : filteredDoctors)
            doctorSalaries.add(new Pendapatan(doctor, doctor.getListAppointment()));
        return doctorSalaries;
    }

    public List<PendapatanTahunan> getThisYearSalary() {
        int year = LocalDate.now().getYear();
        List<Pendapatan> relatedSalary = getAllSalary();

        List<PendapatanTahunan> yearlySalaries = new ArrayList<>();

        for (Pendapatan salary : relatedSalary) {
            PendapatanTahunan yearlySalary = new PendapatanTahunan(salary.getDokter(), salary.getAppointments())
                    .selectAppointmentByYear(year)
                    .calculate();
            yearlySalaries.add(yearlySalary);
        }
        return yearlySalaries;
    }

    public List<PendapatanTahunan> getYearlySalary(List<String> listId, int year) {
        List<Pendapatan> relatedSalary = getRelatedSalary(listId);
        List<PendapatanTahunan> yearlySalaries = new ArrayList<>();
        for (Pendapatan salary : relatedSalary) {
            PendapatanTahunan yearlySalary = new PendapatanTahunan(salary.getDokter(), salary.getAppointments())
                    .selectAppointmentByYear(year)
                    .calculate();
            yearlySalaries.add(yearlySalary);
        }
        return yearlySalaries;
    }

    public List<PendapatanBulanan> getMonthlySalary(List<String> listId, int month, int year) {
        List<Pendapatan> relatedSalary = getRelatedSalary(listId);
        List<PendapatanBulanan> monthlySalaries = new ArrayList<>();
        for (Pendapatan salary : relatedSalary) {
            PendapatanBulanan monthlySalary = new PendapatanBulanan(salary.getDokter(), salary.getAppointments())
                    .selectAppointmentByMonth(year, month)
                    .calculate();
            monthlySalaries.add(monthlySalary);
        }
        return monthlySalaries;
    }

    public List<PendapatanKumulatif> getCumulativeSalary(List<String> listId) {
        List<Pendapatan> relatedSalary = getRelatedSalary(listId);
        List<PendapatanKumulatif> cumulativeSalaries = new ArrayList<>();
        for (Pendapatan salary : relatedSalary) {
            PendapatanKumulatif cumulativeSalary = new PendapatanKumulatif(salary.getDokter(), salary.getAppointments())
                    .calculate();
            cumulativeSalaries.add(cumulativeSalary);
        }
        return cumulativeSalaries;
    }
}
