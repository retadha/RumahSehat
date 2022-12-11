package apap.proyek.rumahsehat.controller;

import apap.proyek.rumahsehat.dto.chart.PendapatanBulanan;
import apap.proyek.rumahsehat.dto.chart.PendapatanKumulatif;
import apap.proyek.rumahsehat.dto.chart.PendapatanTahunan;
import apap.proyek.rumahsehat.service.ChartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/chart")
public class ChartRestController {
    @Autowired
    private ChartService chartService;

    @GetMapping(value = "/api/all")
    private List<PendapatanTahunan> gYearlySalaries() {
        return chartService.getThisYearSalary();
    }

    @GetMapping("/api/line-chart-bulanan")
    private List<PendapatanBulanan> getMonthlySalary(
            @RequestParam(value = "id") List<String> values,
            @RequestParam(value = "bulan") String month,
            @RequestParam(value = "tahun") String year) {
        return chartService.getMonthlySalary(values, Integer.parseInt(month), Integer.parseInt(year));
    }

    @GetMapping("/api/line-chart-tahunan")
    private List<PendapatanTahunan> getYearlySalary(
            @RequestParam(value = "id") List<String> values,
            @RequestParam(value = "tahun") String year) {
        return chartService.getYearlySalary(values, Integer.parseInt(year));
    }

    @GetMapping("/api/kumulatif-chart")
    private List<PendapatanKumulatif> getCumulativeSalary(
            @RequestParam(value = "id") List<String> values) {
        return chartService.getCumulativeSalary(values);
    }
}