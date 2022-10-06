package com.smirnov.springschooldatabase.dao.mappers;

import com.smirnov.springschooldatabase.domain.Course;
import com.smirnov.springschooldatabase.domain.Group;
import com.smirnov.springschooldatabase.domain.Lesson;
import com.smirnov.springschooldatabase.domain.Room;
import com.smirnov.springschooldatabase.domain.Teacher;
import com.smirnov.springschooldatabase.domain.Timetable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LessonMapper implements RowMapper<Lesson>{

    public final JdbcTemplate jdbcTemplate;

    public LessonMapper(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Lesson mapRow(ResultSet resultSet, int i) throws SQLException {

        return Lesson.builder().withId(resultSet.getInt("LESSON_ID"))
                .withGroup(Group.builder().withId(resultSet.getInt("GROUP_ID")).build())
                .withRoom(Room.builder().withId(resultSet.getInt("TEACHER_ID")).build())
                .withTeacher(Teacher.builder().withId(resultSet.getInt("TEACHER_ID")).build())
                .withCourse(Course.builder().withId(resultSet.getInt("COURSE_ID")).build())
                .withTimetable(Timetable.builder().withId(resultSet.getInt("TIMETABLE_ID")).build())
                .build();
    }

}
