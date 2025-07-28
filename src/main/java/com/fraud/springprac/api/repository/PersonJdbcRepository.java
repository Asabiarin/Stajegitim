package com.fraud.springprac.api.repository;

import com.fraud.springprac.api.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Repository
public class PersonJdbcRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Page<Person> findByAttributeKeyValue(String key, String input, Pageable pageable) {
        // Parse operator and value
        Matcher matcher = Pattern.compile("^(>=|<=|>|<|=)?(.*)$").matcher(input);
        matcher.find();

        String operator = matcher.group(1) != null ? matcher.group(1) : "=";
        String value = matcher.group(2);
        boolean isNumeric = value.matches("-?\\d+(\\.\\d+)?");

        // Build query
        String whereClause = buildWhereClause(key, operator, isNumeric);
        String sql = String.format("""
            SELECT * FROM egitim.person_table 
            WHERE %s
            ORDER BY id
            LIMIT %d OFFSET %d
            """, whereClause, pageable.getPageSize(), pageable.getOffset());

        // Execute query
        List<Person> persons = jdbcTemplate.query(
                String.format("SELECT * FROM egitim.person_table WHERE %s LIMIT ? OFFSET ?", whereClause),
                ps -> {
                    ps.setString(1, key);
                    ps.setString(2, value);
                    ps.setInt(3, pageable.getPageSize());
                    ps.setInt(4, (int) pageable.getOffset());
                },
                (rs, rowNum) -> {
                    Person person = new Person();
                    person.setId(rs.getInt("id"));
                    // set other properties
                    return person;
                });

        // Count query
        Long total = jdbcTemplate.queryForObject(
                String.format("SELECT COUNT(*) FROM egitim.person_table WHERE %s", whereClause),
                new Object[]{key, value},
                Long.class);

        return new PageImpl<>(persons, pageable, total);
    }
    private String buildWhereClause(String key, String operator, boolean isNumeric) {
        if (isNumeric) {
            return String.format("CAST(attributes->>? AS DOUBLE PRECISION) %s CAST(? AS DOUBLE PRECISION)", operator);
        } else {
            if (!"=".equals(operator)) {
                throw new IllegalArgumentException("String comparison only supports equals (=) operator");
            }
            return "attributes->>? = ?";
        }
    }
}