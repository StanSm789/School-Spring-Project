package com.smirnov.springschooldatabase.dao;

import com.smirnov.springschooldatabase.dao.impl.CourseDaoImpl;
import com.smirnov.springschooldatabase.dao.mappers.CourseMapper;
import com.smirnov.springschooldatabase.dao.mappers.TeacherMapper;
import com.smirnov.springschooldatabase.domain.Course;
import com.smirnov.springschooldatabase.domain.Teacher;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.support.TransactionTemplate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

class CourseDaoImplTest extends DaoTest {

    private final CourseDao courseDao = new CourseDaoImpl(jdbcTemplate(), new TransactionTemplate(transactionManager()),
            new CourseMapper(), new TeacherMapper());

    @Test
    void findByIdShouldReturnCourseWhenCourseIdIs1() {

        Teacher teacher =
                Teacher.builder().withId(1).withFirstName("James").withLastName("Smith").withDegreeTitle("PhD in Physics")
                        .withDegreeDescription("Physics studies").withSocialMedia("LinkedIn").build();
        List<Teacher> teachers = Arrays.asList(teacher);

        Course expectedCourse = Course.builder().withId(1).withName("Physics")
                .withDescription("Physics studies").withTeachers(teachers).build();
        Course actualCourse = courseDao.findById(1).get();

        assertThat(expectedCourse, is(equalTo(actualCourse)));
    }

    @Test
    void findAllShouldFindAllCourses() {

        Teacher teacher =
                Teacher.builder().withId(1).withFirstName("James").withLastName("Smith").withDegreeTitle("PhD in Physics")
                        .withDegreeDescription("Physics studies").withSocialMedia("LinkedIn").build();
        List<Teacher> teachers = Arrays.asList(teacher);

        Course course = Course.builder().withId(1).withName("Physics")
                .withDescription("Physics studies").withTeachers(teachers).build();

        List<Course> expectedCourses = new ArrayList<>();
        expectedCourses.add(course);
        List<Course> actualCourses= courseDao.findAll();

        assertThat(expectedCourses, is(equalTo(actualCourses)));
    }

    @Test
    void findAllShouldFindAllCoursesWithPagination() {

        Teacher teacher =
                Teacher.builder().withId(1).withFirstName("James").withLastName("Smith").withDegreeTitle("PhD in Physics")
                        .withDegreeDescription("Physics studies").withSocialMedia("LinkedIn").build();
        List<Teacher> teachers = Arrays.asList(teacher);

        Course course = Course.builder().withId(1).withName("Physics")
                .withDescription("Physics studies").withTeachers(teachers).build();

        List<Course> expectedCourses = new ArrayList<>();
        expectedCourses.add(course);
        List<Course> actualCourses= courseDao.findAll(0, 1);

        assertThat(expectedCourses, is(equalTo(actualCourses)));
    }

    @Test
    void saveShouldSaveCourse() {

        List<Teacher> teachers = new ArrayList<>();

        Course expectedCourse = Course.builder()
                .withId(2)
                .withName("Physics")
                .withDescription("Physics studies for students")
                .withTeachers(teachers)
                .build();
        courseDao.save(expectedCourse);
        Course actualCourse = courseDao.findById(2).get();

        assertThat(expectedCourse, is(equalTo(actualCourse)));
    }

    @Test
    void updateShouldUpdateCourse() {

        List<Teacher> teachers = new ArrayList<>();

        Course expectedCourse = Course.builder()
                .withId(1)
                .withName("Advanced Mathematics")
                .withDescription("Advanced math studies for students")
                .withTeachers(teachers)
                .build();

        courseDao.update(1, expectedCourse);
        courseDao.removeTeachersFromCourse(expectedCourse.getId());
        Course actualCourse = courseDao.findById(1).get();

        assertThat(expectedCourse, is(equalTo(actualCourse)));
    }

    @Test
    void deleteByIdShouldDeleteCourseWhereCourseIdIs1() {

        courseDao.deleteById(1);
        Boolean actualResult = courseDao.findById(1).isPresent();

        assertThat(false, is(equalTo(actualResult)));
    }

    @Test
    void addTeacherToCourseShouldAddTeacherToCourse() {

        Teacher expectedTeacher =
                Teacher.builder().withId(1).withFirstName("James").withLastName("Smith").withDegreeTitle("PhD in Physics")
                        .withDegreeDescription("Physics studies").withSocialMedia("LinkedIn").build();
        courseDao.removeTeachersFromCourse(1);
        courseDao.addTeacherToCourse(1, expectedTeacher);

        assertThat(expectedTeacher, is(equalTo(courseDao.findById(1).get().getTeachers().get(0))));
    }

    @Test
    void removeTeacherFromCourseShouldRemoveTeacherFromCourseWhereCourseIdIs1AndTeacherIdIs1() {

        Teacher expectedTeacher =
                Teacher.builder().withId(1).withFirstName("James").withLastName("Smith").withDegreeTitle("PhD in Physics")
                        .withDegreeDescription("Physics studies").withSocialMedia("LinkedIn").build();
        courseDao.removeTeacherFromCourse(1, expectedTeacher);
        Boolean actualResult = courseDao.findById(1).get()
                .getTeachers().isEmpty();

        assertThat(true, is(equalTo(actualResult)));
    }

    @Test
    void removeTeachersFromCourseShouldRemoveTeachersFromCourseWhereCourseIdIs1() {

        courseDao.removeTeachersFromCourse(1);
        Boolean actualResult = courseDao.findById(1).get()
                .getTeachers().isEmpty();

        assertThat(true, is(equalTo(actualResult)));
    }

}
