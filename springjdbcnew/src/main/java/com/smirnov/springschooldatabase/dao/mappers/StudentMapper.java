package com.smirnov.springschooldatabase.dao.mappers;

import com.smirnov.springschooldatabase.domain.Student;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentMapper implements RowMapper<Student> {

    @Override
    public Student mapRow(ResultSet resultSet, int i) throws SQLException {

        return Student.builder().withId(resultSet.getInt("STUDENT_ID"))
                .withFirstName(resultSet.getString("FIRST_NAME"))
                .withLastName(resultSet.getString("LAST_NAME"))
                .withGroupId(resultSet.getInt("GROUP_ID"))
                .withStudyModeTitle(resultSet.getString("TITLE"))
                .withStudyModeDescription(resultSet.getString("DESCRIPTION"))
                .build();
    }

}
