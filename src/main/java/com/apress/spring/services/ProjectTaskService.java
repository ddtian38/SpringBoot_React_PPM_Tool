package com.apress.spring.services;

import com.apress.spring.domain.Backlog;
import com.apress.spring.domain.ProjectTask;
import com.apress.spring.exceptions.ProjectNotFoundException;
import com.apress.spring.repository.BacklogRepository;
import com.apress.spring.repository.ProjectRepository;
import com.apress.spring.repository.ProjectTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectTaskService {


    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    @Autowired
    private ProjectRepository projectRepository;

    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask){

        try {
            Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);

            projectTask.setBacklog(backlog);

            Integer backLogSequence = backlog.getPTSequence();
            backLogSequence++;

            backlog.setPTSequence(backLogSequence);

            projectTask.setProjectSequence(projectIdentifier+"-"+backLogSequence);
            projectTask.setProjectIdentifier(projectIdentifier);

            if(projectTask.getPriority() == null){
                projectTask.setPriority(3);
            }

            if(projectTask.getStatus() == null){
                projectTask.setStatus("TODO");
            }

            return projectTaskRepository.save(projectTask);
        }
        catch(Exception e){
            throw new ProjectNotFoundException("Project not found.");
        }

    }

    public Iterable<ProjectTask> findBackLogById(String backlog_id){

        if(projectRepository.findByProjectIdentifier(backlog_id) == null){

            throw new ProjectNotFoundException("Project with \"" + backlog_id + "\" not found.");
        }


        return projectTaskRepository.findByProjectIdentifierOrderByPriority(backlog_id);
    }

    public ProjectTask findPTByProjectSequence(String backlog_id, String pt_id){

        Backlog backlog = backlogRepository.findByProjectIdentifier(backlog_id);

        if(backlog == null){
            throw new ProjectNotFoundException("Project with \"" + backlog_id + "\" not found.");
        }

        ProjectTask projectTask = projectTaskRepository.findByProjectSequence(pt_id);

        if(projectTask == null) {throw new ProjectNotFoundException("Project Task \"" + pt_id + "\" is not found");}

        if(!projectTask.getProjectIdentifier().equals(backlog_id)) {
            throw new ProjectNotFoundException("Project Task \"" + pt_id + "\" does not exist in project: \"" + backlog_id + "\"");
        }

        return projectTask;
    }

    public ProjectTask updateByProjectSequence(ProjectTask updatedProjectTask){

        return  projectTaskRepository.save(updatedProjectTask);
    }

    public void deletePTByProjectSequence(String backlog_id, String pt_id){
        ProjectTask projectTask = findPTByProjectSequence(backlog_id, pt_id);

//        Backlog backlog = projectTask.getBacklog();
//
//        List<ProjectTask> pts = backlog.getProjectTaskList();
//
//        pts.remove(projectTask);
//        backlogRepository.save(backlog);

        projectTaskRepository.delete(projectTask);

    }

}
