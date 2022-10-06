package com.smirnov.springschooldatabase.dao;

import com.smirnov.springschooldatabase.dao.impl.LessonDaoImpl;
import com.smirnov.springschooldatabase.dao.mappers.CourseMapper;
import com.smirnov.springschooldatabase.dao.mappers.GroupMapper;
import com.smirnov.springschooldatabase.dao.mappers.LessonMapper;
import com.smirnov.springschooldatabase.dao.mappers.RoomMapper;
import com.smirnov.springschooldatabase.dao.mappers.TeacherMapper;
import com.smirnov.springschooldatabase.dao.mappers.TimetableMapper;
import com.smirnov.springschooldatabase.domain.Course;
import com.smirnov.springschooldatabase.domain.Group;
import com.smirnov.springschooldatabase.domain.Lesson;
import com.smirnov.springschooldatabase.domain.Room;
import com.smirnov.springschooldatabase.domain.Teacher;
import com.smirnov.springschooldatabase.domain.Timetable;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.support.TransactionTemplate;
import java.util.Arrays;
import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

class LessonDaoImplTest extends DaoTest {

    private final LessonDao lessonDao = new LessonDaoImpl(jdbcTemplate(), new TransactionTemplate(transactionManager()),
            new LessonMapper(jdbcTemplate()), new GroupMapper(),
            new RoomMapper(), new TeacherMapper(), new CourseMapper(), new TimetableMapper());

    @Test
    void findByIdShouldReturnLessonWhenLessonIdIs1() {

        Timetable expectedTimetable = Timetable.builder().withId(1).withDescription("some description")
                .withTimetable(null).build();
        Teacher expectedTeacher =
                Teacher.builder().withId(1).withFirstName("James").withLastName("Smith").withDegreeTitle("PhD in Physics")
                        .withDegreeDescription("Physics studies").withSocialMedia("LinkedIn").build();
        Course expectedCourse = Course.builder().withId(1).withName("Physics")
                .withDescription("Physics studies").withTeachers(null).build();
        Room expectedRoom = Room.builder().withId(1).withCampus("Nathan")
                .withAddress("Kessel Road").withRoomNumber(10).build();
        Group expectedGroup = Group.builder().withId(1).withName("ff-55").withCourses(null)
                .withStudents(null).build();

        Lesson expectedLesson = Lesson.builder().withId(1).withGroup(expectedGroup).withRoom(expectedRoom)
                .withTeacher(expectedTeacher).withCourse(expectedCourse).withTimetable(expectedTimetable).build();
        Lesson actualLesson = lessonDao.findById(1).get();

        assertThat(expectedLesson, is(equalTo(actualLesson)));
    }

    @Test
    void findAllShouldFindAllLessons() {

        Timetable expectedTimetable = Timetable.builder().withId(1).withDescription("some description")
                .withTimetable(null).build();
        Teacher expectedTeacher =
                Teacher.builder().withId(1).withFirstName("James").withLastName("Smith").withDegreeTitle("PhD in Physics")
                        .withDegreeDescription("Physics studies").withSocialMedia("LinkedIn").build();
        Course expectedCourse = Course.builder().withId(1).withName("Physics")
                .withDescription("Physics studies").withTeachers(null).build();
        Room expectedRoom = Room.builder().withId(1).withCampus("Nathan")
                .withAddress("Kessel Road").withRoomNumber(10).build();
        Group expectedGroup = Group.builder().withId(1).withName("ff-55").withCourses(null)
                .withStudents(null).build();

        Lesson expectedLesson = Lesson.builder().withId(1).withGroup(expectedGroup).withRoom(expectedRoom)
                .withTeacher(expectedTeacher).withCourse(expectedCourse).withTimetable(expectedTimetable).build();
        List<Lesson> expectedLessons = Arrays.asList(expectedLesson);
        List<Lesson> actualLessons = lessonDao.findAll();

        assertThat(expectedLessons, is(equalTo(actualLessons)));
    }

    @Test
    void findAllShouldFindAllLessonsWithPagination() {

        Timetable expectedTimetable = Timetable.builder().withId(1).withDescription("some description")
                .withTimetable(null).build();
        Teacher expectedTeacher =
                Teacher.builder().withId(1).withFirstName("James").withLastName("Smith").withDegreeTitle("PhD in Physics")
                        .withDegreeDescription("Physics studies").withSocialMedia("LinkedIn").build();
        Course expectedCourse = Course.builder().withId(1).withName("Physics")
                .withDescription("Physics studies").withTeachers(null).build();
        Room expectedRoom = Room.builder().withId(1).withCampus("Nathan")
                .withAddress("Kessel Road").withRoomNumber(10).build();
        Group expectedGroup = Group.builder().withId(1).withName("ff-55").withCourses(null)
                .withStudents(null).build();

        Lesson expectedLesson = Lesson.builder().withId(1).withGroup(expectedGroup).withRoom(expectedRoom)
                .withTeacher(expectedTeacher).withCourse(expectedCourse).withTimetable(expectedTimetable).build();
        List<Lesson> expectedLessons = Arrays.asList(expectedLesson);
        List<Lesson> actualLessons = lessonDao.findAll(0, 1);

        assertThat(expectedLessons, is(equalTo(actualLessons)));
    }

    @Test
    void saveShouldSaveLesson() {

        Timetable expectedTimetable = Timetable.builder().withId(1).withDescription("some description")
                .withTimetable(null).build();
        Teacher expectedTeacher =
                Teacher.builder().withId(1).withFirstName("James").withLastName("Smith").withDegreeTitle("PhD in Physics")
                        .withDegreeDescription("Physics studies").withSocialMedia("LinkedIn").build();
        Course expectedCourse = Course.builder().withId(1).withName("Physics")
                .withDescription("Physics studies").withTeachers(null).build();
        Room expectedRoom = Room.builder().withId(1).withCampus("Nathan")
                .withAddress("Kessel Road").withRoomNumber(10).build();
        Group expectedGroup = Group.builder().withId(1).withName("ff-55").withCourses(null)
                .withStudents(null).build();

        Lesson expectedLesson = Lesson.builder().withId(2).withGroup(expectedGroup).withRoom(expectedRoom)
                .withTeacher(expectedTeacher).withCourse(expectedCourse).withTimetable(expectedTimetable).build();
        lessonDao.save(expectedLesson);
        Lesson actualLesson = lessonDao.findById(2).get();

        assertThat(expectedLesson, is(equalTo(actualLesson)));
    }

    @Test
    void updateShouldUpdateLesson() {

        Timetable expectedTimetable = Timetable.builder().withId(1).withDescription("some description")
                .withTimetable(null).build();
        Teacher expectedTeacher =
                Teacher.builder().withId(1).withFirstName("James").withLastName("Smith").withDegreeTitle("PhD in Physics")
                        .withDegreeDescription("Physics studies").withSocialMedia("LinkedIn").build();
        Course expectedCourse = Course.builder().withId(1).withName("Physics")
                .withDescription("Physics studies").withTeachers(null).build();
        Room expectedRoom = Room.builder().withId(1).withCampus("Nathan")
                .withAddress("Kessel Road").withRoomNumber(10).build();
        Group expectedGroup = Group.builder().withId(2).withName("ff-77").withCourses(null)
                .withStudents(null).build();

        Lesson expectedLesson = Lesson.builder().withId(1).withGroup(expectedGroup).withRoom(expectedRoom)
                .withTeacher(expectedTeacher).withCourse(expectedCourse).withTimetable(expectedTimetable).build();
        lessonDao.update(1, expectedLesson);
        Lesson actualLesson = lessonDao.findById(1).get();

        assertThat(expectedLesson, is(equalTo(actualLesson)));
    }

    @Test
    void deleteByIdShouldDeleteLessonWhereLessonIdIs1() {

        lessonDao.deleteById(1);
        Boolean actualResult = lessonDao.findById(1).isPresent();

        assertThat(false, is(equalTo(actualResult)));
    }

}
