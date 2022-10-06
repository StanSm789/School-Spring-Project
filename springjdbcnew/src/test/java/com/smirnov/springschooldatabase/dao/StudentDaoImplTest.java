package com.smirnov.springschooldatabase.dao;

import com.smirnov.springschooldatabase.dao.impl.StudentDaoImpl;
import com.smirnov.springschooldatabase.dao.mappers.GroupMapper;
import com.smirnov.springschooldatabase.dao.mappers.StudentMapper;
import com.smirnov.springschooldatabase.domain.Group;
import com.smirnov.springschooldatabase.domain.Student;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.support.TransactionTemplate;
import java.util.Arrays;
import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

class StudentDaoImplTest extends DaoTest {

    private final StudentDao studentDao = new StudentDaoImpl(jdbcTemplate(), new TransactionTemplate(transactionManager()),
            new StudentMapper(), new GroupMapper());

    @Test
    void findByIdShouldReturnStudentWhenStudentIdIs1() {

        Student expectedStudent = Student.builder().withId(1).withFirstName("John")
                .withLastName("Wang").withStudyModeTitle("full time")
                .withStudyModeDescription("full time description").build();
        Student actualStudent= studentDao.findById(1).get();

        assertThat(expectedStudent, is(equalTo(actualStudent)));
    }

    @Test
    void findAllShouldFindAllStudents() {

        Student expectedStudent = Student.builder().withId(1).withFirstName("John")
                .withLastName("Wang").withStudyModeTitle("full time")
                .withStudyModeDescription("full time description").build();
        List<Student> expectedStudents = Arrays.asList(expectedStudent);
        List<Student> actualStudents = studentDao.findAll();

        assertThat(expectedStudents, is(equalTo(actualStudents)));
    }

    @Test
    void findAllShouldFindAllStudentsWithPagination() {

        Student expectedStudent = Student.builder().withId(1).withFirstName("John")
                .withLastName("Wang").withStudyModeTitle("full time")
                .withStudyModeDescription("full time description").build();
        List<Student> expectedStudents = Arrays.asList(expectedStudent);
        List<Student> actualStudents = studentDao.findAll(0, 1);

        assertThat(expectedStudents, is(equalTo(actualStudents)));
    }

    @Test
    void saveShouldSaveStudent() {

        Student expectedStudent = Student.builder().withId(2).withFirstName("Mark")
                .withLastName("Maron").withStudyModeTitle("full time")
                .withStudyModeDescription("fill time description").build();
        studentDao.save(expectedStudent);
        Student actualStudent = studentDao.findById(2).get();

        assertThat(expectedStudent, is(equalTo(actualStudent)));
    }

    @Test
    void saveShouldSaveStudentWithGroup() {

        Student expectedStudent = Student.builder().withId(2).withFirstName("Mark")
                .withLastName("Maron").withStudyModeTitle("full time")
                .withStudyModeDescription("fill time description").withGroupId(1).build();
        studentDao.save(expectedStudent);
        Student actualStudent = studentDao.findById(2).get();

        assertThat(expectedStudent, is(equalTo(actualStudent)));
    }

    @Test
    void updateShouldUpdateStudent() {

        Student expectedStudent = Student.builder().withId(1).withFirstName("John")
                .withLastName("Wang").withStudyModeTitle("full time")
                .withStudyModeDescription("full time study mode").build();

        studentDao.update(1, expectedStudent);
        Student actualStudent = studentDao.findById(1).get();

        assertThat(expectedStudent, is(equalTo(actualStudent)));
    }

    @Test
    void updateShouldUpdateStudentWithGroup() {

        Student expectedStudent = Student.builder().withId(1).withFirstName("John")
                .withLastName("Wang").withStudyModeTitle("full time")
                .withStudyModeDescription("full time study mode").withGroupId(2).build();

        studentDao.update(1, expectedStudent);
        Student actualStudent = studentDao.findById(1).get();

        assertThat(expectedStudent, is(equalTo(actualStudent)));
    }

    @Test
    void deleteByIdShouldDeleteStudentWhereStudentIdIs1() {

        studentDao.deleteById(1);
        Boolean actualResult = studentDao.findById(1).isPresent();

        assertThat(false, is(equalTo(actualResult)));
    }

    @Test
    void addGroupToStudentShouldAddGroupToStudentWhereStudentIdIs1() {

        Group expectedGroup = Group.builder().withId(1).withName("ff-55").build();

        int expectedStudentId = 1;

        studentDao.addGroupToStudent(1, expectedGroup);
        int actualStudentId = studentDao.findById(1).get().getGroupId();

        assertThat(expectedStudentId, is(equalTo(actualStudentId)));
    }

}
