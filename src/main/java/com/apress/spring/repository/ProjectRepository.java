package com.apress.spring.repository;

import com.apress.spring.domain.Project;
import com.apress.spring.exceptions.ProjectIdException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Long> {

  public Project findByProjectIdentifier(String projectId);

    @Override
    Iterable<Project> findAll();


}
