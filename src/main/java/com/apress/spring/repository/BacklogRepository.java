package com.apress.spring.repository;


import com.apress.spring.domain.Backlog;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BacklogRepository extends CrudRepository<Backlog, Long> {

     Backlog findByProjectIdentifier(String identifer);
}
