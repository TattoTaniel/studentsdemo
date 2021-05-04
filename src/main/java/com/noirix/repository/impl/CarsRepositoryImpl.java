package com.noirix.repository.impl;

import com.noirix.domain.Cars;
import com.noirix.repository.CarsRepository;

import java.util.List;

public class CarsRepositoryImpl implements CarsRepository {

    public static final String POSTRGES_DRIVER_NAME = "org.postgresql.Driver";
    public static final String DATABASE_URL = "jdbc:postgresql://localhost:";
    public static final int DATABASE_PORT = 5432;
    public static final String DATABASE_NAME = "/student_demo";
    public static final String DATABASE_LOGIN = "postgres";
    public static final String DATABASE_PASSWORD = "postgres";

    @Override
    public List<Cars> findUsersByQuery(String query) {
        return null;
    }

    @Override
    public List<Cars> getPage(int limit, int offset) {
        return null;
    }

    @Override
    public List<Cars> findAll() {
        return null;
    }

    @Override
    public Cars findOne(Long id) {
        return null;
    }

    @Override
    public Cars save(Cars entity) {
        return null;
    }

    @Override
    public Cars update(Cars entity) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
