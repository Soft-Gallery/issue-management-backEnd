package com.softgallery.issuemanagementbackEnd.dto.statistics;

import com.softgallery.issuemanagementbackEnd.service.issue.MainCause;
import java.time.Duration;
import java.time.LocalDateTime;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
public class StatisticsDTO {
    private Long id;
    private Long issueId;
    private Long projectId;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endDate;
    private Long duration;
    private MainCause mainCause;

    // Duration(시간차) Long 값으로 전달
    private Long calculateDuration() {
        Duration duration = Duration.between(startDate, endDate);
        if(duration.isNegative()) return 0L;
        else return duration.getSeconds();
    }

    public StatisticsDTO() { }
    public StatisticsDTO(final Long id, final Long issueId, final Long projectId, final LocalDateTime startDate,
                         final LocalDateTime endDate,
                         final MainCause mainCause) {
        this.id = id;
        this.issueId = issueId;
        this.projectId = projectId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.duration = calculateDuration();
        this.mainCause = mainCause;
    }
}
