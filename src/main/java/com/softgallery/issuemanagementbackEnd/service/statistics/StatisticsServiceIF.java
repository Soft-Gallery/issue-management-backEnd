package com.softgallery.issuemanagementbackEnd.service.statistics;

import com.softgallery.issuemanagementbackEnd.dto.IssueDTO;
import com.softgallery.issuemanagementbackEnd.dto.StatisticsDTO;
import com.softgallery.issuemanagementbackEnd.exception.ObjectNotFoundException;
import java.util.HashMap;

public interface StatisticsServiceIF {
    Boolean createIssueStatistics(StatisticsDTO statisticsDTO);

    StatisticsDTO getStatisticsByID(Long statisticsId) throws ObjectNotFoundException;


    // statistics in global region
    Long getNumberOfAllIssues();

    HashMap<String, Long> getNumberOfAllIssuesByPriority();

    HashMap<String, Long> getNumberOfAllIssuesByMainCause();

    HashMap<String, Long> getNumberOfIssuesByState();

    Long getNumberOfIssuesWithinDuration(Long lowerDuration, Long upperDuration);


    // statistics in certain project region
    Long getNumberOfIssuesByProject(Long projectId);

    HashMap<String, Long> getNumberOfIssuesByProjectAndPriority(Long projectId);

    HashMap<String, Long> getNumberOfIssuesByProjectAndMainCause(Long projectId);

    HashMap<String, Long> getNumberOfIssuesByProjectAndState(Long projectId);

    Long getNumberOfIssuesByProjectWithinDuration(Long projectId, Long lowerDuration, Long upperDuration);


    Boolean updateIssueStatistics(StatisticsDTO statisticsDTO, Long id) throws ObjectNotFoundException;

    Boolean deleteIssueStatistics(Long id);
}
