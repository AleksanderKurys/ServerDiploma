package net.javaservice.diplomaservice.statistic.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javaservice.diplomaservice.authorization.entity.UserEvent;
import net.javaservice.diplomaservice.event.repository.EventRepository;
import net.javaservice.diplomaservice.statistic.response.StatisticResponse;
import net.javaservice.diplomaservice.user.response.DepartmentStatistic;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class StatisticService {

    private final EventRepository repository;
    public StatisticResponse getStatistic(LocalDateTime startDate, LocalDateTime endDate) {
        startDate = startDate == null ? LocalDateTime.of(1900, 1, 1,0,0): startDate;
        endDate = endDate == null ? LocalDateTime.now() : endDate;

        var events = repository.findWhereBetween(
                Date.from(startDate.toInstant(ZoneOffset.UTC)),
                Date.from(endDate.toInstant(ZoneOffset.UTC))
        );
        var usersEvents= events.get(0).getUserEvent();

        var result = events.stream().flatMap(it -> it.getUserEvent().stream().filter(UserEvent::getIsVisited).map(UserEvent::getUser))
                .map(it -> Pair.of(it.getDepartment(), 1))
                .collect(Collectors.groupingBy(Pair::getKey, Collectors.summingInt(Pair::getValue)));

        var list = result.entrySet().stream().map(it -> new DepartmentStatistic(it.getKey(), it.getValue())).toList();
        var count = result.values().stream().reduce(0, Integer::sum);
            return new StatisticResponse(list, count);
    }
}