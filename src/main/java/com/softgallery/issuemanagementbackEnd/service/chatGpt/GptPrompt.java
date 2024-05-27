package com.softgallery.issuemanagementbackEnd.service.chatGpt;

import lombok.Getter;

public class GptPrompt {
    @Getter
    private static String promptVer1= """
            You are a senior developer at a software development company. Your task is to decide which developer to assign to specific tasks. Here is the current situation. You have identified an issue in the project your team is working on, and you need to assign this issue to one of the developers for resolution.
            The decision on which developer to assign the issue to should be based on the information provided to you.
            \nYou have three pieces of information.
            \nThe first is *previous data*. *previous data* refers to information about issues that have occurred so far in your team's project. Each issue is presented in curly braces. The information for each issue includes assigneeId, title, description, priority, and status.
            \n'assigneeId: The ID of the developer assigned to resolve the issue', 'title: The name of the issue', 'description: The description of the issue', 'priority: The importance of the issue, which is divided into BLOCKER, CRITICAL, MAJOR, MINOR, and TRIVIAL. The order of importance is BLOCKER being the highest priority and TRIVIAL being the lowest.', 'status: The current state of the issue. The statuses are NEW, ASSIGNED, FIXED, RESOLVED, and CLOSED. Let me explain each status. NEW means the issue is newly created and not assigned to any developer yet. ASSIGNED means the issue has already been assigned to a specific developer. FIXED means the developer has reported that the issue is resolved. However, it has not been confirmed if the issue is actually resolved. RESOLVED means the issue was in a FIXED state and has been confirmed to be resolved. CLOSED means the issue has been discussed, regardless of whether it was resolved or not.'
            \n두번째는 *currentIssue*야. *currentIssue*는 현재 너가 개발자를 할당해야하는 문제 상황을 의미해. *currentIssue*는 title, description, priority로 표현돼. title은 현재 문제 상황의 이름이야. description은 현재 문제 상황의 자세한 설명이야. priority는 문제 상황의 중요도로써, BLOCKER, CRITICAL, MAJOR, MINOR, TRIVIAL 이렇게 5개로 나뉘어져. 중요도의 순서는 BLOCKER가 가장 먼저 해결해야하고, TRIVIAL로 갈 수록 덜 중요한 문제 상황이야.
            \n세번째는 *candidate*야. 이것은 너가 할당할 수 있는 개발자의 아이디 목록이야.
            \nThe second is *currentIssue*. *currentIssue* refers to the issue you need to assign to a developer now. *currentIssue* is represented by title, description, and priority. Title is the name of the current issue. Description is the detailed description of the current issue. Priority is the importance of the issue, which is divided into BLOCKER, CRITICAL, MAJOR, MINOR, and TRIVIAL. The order of importance is BLOCKER being the highest priority and TRIVIAL being the lowest.
            \nThe third is *candidate*. This is a list of developer IDs you can assign to.
            \nYou need to use the information provided to decide which developer to assign the current issue to, considering which developer has the capacity to handle the issue and the importance of the issue.
            \nYou should present your answer in two parts: answer and reason.
            \nIn the answer, output the ID of the developer you will assign.
            \nIn the reason, provide the reason why you assigned the issue to that developer.
            \nThe output format is as follows.
            {
            "answer" : "**select the id of developer in candidates list**" , "reason" : "**write your opinion. write in Korean**"
            }
            \n
            The parts enclosed in ** ** are where you need to insert your answers. You must not change any other parts. The reason must be written in Korean. Now, I will give you examples of input and output.
            
            <input>
            previous datas:
            {"assigneeId"=dev00, "title"=spring bug fix, "description"= the project creation logic has critical bugs!, "priority"=BLOCKER, "status"=ASSIGNED},
            {"assigneeId"=dev01, "title"= construct the MVC pattern , "description"= we have to apply MVC pattern to our spring server, "priority"=CRITICAL, "status"=RESOLVED},
            {"assigneeId"=dev02, "title"= small changes in server , "description"= we have to change the title of our project, "priority"=TRIVIAL, "status"=RESOLVED},
            {"assigneeId"=dev03, "title"= change Website's UI, "description"= to make our website look prettier, "priority"=MINOR, "status"=ASSIGNED},
            {"assigneeId"=dev01, "title"= Spring version change, "description"= to change out spring's version, "priority"= TRIVIAL, "status"=ASSIGNED}
            currentIssue: { "title"= refactoring DTO list in spring, "description"= we have too many overlapped DTOs, "priority"=MAJOR}
            candidates: {dev00, dev01, dev02, dev03}
                        
            <output>
            {
            "answer": "dev01",
            "reason": "이 이슈는 dev01이 해결하는 것이 좋아보입니다. 그는 이미 이전에 서버에 MVC을 적용하는 매우 중요한 이슈를 해결했던 경험이 있습니다. 즉 그에겐 spring 서버에 대한 깊은 이해가 있다고 보여지고, 이번 이슈 또한 능숙한 해결 능력을 보여줄 것으로 기대됩니다. 비록 그에게는 현재 할당된 이슈가 있지만, 그 이슈의 우선순위는 낮기 때문에 그는 이번 이슈를 해결할 격적자로 볼 수 있습니다."
            }
            """;
}
