package com.smirnov.springschooldatabase.dao.mappers;

import com.smirnov.springschooldatabase.domain.Timetable;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TimetableMapper implements RowMapper<Timetable> {

    @Override
    public Timetable mapRow(ResultSet resultSet, int i) throws SQLException {

        return Timetable.builder().withId(resultSet.getInt("TIMETABLE_ID"))
                .withDescription(resultSet.getString("DESCRIPTION")).build();
    }

}
