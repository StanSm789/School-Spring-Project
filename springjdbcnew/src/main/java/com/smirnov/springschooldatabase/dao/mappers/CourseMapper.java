package com.smirnov.springschooldatabase.dao.mappers;

import com.smirnov.springschooldatabase.domain.Course;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CourseMapper implements RowMapper<Course> {

    @Override
    public Course mapRow(ResultSet resultSet, int i) throws SQLException {

        return Course.builder().withId(resultSet.getInt("COURSE_ID"))
                .withName(resultSet.getString("COURSE_NAME"))
                .withDescription(resultSet.getString("COURSE_DESCRIPTION"))
                .build();
    }

}
