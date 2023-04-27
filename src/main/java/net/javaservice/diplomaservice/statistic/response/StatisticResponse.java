package net.javaservice.diplomaservice.statistic.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.javaservice.diplomaservice.user.response.DepartmentStatistic;

import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StatisticResponse {
    private List<DepartmentStatistic> departmentStatistics;
    private Integer totalPeopleCount;
}