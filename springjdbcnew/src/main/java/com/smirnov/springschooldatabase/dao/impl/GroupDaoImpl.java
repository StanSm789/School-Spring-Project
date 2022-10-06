package com.smirnov.springschooldatabase.dao.impl;

import com.smirnov.springschooldatabase.dao.GroupDao;
import com.smirnov.springschooldatabase.dao.mappers.CourseMapper;
import com.smirnov.springschooldatabase.dao.mappers.GroupMapper;
import com.smirnov.springschooldatabase.dao.mappers.StudentMapper;
import com.smirnov.springschooldatabase.dao.mappers.TeacherMapper;
import com.smirnov.springschooldatabase.domain.Course;
import com.smirnov.springschooldatabase.domain.Group;
import com.smirnov.springschooldatabase.domain.Student;
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
public class GroupDaoImpl extends AbstractDao<Group> implements GroupDao {

    public static final String FIND_BY_ID_QUERY = "SELECT * FROM UNI_GROUP WHERE GROUP_ID = ?;";
    public static final String FIND_ALL_QUERY = "SELECT * FROM UNI_GROUP;";
    public static final String FIND_ALL_QUERY_WITH_LIMIT =
            "SELECT * FROM UNI_GROUP ORDER BY GROUP_ID OFFSET ? ROWS FETCH NEXT ? ROWS ONLY;";
    public static final String SAVE_QUERY = "INSERT INTO UNI_GROUP (GROUP_NAME) VALUES (?);";
    public static final String UPDATE_QUERY = "UPDATE UNI_GROUP\n" +
            "SET GROUP_ID = ?, GROUP_NAME = ?\n" +
            "WHERE GROUP_ID = ?;";
    public static final String DELETE_BY_ID_QUERY = "DELETE FROM UNI_GROUP WHERE GROUP_ID = ?;";
    protected CourseMapper courseMapper;
    protected GroupMapper groupMapper;
    protected StudentMapper studentMapper;
    protected TeacherMapper teacherMapper;

    @Autowired
    public GroupDaoImpl(JdbcTemplate jdbcTemplate, TransactionTemplate transactionTemplate,
                        CourseMapper courseMapper, GroupMapper groupMapper,
                        StudentMapper studentMapper, TeacherMapper teacherMapper) {
        super(jdbcTemplate,transactionTemplate, FIND_BY_ID_QUERY, FIND_ALL_QUERY,
                SAVE_QUERY, UPDATE_QUERY, DELETE_BY_ID_QUERY);
        this.courseMapper = courseMapper;
        this.groupMapper = groupMapper;
        this.studentMapper = studentMapper;
        this.teacherMapper = teacherMapper;
    }

    @Override
    public Group findByIntParam(String sql, Integer id) {

        return transactionTemplate.execute( status -> {
            Group group = jdbcTemplate.query(FIND_BY_ID_QUERY,
                    new Object[]{id}, groupMapper)
                    .stream().findAny().orElse(null);

            if (group != null) {
                group.setCourses(getCourses(id));
                group.setStudents(getStudents(id));
            }

            return group;
        });
    }

    @Override
    protected List<Group> findAllEntities(String sql) {

        return transactionTemplate.execute( status -> {
            List<Group> groups = jdbcTemplate.query(FIND_ALL_QUERY, groupMapper);
            for(Group group : groups){
                group.setCourses(getCourses(group.getId()));
                group.setStudents(getStudents(group.getId()));
            }

            return groups;
        });
    }

    @Override
    protected List<Group> findAllEntitiesWithinScope(String sql, int leftScope, int rightScope) {

        return transactionTemplate.execute( status -> {
            List<Group> groups = jdbcTemplate.query(FIND_ALL_QUERY_WITH_LIMIT,
                    new Object[] { leftScope, rightScope }, groupMapper);
            for(Group group : groups){
                group.setCourses(getCourses(group.getId()));
                group.setStudents(getStudents(group.getId()));
            }

            return groups;
        });
    }

    @Override
    protected void insert(String sql, Group group) {

        jdbcTemplate.update(SAVE_QUERY, group.getName());
    }

    @Override
    protected void updateValue(String sql, Integer id, Group group) {

        jdbcTemplate.update(UPDATE_QUERY, group.getId(), group.getName(), id);
    }

    @Override
    protected void deleteValue(String sql, Integer id) {

        String sqlStatement = "DELETE FROM COURSE_GROUP WHERE GROUP_ID = ?;";

        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                try {
                    jdbcTemplate.update("update student set group_id = null where group_id = ?", id);
                    jdbcTemplate.update("update lesson set group_id = null where group_id = ?", id);
                    jdbcTemplate.update(sqlStatement, id);
                    jdbcTemplate.update(DELETE_BY_ID_QUERY, id);
                } catch (Exception exception) {
                    status.setRollbackOnly();
                }
            }
        });
    }

    @Override
    public void addCourseToGroup(int groupId, Course course) {

        String sqlStatement = "INSERT INTO COURSE_GROUP (COURSE_ID, GROUP_ID) VALUES (?, ?);";
        jdbcTemplate.update(sqlStatement, course.getId(), groupId);
    }

    private List<Student> getStudents(Integer groupId) {

        String sql = "select * from student where group_id = ?;";

        return jdbcTemplate.query(sql, studentMapper, groupId);
    }

    private List<Course> getCourses(Integer groupId) {

        String courseSql = "SELECT * FROM COURSE WHERE COURSE_ID in (select course_id from" +
                " course_group where group_id = ?);";

        return transactionTemplate.execute( status -> {
            List<Course> courses = jdbcTemplate.query(courseSql,
                    new Object[] {groupId}, courseMapper);

            return addTeachersToCourses(courses);
        });
    }

    private List<Course> addTeachersToCourses(List<Course> courses) {

        String teachersSql = "SELECT * from TEACHER where teacher_ID = " +
                "(SELECT TEACHER_ID  from course_teacher where course_id = ?);";

        return transactionTemplate.execute( status -> {
            for(Course course : courses) {
                List<Teacher> teachers =
                        jdbcTemplate.query(teachersSql, teacherMapper, course.getId());

                course.setTeachers(teachers);
            }

            return courses;
        });
    }

}
