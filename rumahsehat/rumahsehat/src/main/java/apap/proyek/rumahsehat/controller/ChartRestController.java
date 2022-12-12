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

    @RequestMapping(value = "/chart/line-chart-bulanan", method = RequestMethod.GET)
    private ResponseEntity getMonthlySalary(
            @RequestParam(value = "id") List<String> values,
            @RequestParam(value = "bulan") String month,
            @RequestParam(value = "tahun") String year) {
        log.info("api get salary bulan");
        ResponseEntity responseEntity = null;
        try {
            responseEntity = ResponseEntity.ok(chartService.getMonthlySalary(values, Integer.parseInt(month), Integer.parseInt(year)));
        } catch (Exception e) {
            log.error("error in get salary bulan");
            responseEntity = ResponseEntity.badRequest().body(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @RequestMapping(value = "/chart/line-chart-tahunan", method = RequestMethod.GET)
    private ResponseEntity getYearlySalary(
            @RequestParam(value = "id") List<String> values,
            @RequestParam(value = "tahun") String year) {
        log.info("api get salary tahun");
        ResponseEntity responseEntity = null;
        try {
            responseEntity = ResponseEntity.ok(chartService.getYearlySalary(values, Integer.parseInt(year)));
        } catch (Exception e) {
            log.error("error in get salary tahun");
            responseEntity = ResponseEntity.badRequest().body(HttpStatus.INTERNAL_SERVER_ERROR);

        }
        return responseEntity;
    }

    @RequestMapping(value="/chart/kumulatif-chart", method = RequestMethod.GET)
    private ResponseEntity getCumulativeSalary(
            @RequestParam(value = "id") List<String> values) {
        log.info("api get salary cumulatif");
        ResponseEntity responseEntity = null;
        try {
            responseEntity = ResponseEntity.ok(chartService.getCumulativeSalary(values));
        } catch (Exception e) {
            log.error("error in get salary bulan");
            responseEntity = ResponseEntity.badRequest().body(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }
}