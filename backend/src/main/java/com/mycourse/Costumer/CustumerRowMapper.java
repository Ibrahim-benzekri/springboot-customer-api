package com.mycourse.Costumer;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
@Component

public class CustumerRowMapper implements RowMapper<Custumer> {

    @Override
    public Custumer mapRow(ResultSet rs, int rowNum) throws SQLException {
        Custumer custumer = new Custumer(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getInt("age")

        );
        return custumer;
    }
}
