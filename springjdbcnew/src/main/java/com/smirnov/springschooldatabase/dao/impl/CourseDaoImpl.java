package com.smirnov.springschooldatabase.dao.impl;

import com.smirnov.springschooldatabase.dao.CourseDao;
import com.smirnov.springschooldatabase.dao.mappers.CourseMapper;
import com.smirnov.springschooldatabase.dao.mappers.TeacherMapper;
import com.smirnov.springschooldatabase.domain.Course;
import com.smirnov.springschooldatabase.domain.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import java.util.List;

@Component
@Transactional
public class CourseDaoImpl extends AbstractDao<Course> implements CourseDao {
    public static final String FIND_BY_ID_QUERY = "SELECT * FROM COURSE WHERE COURSE_ID = ?;";
    public static final String FIND_ALL_QUERY = "SELECT * FROM COURSE;";
    public static final String FIND_ALL_QUERY_WITH_LIMIT =
            "SELECT * FROM COURSE ORDER BY COURSE_ID OFFSET ? ROWS FETCH NEXT ? ROWS ONLY;";
    public static final String SAVE_QUERY = "INSERT INTO COURSE (COURSE_NAME, COURSE_DESCRIPTION) VALUES (?, ?);";
    public static final String UPDATE_QUERY = "UPDATE COURSE\n" +
            "SET COURSE_ID = ?, COURSE_NAME = ?, COURSE_DESCRIPTION = ?\n" +
            "WHERE COURSE_ID = ?;";
    public static final String DELETE_BY_ID_QUERY = "DELETE FROM COURSE WHERE COURSE_ID = ?;";
    protected CourseMapper courseMapper;
    protected TeacherMapper teacherMapper;

    @Autowired
    public CourseDaoImpl(JdbcTemplate jdbcTemplate, TransactionTemplate transactionTemplate,
                         CourseMapper courseMapper, TeacherMapper teacherMapper) {
        super(jdbcTemplate, transactionTemplate, FIND_BY_ID_QUERY, FIND_ALL_QUERY,
                SAVE_QUERY, UPDATE_QUERY, DELETE_BY_ID_QUERY);
        this.courseMapper = courseMapper;
        this.teacherMapper = teacherMapper;
    }

    @Override
    public Course findByIntParam(String sql, Integer id) {

        return transactionTemplate.execute( status -> {
            Course course = jdbcTemplate.query(FIND_BY_ID_QUERY,
                    new Object[]{id}, courseMapper)
                    .stream().findAny().orElse(null);

            if (course != null) {
                course.setTeachers(getTeachersWithCourse(id));
            }
            return course;
        });
    }

    @Override
    protected List<Course> findAllEntities(String sql) {

        return transactionTemplate.execute( status -> {
            List<Course> courses = jdbcTemplate.query(FIND_ALL_QUERY, courseMapper);
            for(Course course : courses){
                course.setTeachers(getTeachersWithCourse(course.getId()));
            }

            return courses;
        });
    }

    @Override
    protected List<Course> findAllEntitiesWithinScope(String sql, int leftScope, int rightScope) {

        return transactionTemplate.execute( status -> {
            List<Course> courses = jdbcTemplate.query(FIND_ALL_QUERY_WITH_LIMIT,
                    new Object[] { leftScope, rightScope }, courseMapper);
            for(Course course : courses){
                course.setTeachers(getTeachersWithCourse(course.getId()));
            }

            return courses;
        });
    }

    @Override
    protected void insert(String sql, Course course) {

            jdbcTemplate.update(SAVE_QUERY, course.getName(), course.getDescription());
    }

    @Override
    protected void updateValue(String sql, Integer id, Course course) {

        jdbcTemplate.update(UPDATE_QUERY, course.getId(), course.getName(), course.getDescription(), id);
    }

    @Override
    protected void deleteValue(String sql, Integer id) {

        String sqlStatementOne = "DELETE FROM COURSE_GROUP WHERE COURSE_ID = ?;";
        String sqlStatementTwo = "DELETE FROM COURSE_TEACHER WHERE COURSE_ID = ?;";

        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                try {
                    jdbcTemplate.update("update lesson set COURSE_ID = null where COURSE_ID = ?", id);
                    jdbcTemplate.update(sqlStatementOne, id);
                    jdbcTemplate.update(sqlStatementTwo, id);
                    jdbcTemplate.update(DELETE_BY_ID_QUERY, id);
                } catch (Exception exception) {
                    status.setRollbackOnly();
                }
            }
        });
    }

    @Override
    public void addTeacherToCourse(int courseId, Teacher teacher) {

        String sqlStatement = "INSERT INTO COURSE_TEACHER (COURSE_ID, TEACHER_ID) VALUES (?, ?);";
        jdbcTemplate.update(sqlStatement, courseId, teacher.getId());
    }

    @Override
    public void removeTeacherFromCourse(int courseId, Teacher teacher) {

        String sqlStatement = "delete from COURSE_TEACHER where course_id = ? and teacher_id = ?;";
        jdbcTemplate.update(sqlStatement, courseId, teacher.getId());
    }

    @Override
    public void removeTeachersFromCourse(int courseId) {

        String sqlStatement = "delete from COURSE_TEACHER where course_id = ?;";
        jdbcTemplate.update(sqlStatement, courseId);
    }

    private List<Teacher> getTeachersWithCourse(Integer courseId) {

        String sql = "SELECT * from TEACHER where teacher_ID = " +
                "(SELECT TEACHER_ID  from course_teacher where course_id = ?);";

        return jdbcTemplate.query(sql, teacherMapper, courseId);
    }

}
