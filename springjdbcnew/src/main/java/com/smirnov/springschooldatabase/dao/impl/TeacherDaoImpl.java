package com.smirnov.springschooldatabase.dao.impl;

import com.smirnov.springschooldatabase.dao.TeacherDao;
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
public class TeacherDaoImpl extends AbstractDao<Teacher> implements TeacherDao {
    public static final String FIND_BY_ID_QUERY = "SELECT * FROM TEACHER WHERE TEACHER_ID = ?;";
    public static final String FIND_ALL_QUERY = "SELECT * FROM TEACHER;";
    public static final String FIND_ALL_QUERY_WITH_LIMIT =
            "SELECT * FROM TEACHER ORDER BY TEACHER_ID OFFSET ? ROWS FETCH NEXT ? ROWS ONLY;";
    public static final String SAVE_QUERY = "INSERT INTO TEACHER (FIRST_NAME, LAST_NAME, TITLE, DESCRIPTION, SOCIAL_MEDIA) " +
            "VALUES(?, ?, ?, ?, ?);";
    public static final String UPDATE_QUERY = "UPDATE TEACHER SET FIRST_NAME = ?, LAST_NAME = ?, TITLE = ?, " +
            "DESCRIPTION = ?, SOCIAL_MEDIA = ? WHERE TEACHER_ID = ?;";
    public static final String DELETE_BY_ID_QUERY = "DELETE FROM TEACHER WHERE TEACHER_ID = ?;";
    protected TeacherMapper teacherMapper;
    protected CourseMapper courseMapper;

    @Autowired
    public TeacherDaoImpl(JdbcTemplate jdbcTemplate, TransactionTemplate transactionTemplate,
                          TeacherMapper teacherMapper, CourseMapper courseMapper) {
        super(jdbcTemplate, transactionTemplate, FIND_BY_ID_QUERY, FIND_ALL_QUERY,
                SAVE_QUERY, UPDATE_QUERY, DELETE_BY_ID_QUERY);
        this.teacherMapper = teacherMapper;
        this.courseMapper = courseMapper;
    }

    @Override
    protected Teacher findByIntParam(String sql, Integer id) {

        return jdbcTemplate.query(FIND_BY_ID_QUERY,
                new Object[]{id}, teacherMapper)
                .stream().findAny().orElse(null);
    }

    @Override
    protected List<Teacher> findAllEntities(String sql) {

        return jdbcTemplate.query(FIND_ALL_QUERY, teacherMapper);
    }

    @Override
    protected List<Teacher> findAllEntitiesWithinScope(String sql, int leftScope, int rightScope) {

        return jdbcTemplate.query(FIND_ALL_QUERY_WITH_LIMIT,
                new Object[] { leftScope, rightScope }, teacherMapper);
    }

    @Override
    protected void insert(String sql, Teacher teacher) {

        jdbcTemplate.update(SAVE_QUERY, teacher.getFirstName(), teacher.getLastName(),
                teacher.getDegreeTitle(), teacher.getDegreeDescription(), teacher.getSocialMedia());
    }

    @Override
    protected void updateValue(String sql, Integer id, Teacher teacher) {

        jdbcTemplate.update(UPDATE_QUERY, teacher.getFirstName(),teacher.getLastName(),
                teacher.getDegreeTitle(), teacher.getDegreeDescription(), teacher.getSocialMedia(), id);
    }

    @Override
    protected void deleteValue(String sql, Integer id) {

        String sqlStatement = "DELETE FROM COURSE_TEACHER WHERE TEACHER_ID = ?;";

        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                try {
                    jdbcTemplate.update("update lesson set TEACHER_ID = null where TEACHER_ID = ?", id);
                    jdbcTemplate.update(sqlStatement, id);
                    jdbcTemplate.update(DELETE_BY_ID_QUERY, id);
                } catch (Exception exception) {
                    status.setRollbackOnly();
                }
            }
        });
    }

    @Override
    public List<Course> findTeachingCourses(Integer teacherId) {

        String sql = "SELECT * from Course where course_id = " +
                "(SELECT course_id  from course_teacher where TEACHER_ID = ?);";

        String findTeachersSql = "SELECT * from TEACHER where teacher_ID = " +
                "(SELECT TEACHER_ID  from course_teacher where course_id = ?);";

        return transactionTemplate.execute( status -> {
            List<Course> courses = jdbcTemplate.query(sql, courseMapper, teacherId);

            if (!courses.isEmpty()) {
                for (Course course : courses) {
                    List<Teacher> teachers =
                            jdbcTemplate.query(findTeachersSql, teacherMapper, course.getId());
                    course.setTeachers(teachers);
                }
            }

            return courses;
        });
    }

}
