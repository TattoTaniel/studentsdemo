package com.noirix.repository.impl;

import com.noirix.domain.User;
import com.noirix.repository.UserColumn;
import com.noirix.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
@Primary
@RequiredArgsConstructor
public class JdbcTemplateUserRepository implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

//    @Autowired
//    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }
//
//    @Autowired
//    public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
//        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
//    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query("select * from users order by id desc", this::getUserRowMapper);
    }

    private User getUserRowMapper(ResultSet rs, int i) throws SQLException {
        User user = new User();
        user.setId(rs.getLong(UserColumn.ID));
        user.setName(rs.getString(UserColumn.NAME));
        user.setSurname(rs.getString(UserColumn.SURNAME));
        user.setBirthDate(rs.getDate(UserColumn.BIRTH_DATE));
        user.setLogin(rs.getString(UserColumn.LOGIN));
        user.setWeight(rs.getFloat(UserColumn.WEIGHT));
        return user;
    }

    @Override
    public User findOne(Long id) {
        //final String findOneWithWildcard = "select * from users where id = ?";
        //return jdbcTemplate.queryForObject(findOneWithWildcard, new Object[]{id}, this::getUserRowMapper);
        //return jdbcTemplate.queryForObject(findOneWithWildcard, this::getUserRowMapper, id);

        final String findOneWithNameParam = "select * from users where id = :blablabla and id = :qweqwe";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("blablabla", id);
        params.addValue("qweqwe", id);

        return namedParameterJdbcTemplate.queryForObject(findOneWithNameParam, params, this::getUserRowMapper);
    }

    @Override
    public User save(User entity) {
        final String createQuery = "insert into users (name, surname, birth_date, login, weight) " +
                "values (:name, :surname, :birthDate, :login, :weight);";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        MapSqlParameterSource params = generateUserParamsMap(entity);

        namedParameterJdbcTemplate.update(createQuery, params, keyHolder, new String[]{"id"});

        long createdUserId = Objects.requireNonNull(keyHolder.getKey()).longValue();

        return findOne(createdUserId);
    }

    @Override
    public void addOne(User entity) {
        final String createQuery = "insert into users (name, surname, birth_date, login, weight) " +
                "values (:name, :surname, :birthDate, :login, :weight);";

        MapSqlParameterSource params = generateUserParamsMap(entity);
        namedParameterJdbcTemplate.update(createQuery, params);
    }

    @Override
    public void save(List<User> entities) {
        for (User entity : entities) {
            addOne(entity);
        }
    }

    private MapSqlParameterSource generateUserParamsMap(User entity) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", entity.getName());
        params.addValue("surname", entity.getSurname());
        params.addValue("birthDate", entity.getBirthDate());
        params.addValue("login", entity.getLogin());
        params.addValue("weight", entity.getWeight());

        return params;
    }

    @Override
    public User update(User entity) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    //Specification
    //Criteria API
    //Search Criteria object
    //like '%query%' and like '%query%' and like '%query%'
    //ElasticSearch
    //PostgresFTS
    @Override
    public List<User> findUsersByQuery(Integer limit, String query) {
        final String searchQuery = "select * from users where name like :query limit :limit";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("query", "%" + query + "%");
        params.addValue("limit", limit);

        return namedParameterJdbcTemplate.query(searchQuery, params, this::getUserRowMapper);
    }

    @Override
    public Double getUserExpensiveCarPrice(Integer userId) {
        return null;
    }

    @Override
    public void batchInsert(List<User> users) {
        final String createQuery = "insert into users (name, surname, birth_date, login, weight) " +
                "values (:name, :surname, :birthDate, :login, :weight);";

        List<MapSqlParameterSource> batchParams = new ArrayList<>();

        for (User user : users) {
            batchParams.add(generateUserParamsMap(user));
        }

        namedParameterJdbcTemplate.batchUpdate(createQuery, batchParams.toArray(new MapSqlParameterSource[0]));

    }
}