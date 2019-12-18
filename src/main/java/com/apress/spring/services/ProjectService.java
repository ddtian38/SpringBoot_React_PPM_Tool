package com.apress.spring.services;

import com.apress.spring.domain.Backlog;
import com.apress.spring.domain.Project;
import com.apress.spring.exceptions.ProjectIdException;
import com.apress.spring.repository.BacklogRepository;
import com.apress.spring.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private BacklogRepository backlogRepository;

    public Project saveOrUpdateProject(Project project){

       try{

           project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());

           if(project.getId() ==  null){
               Backlog backlog = new Backlog();
               project.setBacklog(backlog);
               backlog.setProject(project);
               backlog.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
           }else{
               project.setBacklog(backlogRepository.findByProjectIdentifier(project.getProjectIdentifier().toUpperCase()));
           }


           return projectRepository.save(project);

       }catch (Exception e){
            throw new ProjectIdException("Project Id: " + project.getProjectIdentifier().toUpperCase() + " is taken.");

       }
    }

    public Project findProjectById(String projectId){
        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());

        if(project == null) throw new ProjectIdException("Project Id: " + projectId.toUpperCase() + " does not exist.");

        return projectRepository.findByProjectIdentifier(projectId.toUpperCase());
    }

    public Iterable<Project> findAllProjects(){
        return projectRepository.findAll();
    }

    public void deleteProjectById(String projectId){
        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());

        if(project == null) throw new ProjectIdException("Cannot delete project with id: " + projectId.toUpperCase() + ". This project does not exist.");

         projectRepository.delete(project);
    }

//    public Project updateProjectById(String projectId, Project newProject){
//        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());
//        if(project == null) throw new ProjectIdException("Cannot update project with id: " + projectId.toUpperCase() + ". This project does not exist.");
//
//        project.setProjectName(newProject.getProjectName());
//        project.setDescription(newProject.getDescription());
//        project.setStartDate(newProject.getStartDate());
//        project.setEndDate(newProject.getEndDate());
//
//        return projectRepository.save(project);
//
//    }
}
