package com.smirnov.springschooldatabase.dao.impl;

import com.smirnov.springschooldatabase.dao.TimetableDao;
import com.smirnov.springschooldatabase.dao.mappers.LessonMapper;
import com.smirnov.springschooldatabase.dao.mappers.TimetableMapper;
import com.smirnov.springschooldatabase.domain.Lesson;
import com.smirnov.springschooldatabase.domain.Timetable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import java.util.Calendar;
import java.util.List;

@Component
@Transactional
public class TimetableDaoImpl extends AbstractDao<Timetable> implements TimetableDao {
    public static final String FIND_BY_ID_QUERY = "SELECT * FROM TIMETABLE WHERE TIMETABLE_ID = ?;";
    public static final String FIND_ALL_QUERY = "SELECT * FROM TIMETABLE;";
    public static final String FIND_ALL_QUERY_WITH_LIMIT =
            "SELECT * FROM TIMETABLE ORDER BY TIMETABLE_ID OFFSET ? ROWS FETCH NEXT ? ROWS ONLY;";
    public static final String SAVE_QUERY = "INSERT INTO TIMETABLE (DESCRIPTION) VALUES (?);";
    public static final String UPDATE_QUERY = "UPDATE TIMETABLE SET DESCRIPTION = ? WHERE TIMETABLE_ID = ?;";
    public static final String DELETE_BY_ID_QUERY = "DELETE FROM TIMETABLE WHERE TIMETABLE_ID = ?;";
    protected TimetableMapper timetableMapper;
    protected LessonMapper lessonMapper;

    @Autowired
    public TimetableDaoImpl(JdbcTemplate jdbcTemplate, TransactionTemplate transactionTemplate,
                            TimetableMapper timetableMapper, LessonMapper lessonMapper) {
        super(jdbcTemplate, transactionTemplate, FIND_BY_ID_QUERY, FIND_ALL_QUERY,
                SAVE_QUERY, UPDATE_QUERY, DELETE_BY_ID_QUERY);
        this.timetableMapper = timetableMapper;
        this.lessonMapper = lessonMapper;
    }

    @Override
    protected Timetable findByIntParam(String sql, Integer id) {

        return transactionTemplate.execute( status -> {
            Timetable timetable = jdbcTemplate.query(FIND_BY_ID_QUERY,
                    new Object[]{id}, timetableMapper)
                    .stream().findAny().orElse(null);

            if (timetable != null) {
                timetable.setTimetable(getLessons(timetable.getId()));
            }

            return timetable;
        });
    }

    @Override
    protected List<Timetable> findAllEntities(String sql) {

        return transactionTemplate.execute( status -> {
        List<Timetable> listOfTimetables = jdbcTemplate.query(FIND_ALL_QUERY, timetableMapper);

        for(Timetable timetable : listOfTimetables) {
            timetable.setTimetable(getLessons(timetable.getId()));
        }

        return listOfTimetables;
        });
    }

    @Override
    protected List<Timetable> findAllEntitiesWithinScope(String sql, int leftScope, int rightScope) {

        return transactionTemplate.execute( status -> {
        List<Timetable> listOfTimetables = jdbcTemplate.query(FIND_ALL_QUERY_WITH_LIMIT,
                new Object[] { leftScope, rightScope }, timetableMapper);

        for(Timetable timetable : listOfTimetables) {
            timetable.setTimetable(getLessons(timetable.getId()));
        }

        return listOfTimetables;
        });
    }

    @Override
    protected void insert(String sql, Timetable timetable) {

        jdbcTemplate.update(SAVE_QUERY, timetable.getDescription());
    }

    @Override
    protected void updateValue(String sql, Integer id, Timetable timetable) {

        jdbcTemplate.update(UPDATE_QUERY, timetable.getDescription(), id);
    }

    @Override
    protected void deleteValue(String sql, Integer id) {

        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                try {
                    jdbcTemplate.update("update lesson set TIMETABLE_ID = null where TIMETABLE_ID = ?", id);
                    jdbcTemplate.update(DELETE_BY_ID_QUERY, id);
                } catch (Exception exception) {
                    status.setRollbackOnly();
                }
            }
        });
    }

    private List<Lesson> getLessons(Integer timetableId) {

        String sql = "select * from LESSON where TIMETABLE_ID = ?;";

        return transactionTemplate.execute( status -> {
            List<Lesson> lessons = jdbcTemplate.query(sql, lessonMapper, timetableId);
            for (Lesson lesson : lessons) {
                lesson.setCalendar(getCalendar(lesson.getId()));
            }

        return lessons;
        });
    }

    private Calendar getCalendar(int id) {
        String sql = "select calendar from lesson where lesson_id = ?;";

        return jdbcTemplate.queryForObject(sql, new Object[] { id }, Calendar.class);
    }

}
