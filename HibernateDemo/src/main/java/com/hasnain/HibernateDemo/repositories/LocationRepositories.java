package com.hasnain.HibernateDemo.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.hasnain.HibernateDemo.models.Location;
@Repository
public interface LocationRepositories  extends CrudRepository<Location, Integer>{

}
