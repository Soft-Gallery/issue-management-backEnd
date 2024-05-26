package com.softgallery.issuemanagementbackEnd.dto;

import com.softgallery.issuemanagementbackEnd.service.issue.MainCause;
import com.softgallery.issuemanagementbackEnd.service.issue.Priority;
import com.softgallery.issuemanagementbackEnd.service.issue.State;
import java.time.Duration;
import java.time.LocalDateTime;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
public class StatisticsDTO {
    private Long id;
    private Long issueId;
    private Long projectId;
    private Priority priority;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endDate = LocalDateTime.of(1990, 1, 1, 0, 0,0 );
    private Long duration;
    private State state;
    private MainCause mainCause;

    // Duration(시간차) Long 값으로 전달
    private Long calculateDuration() {
        Duration duration = Duration.between(startDate, endDate);
        if(duration.isNegative()) return 0L;
        else return duration.getSeconds();
    }

    public StatisticsDTO() { }

    public StatisticsDTO(final Long issueId, final Long projectId, final Priority priority,
                         final LocalDateTime startDate, final LocalDateTime endDate, final State state) {
        this.issueId = issueId;
        this.projectId = projectId;
        this.priority = priority;
        this.startDate = startDate;
        this.endDate = endDate;
        this.duration = calculateDuration();
        this.state = state;
    }

    public StatisticsDTO(final Long issueId, final Long projectId, final Priority priority, final LocalDateTime startDate,
                         final LocalDateTime endDate, final State state,
                         final MainCause mainCause) {
        this.issueId = issueId;
        this.projectId = projectId;
        this.priority = priority;
        this.startDate = startDate;
        this.endDate = endDate;
        this.duration = calculateDuration();
        this.state = state;
        this.mainCause = mainCause;
    }

    public StatisticsDTO(final Long id, final Long issueId, final Long projectId, final Priority priority, final LocalDateTime startDate,
                         final LocalDateTime endDate, final State state,
                         final MainCause mainCause) {
        this.id = id;
        this.issueId = issueId;
        this.projectId = projectId;
        this.priority = priority;
        this.startDate = startDate;
        this.endDate = endDate;
        this.duration = calculateDuration();
        this.state = state;
        this.mainCause = mainCause;
    }
}
