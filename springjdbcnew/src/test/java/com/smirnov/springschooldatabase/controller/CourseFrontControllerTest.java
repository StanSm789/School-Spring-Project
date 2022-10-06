package com.smirnov.springschooldatabase.controller;

import com.smirnov.springschooldatabase.dao.impl.CourseDaoImpl;
import com.smirnov.springschooldatabase.domain.Course;
import com.smirnov.springschooldatabase.view.CourseViewProvider;
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
class CourseFrontControllerTest {

    @Mock
    CourseViewProvider viewProvider;

    @Mock
    CourseDaoImpl courseDao;

    @InjectMocks
    CourseFrontController controller;

    @Test
    void startMenuShouldShowCourseWhereIdIs1() {

        Course expectedCourse = Course.builder()
                .withId(1)
                .withName("Mathematics")
                .withDescription("Math studies for students")
                .build();
        String expectedString = "Course ID: 1, Course Name: Mathematics, " +
                "Course Description: Math studies for students, " +
                "Course Teachers: null";

        when(viewProvider.readInt()).thenReturn(1);
        when(courseDao.findById(1)).thenReturn(Optional.of(expectedCourse));
        when(viewProvider.provideView(expectedCourse)).thenReturn(expectedString);
        controller.startMenu();
        verify(courseDao).findById(1);
        verify(viewProvider).provideView(courseDao.findById(1).get());
    }

    @Test
    void startMenuShouldShowAllCourses() {

        Course firstCourse = Course.builder()
                .withId(1)
                .withName("Mathematics")
                .withDescription("Math studies for students")
                .build();
        Course secondCourse = Course.builder()
                .withId(2)
                .withName("English")
                .withDescription("English studies for students")
                .build();
        List<Course> expectedCourses = Arrays.asList(firstCourse, secondCourse);

        String firstString = "Course ID: 1, Course Name: Mathematics, " +
                "Course Description: Math studies for students, " +
                "Course Teachers: null\n";
        String secondString = "Course ID: 2, Course Name: English, " +
                "Course Description: English studies for students, " +
                "Course Teachers: null\n";
        List<String> expectedStrings = Arrays.asList(firstString, secondString);

        when(viewProvider.readInt()).thenReturn(2);
        when(courseDao.findAll()).thenReturn(expectedCourses);
        when(viewProvider.provideView(expectedCourses)).thenReturn(expectedStrings);
        controller.startMenu();
        verify(viewProvider).provideView(courseDao.findAll());
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
        when(courseDao.findById(1)).thenReturn(Optional.empty());
        when(mock.toString()).thenReturn("entity does not exist");
        controller.startMenu();

        verify(viewProvider).print("entity does not exist");
    }

}
