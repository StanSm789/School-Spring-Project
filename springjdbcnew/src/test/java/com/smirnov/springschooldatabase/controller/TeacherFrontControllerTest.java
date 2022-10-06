package com.smirnov.springschooldatabase.controller;

import com.smirnov.springschooldatabase.dao.impl.TeacherDaoImpl;
import com.smirnov.springschooldatabase.domain.Teacher;
import com.smirnov.springschooldatabase.view.TeacherViewProvider;
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
class TeacherFrontControllerTest {

    @Mock
    TeacherViewProvider viewProvider;

    @Mock
    TeacherDaoImpl teacherDao;

    @InjectMocks
    TeacherFrontController controller;

    @Test
    void startMenuShouldShowTeacherWhereIdIs1() {

        Teacher expectedTeacher = Teacher.builder()
                .withId(1)
                .withFirstName("James")
                .withLastName("Smith")
                .withDegreeTitle("PhD")
                .withDegreeDescription("some description")
                .withSocialMedia("linkedIn")
                .build();
        String expectedString = "Teacher ID: 1, First Name: James, Last Name: Smith, " +
                "Degree Title: PhD, Degree Description: some description, Social Media: linkedIn";

        when(viewProvider.readInt()).thenReturn(1);
        when(teacherDao.findById(1)).thenReturn(Optional.of(expectedTeacher));
        when(viewProvider.provideView(expectedTeacher)).thenReturn(expectedString);
        controller.startMenu();
        verify(teacherDao).findById(1);
        verify(viewProvider).provideView(teacherDao.findById(1).get());
    }

    @Test
    void startMenuShouldShowAllTeachers() {

        Teacher firstTeacher = Teacher.builder()
                .withId(1)
                .withFirstName("James")
                .withLastName("Smith")
                .withDegreeTitle("PhD")
                .withDegreeDescription("some description")
                .withSocialMedia("linkedIn")
                .build();
        Teacher secondTeacher = Teacher.builder()
                .withId(2)
                .withFirstName("ALex")
                .withLastName("Cronin")
                .withDegreeTitle("PhD")
                .withDegreeDescription("some description")
                .withSocialMedia("linkedIn")
                .build();
        List<Teacher> expectedTeachers = Arrays.asList(firstTeacher, secondTeacher);

        String firstString = "Teacher ID: 1, First Name: James, Last Name: Smith, " +
                "Degree Title: PhD, Degree Description: some description, Social Media: linkedIn";
        String secondString = "Teacher ID: 2, First Name: Alex, Last Name: Cronin, " +
                "Degree Title: PhD, Degree Description: some description, Social Media: linkedIn";
        List<String> expectedStrings = Arrays.asList(firstString, secondString);

        when(viewProvider.readInt()).thenReturn(2);
        when(teacherDao.findAll()).thenReturn(expectedTeachers);
        when(viewProvider.provideView(expectedTeachers)).thenReturn(expectedStrings);
        controller.startMenu();
        verify(viewProvider).provideView(teacherDao.findAll());
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
        when(teacherDao.findById(1)).thenReturn(Optional.empty());
        when(mock.toString()).thenReturn("entity does not exist");
        controller.startMenu();

        verify(viewProvider).print("entity does not exist");
    }

}
