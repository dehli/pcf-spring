package io.dehli.spring.repos;

import io.dehli.spring.models.Name;
import org.springframework.data.repository.CrudRepository;

public interface NameRepository extends CrudRepository<Name, Integer> {}
