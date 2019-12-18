package com.apress.spring.services;

import com.apress.spring.domain.Backlog;
import com.apress.spring.domain.ProjectTask;
import com.apress.spring.repository.BacklogRepository;
import com.apress.spring.repository.ProjectTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectTaskService {


    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask){
        Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);

        projectTask.setBacklog(backlog);

        Integer backLogSequence = backlog.getPTSequence();
        backLogSequence++;

        backlog.setPTSequence(backLogSequence);

        projectTask.setProjectSequenuce(projectIdentifier+"-"+backLogSequence);
        projectTask.setProjectIdentifer(projectIdentifier);

        if(projectTask.getPriority() == null){
            projectTask.setPriority(3);
        }

        if(projectTask.getStatus() == null){
            projectTask.setStatus("TODO");
        }

        return projectTaskRepository.save(projectTask);
    }

    public Iterable<ProjectTask> findBackLogById(String backlog_id){

        return projectTaskRepository.findByProjectIdentifierOrderByPriority(backlog_id);
    }
}
