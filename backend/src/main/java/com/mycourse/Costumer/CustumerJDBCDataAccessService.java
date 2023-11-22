package com.mycourse.Costumer;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("JDBC")
public class CustumerJDBCDataAccessService implements CustumerDAO {
    private final JdbcTemplate jdbcTemplate;
    private  final CustumerRowMapper custumerRowMapper;

    public CustumerJDBCDataAccessService(JdbcTemplate jdbcTemplate, CustumerRowMapper custumerRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.custumerRowMapper = custumerRowMapper;
    }

    @Override
    public List<Custumer> SelectAllCustumer() {
        var sql = """
                SELECT * FROM customer;
                """;
        return jdbcTemplate.query(sql,custumerRowMapper) ;
    }

    @Override
    public Optional<Custumer> SelectCustumerById(int id) {
        var sql = """
                SELECT * FROM customer WHERE customer.id = ?
                """ ;

        return jdbcTemplate.query(sql,custumerRowMapper,id).stream().findFirst();
    }

    @Override
    public void insertCustumer(Custumer custumer) {
        var sql = """
                INSERT INTO customer (name,email,age) VALUES (?,?,?)
                """;
        jdbcTemplate.update(
                sql,custumer.getName(),custumer.getEmail(),custumer.getAge()
        );
    }

    @Override
    public boolean existWithSameEmail(String email) {
        var sql = """
                SELECT COUNT(customer.id) FROM customer WHERE customer.email = ?;
                """;
        int result = jdbcTemplate.queryForObject(sql,Integer.class,email);
        if(result == 0){
            return false;
        }

        return true;
    }

    @Override
    public boolean existWithSameId(int id) {
        var sql = """
                SELECT COUNT(customer.id) FROM customer WHERE customer.id = ?;
                """;
        int result = jdbcTemplate.queryForObject(sql,Integer.class,id);
        if(result == 0){
            return false;
        }

        return true;
    }

    @Override
    public void deleteWithId(int id) {
        var sql = """
                DELETE FROM customer WHERE customer.id = ?
                """;
        jdbcTemplate.update(sql,id);
    }

    @Override
    public void UpdateInformation(Custumer custumer) {
            var sql = """
                    UPDATE customer
                    SET name=?,email=?,age=?
                    WHERE customer.id =? 
                    """;
            jdbcTemplate.update(sql,custumer.getName(),custumer.getEmail(),custumer.getAge(),custumer.getId());
    }
}
