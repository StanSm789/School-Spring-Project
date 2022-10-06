package com.smirnov.springschooldatabase.dao;

import com.smirnov.springschooldatabase.dao.impl.GroupDaoImpl;
import com.smirnov.springschooldatabase.dao.mappers.CourseMapper;
import com.smirnov.springschooldatabase.dao.mappers.GroupMapper;
import com.smirnov.springschooldatabase.dao.mappers.StudentMapper;
import com.smirnov.springschooldatabase.dao.mappers.TeacherMapper;
import com.smirnov.springschooldatabase.domain.Course;
import com.smirnov.springschooldatabase.domain.Group;
import com.smirnov.springschooldatabase.domain.Teacher;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.support.TransactionTemplate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

class GroupDaoImplTest extends DaoTest {

    private final GroupDao groupDao = new GroupDaoImpl(jdbcTemplate(), new TransactionTemplate(transactionManager()),
            new CourseMapper(), new GroupMapper(), new StudentMapper(), new TeacherMapper());

    @Test
    void findByIdShouldReturnGroupWhenGroupIdIs1() {

        Group expectedGroup = Group.builder().withId(1).withName("ff-55").withCourses(Collections.emptyList())
                .withStudents(Collections.emptyList()).build();
        Group actualGroup= groupDao.findById(1).get();

        assertThat(expectedGroup, is(equalTo(actualGroup)));
    }

    @Test
    void findAllShouldFindAllGroups() {

        Group expectedGroupOne = Group.builder().withId(1).withName("ff-55").withCourses(Collections.emptyList())
                .withStudents(Collections.emptyList()).build();
        Group expectedGroupTwo = Group.builder().withId(2).withName("ff-77").withCourses(Collections.emptyList())
                .withStudents(Collections.emptyList()).build();
        List<Group> expectedGroups = Arrays.asList(expectedGroupOne, expectedGroupTwo);
        List<Group> actualGroups = groupDao.findAll();

        assertThat(expectedGroups, is(equalTo(actualGroups)));
    }

    @Test
    void findAllShouldFindAllGroupsWithPagination() {

        Group expectedGroupOne = Group.builder().withId(1).withName("ff-55").withCourses(Collections.emptyList())
                .withStudents(Collections.emptyList()).build();
        List<Group> expectedGroups = Arrays.asList(expectedGroupOne);
        List<Group> actualGroups = groupDao.findAll(0, 1);

        assertThat(expectedGroups, is(equalTo(actualGroups)));
    }

    @Test
    void saveShouldSaveGroup() {

        Group expectedGroup = Group.builder().withId(2).withName("ff-77").withCourses(Collections.emptyList())
                .withStudents(Collections.emptyList()).build();
        groupDao.save(expectedGroup);
        Group actualGroup= groupDao.findById(2).get();

        assertThat(expectedGroup, is(equalTo(actualGroup)));
    }

    @Test
    void updateShouldUpdateGroup() {

        Group expectedGroup = Group.builder().withId(1).withName("ff-100").withCourses(Collections.emptyList())
                .withStudents(Collections.emptyList()).build();
        groupDao.update(1, expectedGroup);
        Group actualGroup = groupDao.findById(1).get();

        assertThat(expectedGroup, is(equalTo(actualGroup)));
    }

    @Test
    void deleteByIdShouldDeleteGroupWhereGroupIdIs1() {

        groupDao.deleteById(1);
        Boolean actualResult = groupDao.findById(1).isPresent();

        assertThat(false, is(equalTo(actualResult)));
    }

    @Test
    void addCourseToGroupShouldAddCourseToGroup() {

        Teacher teacher = Teacher.builder().withId(1).withFirstName("James").withLastName("Smith")
                .withDegreeTitle("PhD in Physics").withDegreeDescription("Physics studies")
                .withSocialMedia("LinkedIn").build();
        Course expectedCourse = Course.builder().withId(1).withName("Physics")
                .withDescription("Physics studies").withTeachers(Arrays.asList(teacher)).build();
        groupDao.addCourseToGroup(1, expectedCourse);
        
        Course actualCourse = groupDao.findById(1).get().getCourses().get(0);

        assertThat(expectedCourse, is(equalTo(actualCourse)));
    }

}
