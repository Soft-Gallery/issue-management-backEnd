package com.softgallery.issuemanagementbackEnd.dto;

import com.softgallery.issuemanagementbackEnd.service.issue.MainCause;
import com.softgallery.issuemanagementbackEnd.service.issue.Priority;
import java.time.Duration;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class StatisticsDTO {
    private Long id;
    private Long issueId;
    private Long projectId;
    private Priority priority;
    private LocalDateTime startDate;
    private LocalDateTime endDate = LocalDateTime.MIN;
    private Long duration;
    private MainCause mainCause;

    // Duration(시간차) Long 값으로 전달
    private Long calculateDuration() {
        Duration duration = Duration.between(startDate, endDate);
        if(duration.isNegative()) return 0L;
        else return duration.getSeconds();
    }

    public StatisticsDTO() { }

    public StatisticsDTO(final Long issueId, final Long projectId, final Priority priority, final LocalDateTime startDate) {
        this.issueId = issueId;
        this.projectId = projectId;
        this.priority = priority;
        this.startDate = startDate;
        this.duration = calculateDuration();
    }

    public StatisticsDTO(final Long issueId, final Long projectId, final Priority priority, final LocalDateTime startDate,
                         final LocalDateTime endDate,
                         final MainCause mainCause) {
        this.issueId = issueId;
        this.projectId = projectId;
        this.priority = priority;
        this.startDate = startDate;
        this.endDate = endDate;
        this.duration = calculateDuration();
        this.mainCause = mainCause;
    }

    public StatisticsDTO(final Long id, final Long issueId, final Long projectId, final Priority priority, final LocalDateTime startDate,
                         final LocalDateTime endDate,
                         final MainCause mainCause) {
        this.id = id;
        this.issueId = issueId;
        this.projectId = projectId;
        this.priority = priority;
        this.startDate = startDate;
        this.endDate = endDate;
        this.duration = calculateDuration();
        this.mainCause = mainCause;
    }
}
