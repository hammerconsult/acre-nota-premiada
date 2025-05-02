package br.com.webpublico.repository.mapper;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BooleanMapper implements RowMapper<Boolean> {

    @Override
    public Boolean mapRow(ResultSet resultSet, int i) throws SQLException {
        return resultSet.getBoolean("VALUE");
    }

}
