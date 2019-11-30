package com.apress.spring.exceptions;

public class ProjectIdExceptionResponse {
    public String getProjectId() {
        return projectId;
    }
    public ProjectIdExceptionResponse(String projectId){
        this.projectId = projectId;
    }
    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    private String projectId;


}
