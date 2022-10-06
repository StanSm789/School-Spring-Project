package com.smirnov.springschooldatabase.controller;

import com.smirnov.springschooldatabase.dao.impl.StudentDaoImpl;
import com.smirnov.springschooldatabase.domain.Student;
import com.smirnov.springschooldatabase.view.StudentViewProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class StudentFrontControllerTest {

    @Mock
    StudentViewProvider viewProvider;

    @Mock
    StudentDaoImpl studentDao;

    @InjectMocks
    StudentFrontController controller;

    @Test
    void startMenuShouldShowStudentWhereIdIs1() {

        Student expectedStudent = Student.builder()
                .withId(1)
                .withFirstName("James")
                .withLastName("Jones")
                .withStudyModeTitle("full time")
                .withStudyModeDescription("full time description")
                .build();
        String expectedString = "Student ID: 1, Student First Name: James, Student Last Name: Jones, " +
                "Group ID: 0, Degree Title: full time, Degree Description: full time description";

        when(viewProvider.readInt()).thenReturn(1);
        when(studentDao.findById(1)).thenReturn(Optional.of(expectedStudent));
        when(viewProvider.provideView(expectedStudent)).thenReturn(expectedString);
        controller.startMenu();
        verify(studentDao).findById(1);
        verify(viewProvider).provideView(studentDao.findById(1).get());
    }

    @Test
    void startMenuShouldShowAllStudents() {

        Student firstStudent = Student.builder()
                .withId(1)
                .withFirstName("James")
                .withLastName("Jones")
                .withStudyModeTitle("full time")
                .withStudyModeDescription("full time description")
                .build();
        Student secondStudent = Student.builder()
                .withId(2)
                .withFirstName("Stanley")
                .withLastName("Smith")
                .withStudyModeTitle("full time")
                .withStudyModeDescription("full time description")
                .build();
        List<Student> expectedStudents = Arrays.asList(firstStudent, secondStudent);

        String firstString = "Student ID: 1, Student First Name: James, Student Last Name: Jones, " +
                "Group ID: 0, Degree Title: full time, Degree Description: full time description\n";
        String secondString = "Student ID: 2, Student First Name: Stanley, Student Last Name: Smith, " +
                "Group ID: 0, Degree Title: full time, Degree Description: full time description\n";
        List<String> expectedStrings = Arrays.asList(firstString, secondString);

        when(viewProvider.readInt()).thenReturn(2);
        when(studentDao.findAll()).thenReturn(expectedStudents);
        when(viewProvider.provideView(expectedStudents)).thenReturn(expectedStrings);
        controller.startMenu();
        verify(viewProvider).provideView(studentDao.findAll());
    }

    @Test
    void printShouldPrintInformationOnTheConsoleWhenInputNumberIsNotEqual1or2() {
        Object mock = Mockito.mock(Object.class);
        when(viewProvider.readInt()).thenReturn(3);
        when(mock.toString()).thenReturn("wrong number");
        controller.startMenu();

        verify(viewProvider).print("wrong number");
    }

    @Test
    void printShouldIndicateThatEntityDoesNotExistWhenOptionalIsEmpty() {
        Object mock = Mockito.mock(Object.class);
        when(viewProvider.readInt()).thenReturn(1);
        when(studentDao.findById(1)).thenReturn(Optional.empty());
        when(mock.toString()).thenReturn("entity does not exist");
        controller.startMenu();

        verify(viewProvider).print("entity does not exist");
    }

}
