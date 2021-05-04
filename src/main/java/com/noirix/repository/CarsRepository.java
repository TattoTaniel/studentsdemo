package com.noirix.repository;

import com.noirix.domain.Cars;

import java.util.List;

public interface CarsRepository extends CrudOperations<Long, Cars> {

    List<Cars> findUsersByQuery(String query);

    List<Cars> getPage(int limit, int offset);
}
