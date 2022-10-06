package com.smirnov.springschooldatabase.dao.impl;

import com.smirnov.springschooldatabase.dao.LessonDao;
import com.smirnov.springschooldatabase.dao.mappers.CourseMapper;
import com.smirnov.springschooldatabase.dao.mappers.GroupMapper;
import com.smirnov.springschooldatabase.dao.mappers.LessonMapper;
import com.smirnov.springschooldatabase.dao.mappers.RoomMapper;
import com.smirnov.springschooldatabase.dao.mappers.TeacherMapper;
import com.smirnov.springschooldatabase.dao.mappers.TimetableMapper;
import com.smirnov.springschooldatabase.domain.Lesson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;
import java.util.Calendar;
import java.util.List;

@Component
@Transactional
public class LessonDaoImpl extends AbstractDao<Lesson> implements LessonDao {
    public static final String FIND_BY_ID_QUERY = "SELECT * FROM LESSON WHERE LESSON_ID = ?;";
    public static final String FIND_ALL_QUERY = "SELECT * FROM LESSON;";
    public static final String FIND_ALL_QUERY_WITH_LIMIT =
            "SELECT * FROM LESSON ORDER BY LESSON_ID OFFSET ? ROWS FETCH NEXT ? ROWS ONLY;";
    public static final String SAVE_QUERY = "INSERT INTO LESSON (GROUP_ID, ROOM_ID, " +
            "TEACHER_ID, COURSE_ID, TIMETABLE_ID, CALENDAR) " +
            "VALUES(?, ?, ?, ?, ?, ?);";
    public static final String UPDATE_QUERY = "UPDATE LESSON SET GROUP_ID = ?, ROOM_ID = ?, TEACHER_ID = ?, " +
            "COURSE_ID = ?,  TIMETABLE_ID = ?, CALENDAR = ? WHERE LESSON_ID = ?;";
    public static final String DELETE_BY_ID_QUERY = "DELETE FROM LESSON WHERE LESSON_ID = ?;";
    protected LessonMapper lessonMapper;
    protected GroupMapper groupMapper;
    protected RoomMapper roomMapper;
    protected TeacherMapper teacherMapper;
    protected CourseMapper courseMapper;
    protected TimetableMapper timetableMapper;

    @Autowired
    public LessonDaoImpl(JdbcTemplate jdbcTemplate, TransactionTemplate transactionTemplate,
                         LessonMapper lessonMapper, GroupMapper groupMapper,
                         RoomMapper roomMapper, TeacherMapper teacherMapper,
                         CourseMapper courseMapper, TimetableMapper timetableMapper) {
        super(jdbcTemplate, transactionTemplate, FIND_BY_ID_QUERY, FIND_ALL_QUERY,
                SAVE_QUERY, UPDATE_QUERY, DELETE_BY_ID_QUERY);
        this.lessonMapper = lessonMapper;
        this.groupMapper = groupMapper;
        this.roomMapper = roomMapper;
        this.teacherMapper = teacherMapper;
        this.courseMapper = courseMapper;
        this.timetableMapper = timetableMapper;
    }

    @Override
    protected Lesson findByIntParam(String sql, Integer id) {

        return transactionTemplate.execute( status -> {
        Lesson lesson =  jdbcTemplate.query(FIND_BY_ID_QUERY,
                    new Object[]{id}, lessonMapper)
                    .stream().findAny().orElse(null);

            if (lesson != null) {
                fillLesson(lesson);
            }

            return lesson;
        });
    }

    @Override
    protected List<Lesson> findAllEntities(String sql) {

        return transactionTemplate.execute( status -> {
            List<Lesson> lessons = jdbcTemplate.query(FIND_ALL_QUERY, lessonMapper);

            for(Lesson lesson : lessons) {
                fillLesson(lesson);
            }

            return lessons;
        });
    }

    @Override
    protected List<Lesson> findAllEntitiesWithinScope(String sql, int leftScope, int rightScope) {

        return transactionTemplate.execute( status -> {
            List<Lesson> lessons =
                    jdbcTemplate.query(FIND_ALL_QUERY_WITH_LIMIT,
                            new Object[] { leftScope, rightScope }, lessonMapper);

            for(Lesson lesson : lessons) {
                fillLesson(lesson);
            }

            return lessons;
        });
    }

    @Override
    protected void insert(String sql, Lesson lesson) {

        jdbcTemplate.update(SAVE_QUERY, lesson.getGroup().getId(), lesson.getRoom().getId(), lesson.getTeacher().getId(),
                lesson.getCourse().getId(), lesson.getTimetable().getId(), lesson.getCalendar());
    }

    @Override
    protected void updateValue(String sql, Integer id, Lesson lesson) {

        jdbcTemplate.update(UPDATE_QUERY, lesson.getGroup().getId(), lesson.getRoom().getId(), lesson.getTeacher().getId(),
                lesson.getCourse().getId(), lesson.getTimetable().getId(), lesson.getCalendar(), id);
    }

    @Override
    protected void deleteValue(String sql, Integer id) {

        jdbcTemplate.update(DELETE_BY_ID_QUERY, id);
    }

    private Calendar getCalendar(int id) {
        String sql = "select calendar from lesson where lesson_id = ?;";

        return jdbcTemplate.queryForObject(sql, new Object[] { id }, Calendar.class);
    }

    private Lesson fillLesson(Lesson lesson) {

        return transactionTemplate.execute( status -> {

            lesson.setGroup(jdbcTemplate.query("select * from uni_group where group_id = ?;",
                    new Object[]{lesson.getGroup().getId()}, groupMapper).stream().findAny().orElse(null));

            lesson.setRoom(jdbcTemplate.query("select * from room where room_id = ?;",
                    new Object[]{lesson.getRoom().getId()}, roomMapper).stream().findAny().orElse(null));

            lesson.setTeacher(jdbcTemplate.query("select * from teacher where teacher_id = ?;",
                    new Object[]{lesson.getTeacher().getId()}, teacherMapper).stream().findAny().orElse(null));

            lesson.setCourse(jdbcTemplate.query("select * from course where course_id = ?;",
                    new Object[]{lesson.getCourse().getId()}, courseMapper).stream().findAny().orElse(null));

            lesson.setTimetable(jdbcTemplate.query("select * from timetable where timetable_id = ?;",
                    new Object[]{lesson.getTimetable().getId()}, timetableMapper).stream().findAny().orElse(null));

            lesson.setCalendar(getCalendar(lesson.getId()));

        return lesson;
        });
    }

}
