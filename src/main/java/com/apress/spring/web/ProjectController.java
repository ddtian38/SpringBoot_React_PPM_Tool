package com.apress.spring.web;

import com.apress.spring.domain.Project;
import com.apress.spring.services.MapValidationError;
import com.apress.spring.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/project")
@CrossOrigin
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private MapValidationError mapValidationErrorService;

    @PostMapping("")
    public ResponseEntity<?> createNewProject(@Valid @RequestBody Project project, BindingResult result){
        System.out.println(project);
       ResponseEntity<?>errorMap = mapValidationErrorService.mapValidationService(result);
       if(errorMap != null) {
//           System.out.println("error");
           return errorMap;
       }

        Project returnProject = projectService.saveOrUpdateProject(project);
        return new ResponseEntity<Project>(returnProject, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<?> findAllProjects(){

        return new ResponseEntity<Iterable>( projectService.findAllProjects(), HttpStatus.OK);
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<?> getProjectById(@PathVariable String projectId){

        Project project = projectService.findProjectById(projectId);

        return new ResponseEntity<Project>(project, HttpStatus.OK);
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<?> deleteProjectById(@PathVariable String projectId){
        projectService.deleteProjectById(projectId);
        return new ResponseEntity<String>("Project " + projectId.toUpperCase()  + " has been successfully deleted.", HttpStatus.OK);
    }

//    @PatchMapping("/{projectId}")
//    public ResponseEntity<?> updateProjectById(@Valid @RequestBody Project newProject, @PathVariable String projectId, BindingResult result){
//        ResponseEntity<?>errorMap = mapValidationErrorService.mapValidationService(result);
//        if(errorMap != null) return errorMap;
//
//        projectService.updateProjectById(projectId, newProject);
//
//        return new ResponseEntity<Project>(projectService.updateProjectById(projectId, newProject), HttpStatus.OK);
//
//    }
}
