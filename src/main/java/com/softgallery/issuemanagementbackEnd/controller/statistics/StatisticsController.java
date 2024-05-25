package com.softgallery.issuemanagementbackEnd.controller.statistics;

import com.softgallery.issuemanagementbackEnd.dto.IssueDTO;
import com.softgallery.issuemanagementbackEnd.dto.StatisticsDTO;
import com.softgallery.issuemanagementbackEnd.exception.ObjectNotFoundException;
import com.softgallery.issuemanagementbackEnd.service.statistics.StatisticsServiceIF;
import java.util.HashMap;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
@RequestMapping("/statistics")
public class StatisticsController {
    private final StatisticsServiceIF statisticsService;

    public StatisticsController(final StatisticsServiceIF statisticsService) {
        this.statisticsService = statisticsService;
    }

    @PostMapping("/create")
    public Boolean createIssueStatistics(@RequestBody IssueDTO issueDTO) {
        return statisticsService.createIssueStatistics(issueDTO);
    }

    @GetMapping("/get/{statisticsId}")
    public StatisticsDTO getStatisticsById(@PathVariable Long statisticsId) throws ObjectNotFoundException {
        return statisticsService.getStatisticsByID(statisticsId);
    }

    // 여기서부터 전체 프로젝트의 이슈 대상
    @GetMapping("/get/global/")
    public Long getNumbers() {
        return statisticsService.getNumberOfAllIssues();
    }

    @GetMapping("/get/global/priority")
    public HashMap<String, Long> getNumbersByPriority() {
        return statisticsService.getNumberOfAllIssuesByPriority();
    }

    @GetMapping("/get/global/mainCause")
    public HashMap<String, Long> getNumbersByMainCause() {
        return statisticsService.getNumberOfAllIssuesByMainCause();
    }

//    @GetMapping("/get/global/state")
//    public HashMap<String, Long> getNumbersByState() {
//        return statisticsService.getNumberOfIssuesByState();
//    }


    // 여기서부터는 특정 프로젝트의 이슈 대상
    @GetMapping("/get/project/{projectId}")
    public Long getNumberOfIssuesInProject(@PathVariable Long projectId) {
        return statisticsService.getNumberOfIssuesByProject(projectId);
    }

    @GetMapping("/get/project/priority/{projectId}")
    public HashMap<String, Long> getNumberOfIssuesByProjectAndPriority(@PathVariable Long projectId) {
        return statisticsService.getNumberOfIssuesByProjectAndPriority(projectId);
    }

    @GetMapping("/get/project/mainCause/{projectId}")
    public HashMap<String, Long> getNumberOfIssuesByProjectAndMainCause(@PathVariable Long projectId) {
        return statisticsService.getNumberOfIssuesByProjectAndMainCause(projectId);
    }

//    @GetMapping("/get/project/state/{projectId}")
//    public HashMap<String, Long> getNumbersByProjectAndState(@PathVariable Long projectId) {
//        return statisticsService.getNumberOfIssuesByProjectAndState(projectId);
//    }


    @PostMapping("/update/{statisticsId}")
    public Boolean updateIssueStatistics(@RequestBody StatisticsDTO statisticsDTO, @PathVariable Long statisticsId) throws ObjectNotFoundException {
        return statisticsService.updateIssueStatistics(statisticsDTO, statisticsId);
    }

    @DeleteMapping("/delete/{statisticsId}")
    public Boolean deleteIssueStatistics(@PathVariable Long statisticsId) {
        return statisticsService.deleteIssueStatistics(statisticsId);
    }
}
