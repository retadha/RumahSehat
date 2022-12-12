package apap.proyek.rumahsehat.controller;

import apap.proyek.rumahsehat.dto.chart.PendapatanBulanan;
import apap.proyek.rumahsehat.dto.chart.PendapatanKumulatif;
import apap.proyek.rumahsehat.dto.chart.PendapatanTahunan;
import apap.proyek.rumahsehat.service.ChartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("/api")
public class ChartRestController {
    @Autowired
    private ChartService chartService;

    @RequestMapping(value = "/chart/all", method = RequestMethod.GET)
    private ResponseEntity getYearlySalaries() {
        log.info("api get all book");
        ResponseEntity responseEntity = null;
        try {
            responseEntity = ResponseEntity.ok(chartService.getThisYearSalary());
        } catch (Exception e) {
            log.error("Error in get all salaries");
            responseEntity = ResponseEntity.badRequest().body(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @GetMapping("/chart/line-chart-bulanan")
    private List<PendapatanBulanan> getMonthlySalary(
            @RequestParam(value = "id") List<String> values,
            @RequestParam(value = "bulan") String month,
            @RequestParam(value = "tahun") String year) {
        return chartService.getMonthlySalary(values, Integer.parseInt(month), Integer.parseInt(year));
    }

    @GetMapping("/chart/line-chart-tahunan")
    private List<PendapatanTahunan> getYearlySalary(
            @RequestParam(value = "id") List<String> values,
            @RequestParam(value = "tahun") String year) {
        return chartService.getYearlySalary(values, Integer.parseInt(year));
    }

    @GetMapping("/chart/kumulatif-chart")
    private List<PendapatanKumulatif> getCumulativeSalary(
            @RequestParam(value = "id") List<String> values) {
        return chartService.getCumulativeSalary(values);
    }
}