package com.smirnov.springschooldatabase.dao.mappers;

import com.smirnov.springschooldatabase.domain.Group;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GroupMapper implements RowMapper<Group> {

    @Override
    public Group mapRow(ResultSet resultSet, int i) throws SQLException {

        return Group.builder().withId(resultSet.getInt("GROUP_ID"))
                .withName(resultSet.getString("GROUP_NAME"))
                .build();
    }

}
