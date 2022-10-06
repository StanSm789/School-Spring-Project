package com.smirnov.springschooldatabase.dao;

import com.smirnov.springschooldatabase.dao.impl.TeacherDaoImpl;
import com.smirnov.springschooldatabase.dao.mappers.CourseMapper;
import com.smirnov.springschooldatabase.dao.mappers.TeacherMapper;
import com.smirnov.springschooldatabase.domain.Course;
import com.smirnov.springschooldatabase.domain.Teacher;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.support.TransactionTemplate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

class TeacherDaoImplTest extends DaoTest {

    private final TeacherDao teacherDao = new TeacherDaoImpl(jdbcTemplate(), new TransactionTemplate(transactionManager()),
            new TeacherMapper(), new CourseMapper());

    @Test
    void findByIdShouldReturnTeacherWhenTeacherIdIs1() {

        Teacher expectedTeacher =
                Teacher.builder().withId(1).withFirstName("James").withLastName("Smith").withDegreeTitle("PhD in Physics")
                .withDegreeDescription("Physics studies").withSocialMedia("LinkedIn").build();
        Teacher actualTeacher = teacherDao.findById(1).get();

        assertThat(expectedTeacher, is(equalTo(actualTeacher)));
    }

    @Test
    void findAllShouldFindAllTeachers() {

        Teacher teacher =
                Teacher.builder().withId(1).withFirstName("James").withLastName("Smith").withDegreeTitle("PhD in Physics")
                        .withDegreeDescription("Physics studies").withSocialMedia("LinkedIn").build();
        List<Teacher> expectedTeachers = Arrays.asList(teacher);

        List<Teacher> actualTeachers= teacherDao.findAll();

        assertThat(expectedTeachers, is(equalTo(actualTeachers)));
    }

    @Test
    void findAllShouldFindAllTeachersWithPagination() {

        Teacher teacher =
                Teacher.builder().withId(1).withFirstName("James").withLastName("Smith").withDegreeTitle("PhD in Physics")
                        .withDegreeDescription("Physics studies").withSocialMedia("LinkedIn").build();
        List<Teacher> expectedTeachers = Arrays.asList(teacher);

        List<Teacher> actualTeachers= teacherDao.findAll(0, 1);

        assertThat(expectedTeachers, is(equalTo(actualTeachers)));
    }

    @Test
    void saveShouldSaveTeacher() {

        Teacher expectedTeacher = Teacher.builder().withId(2).withFirstName("Alex")
                .withLastName("Cronin").withDegreeTitle("PhD in Mathematics")
                .withDegreeDescription("math studies").withSocialMedia("LinkedIn").build();
        teacherDao.save(expectedTeacher);
        Teacher actualTeacher = teacherDao.findById(2).get();

        assertThat(expectedTeacher, is(equalTo(actualTeacher)));
    }

    @Test
    void updateShouldUpdateTeacher() {

        Teacher expectedTeacher = Teacher.builder().withId(1).withFirstName("Alex")
                .withLastName("Cronin").withDegreeTitle("PhD in Mathematics")
                .withDegreeDescription("math studies and physics studies").withSocialMedia("LinkedIn").build();
        teacherDao.update(1, expectedTeacher);
        Teacher actualTeacher = teacherDao.findById(1).get();

        assertThat(expectedTeacher, is(equalTo(actualTeacher)));
    }

    @Test
    void deleteByIdShouldDeleteTeacherWhereTeacherIdIs1() {

        teacherDao.deleteById(1);
        Boolean actualResult = teacherDao.findById(1).isPresent();

        assertThat(false, is(equalTo(actualResult)));
    }

    @Test
    void findTeachingCoursesShouldFindAllCoursesForTeacher() {

        Teacher teacher =
                Teacher.builder().withId(1).withFirstName("James").withLastName("Smith").withDegreeTitle("PhD in Physics")
                        .withDegreeDescription("Physics studies").withSocialMedia("LinkedIn").build();
        List<Teacher> teachers = Arrays.asList(teacher);

        Course expectedCourse = Course.builder().withId(1).withName("Physics")
                .withDescription("Physics studies").withTeachers(teachers).build();
        List<Course> expectedCourses = Arrays.asList(expectedCourse);
        List<Course> actualCourses = teacherDao.findTeachingCourses(1);

        assertThat(expectedCourses, is(equalTo(actualCourses)));
    }

    @Test
    void findTeachingCoursesShouldFindNoCoursesForTeacher() {

        Teacher teacher =
                Teacher.builder().withId(2).withFirstName("James").withLastName("Lewis").withDegreeTitle("PhD in Physics")
                        .withDegreeDescription("Physics studies").withSocialMedia("LinkedIn").build();
        teacherDao.save(teacher);
        List<Teacher> teachers = Arrays.asList(teacher);

        List<Course> expectedCourses = Collections.emptyList();
        List<Course> actualCourses = teacherDao.findTeachingCourses(2);

        assertThat(expectedCourses, is(equalTo(actualCourses)));
    }

}
