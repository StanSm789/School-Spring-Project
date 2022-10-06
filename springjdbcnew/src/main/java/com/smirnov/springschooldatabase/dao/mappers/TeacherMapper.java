package com.smirnov.springschooldatabase.dao.mappers;

import com.smirnov.springschooldatabase.domain.Teacher;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TeacherMapper implements RowMapper<Teacher> {

    @Override
    public Teacher mapRow(ResultSet resultSet, int i) throws SQLException {

        return Teacher.builder().withId(resultSet.getInt("TEACHER_ID"))
                .withFirstName(resultSet.getString("FIRST_NAME"))
                .withLastName(resultSet.getString("LAST_NAME"))
                .withDegreeTitle(resultSet.getString("TITLE"))
                .withDegreeDescription(resultSet.getString("DESCRIPTION"))
                .withSocialMedia(resultSet.getString("SOCIAL_MEDIA"))
                .build();
    }

}
