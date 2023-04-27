package net.javaservice.diplomaservice.statistic;

import lombok.RequiredArgsConstructor;
import net.javaservice.diplomaservice.statistic.response.StatisticResponse;
import net.javaservice.diplomaservice.statistic.service.StatisticService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/statistic")
@RequiredArgsConstructor
public class StatisticController {
    private final StatisticService service;
    @GetMapping("/getStatistic")
    public ResponseEntity<StatisticResponse> getStatistic(@RequestParam(required = false) LocalDateTime startDate, @RequestParam(required = false) LocalDateTime endDate) {

        return ResponseEntity.ok(service.getStatistic(startDate, endDate));
    }
}
