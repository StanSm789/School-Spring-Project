package com.smirnov.springschooldatabase.dao.impl;

import com.smirnov.springschooldatabase.dao.StudentDao;
import com.smirnov.springschooldatabase.dao.mappers.GroupMapper;
import com.smirnov.springschooldatabase.dao.mappers.StudentMapper;
import com.smirnov.springschooldatabase.domain.Group;
import com.smirnov.springschooldatabase.domain.Student;
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
public class StudentDaoImpl extends AbstractDao<Student> implements StudentDao {
    public static final String FIND_BY_ID_QUERY = "select * from student where student_id = ?;";
    public static final String FIND_ALL_QUERY = "SELECT * FROM STUDENT;";
    public static final String FIND_ALL_QUERY_WITH_LIMIT =
            "SELECT * FROM STUDENT ORDER BY student_id OFFSET ? ROWS FETCH NEXT ? ROWS ONLY;";
    public static final String SAVE_QUERY = "INSERT INTO STUDENT (FIRST_NAME, LAST_NAME, TITLE, DESCRIPTION) " +
            "VALUES (?, ?, ?, ?);";
    public static final String UPDATE_QUERY = "UPDATE STUDENT\n" +
            "SET FIRST_NAME = ?, LAST_NAME = ?, TITLE = ?, DESCRIPTION = ?\n" +
            "WHERE STUDENT_ID = ?;";
    public static final String DELETE_BY_ID_QUERY = "DELETE FROM STUDENT WHERE STUDENT_ID = ?;";
    protected StudentMapper studentMapper;
    protected GroupMapper groupMapper;

    @Autowired
    public StudentDaoImpl(JdbcTemplate jdbcTemplate, TransactionTemplate transactionTemplate,
                          StudentMapper studentMapper, GroupMapper groupMapper) {
        super(jdbcTemplate, transactionTemplate, FIND_BY_ID_QUERY, FIND_ALL_QUERY,
                SAVE_QUERY, UPDATE_QUERY, DELETE_BY_ID_QUERY);
        this.studentMapper = studentMapper;
        this.groupMapper = groupMapper;
    }

    @Override
    protected Student findByIntParam(String sql, Integer id) {

        return jdbcTemplate.query(FIND_BY_ID_QUERY,
                new Object[]{id}, studentMapper)
                .stream().findAny().orElse(null);
    }

    @Override
    protected List<Student> findAllEntities(String sql) {

        return jdbcTemplate.query(FIND_ALL_QUERY, studentMapper);
    }

    @Override
    protected List<Student> findAllEntitiesWithinScope(String sql, int leftScope, int rightScope) {

        return jdbcTemplate.query(FIND_ALL_QUERY_WITH_LIMIT,
                new Object[] { leftScope, rightScope }, studentMapper);
    }

    @Override
    protected void insert(String sql, Student student) {

        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                try {
                    Group group =
                            jdbcTemplate.query("select * from uni_group where group_id = ?;",
                                    new Object[]{student.getGroupId()}, groupMapper)
                                    .stream().findAny().orElse(null);

                    if (group == null) {
                        jdbcTemplate.update(SAVE_QUERY, student.getFirstName(),
                                student.getLastName(), student.getStudyModeTitle(), student.getStudyModeDescription());
                    } else {
                        String queryWithGroupId = "INSERT INTO STUDENT (FIRST_NAME, LAST_NAME, GROUP_ID, TITLE, DESCRIPTION) " +
                                "VALUES (?, ?, ?, ?, ?);";

                        jdbcTemplate.update(queryWithGroupId, student.getFirstName(),
                                student.getLastName(), student.getGroupId(), student.getStudyModeTitle(), student.getStudyModeDescription());
                    }
                } catch (Exception exception) {
                    status.setRollbackOnly();
                }
            }
            });
    }

    @Override
    protected void updateValue(String sql, Integer id, Student student) {

        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                try {
                    Group group =
                            jdbcTemplate.query("select * from uni_group where group_id = ?;",
                                    new Object[]{student.getGroupId()}, groupMapper)
                                    .stream().findAny().orElse(null);

                    if (group == null) {
                        jdbcTemplate.update(UPDATE_QUERY, student.getFirstName(), student.getLastName(),
                                student.getStudyModeTitle(), student.getStudyModeDescription(), id);
                    } else {
                        String queryWithGroupId = "UPDATE STUDENT\n" +
                                "SET FIRST_NAME = ?, LAST_NAME = ?, GROUP_ID = ?, TITLE = ?, DESCRIPTION = ?\n" +
                                "WHERE STUDENT_ID = ?;";

                        jdbcTemplate.update(queryWithGroupId, student.getFirstName(), student.getLastName(), student.getGroupId(),
                                student.getStudyModeTitle(), student.getStudyModeDescription(), id);
                    }
                } catch (Exception exception) {
                    status.setRollbackOnly();
                }
            }
        });
    }

    @Override
    protected void deleteValue(String sql, Integer id) {

        jdbcTemplate.update(DELETE_BY_ID_QUERY, id);
    }

    @Override
    public void addGroupToStudent(Integer studentId, Group group) {

        String sql = "update student set group_id = ? where student_id = ?";
        jdbcTemplate.update(sql, group.getId(), studentId);
    }

}
