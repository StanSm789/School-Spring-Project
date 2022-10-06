package com.smirnov.springschooldatabase.controller;

import com.smirnov.springschooldatabase.dao.impl.LessonDaoImpl;
import com.smirnov.springschooldatabase.domain.Lesson;
import com.smirnov.springschooldatabase.domain.Room;
import com.smirnov.springschooldatabase.view.LessonViewProvider;
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
class LessonFrontControllerTest {

    @Mock
    LessonViewProvider viewProvider;

    @Mock
    LessonDaoImpl lessonDao;

    @InjectMocks
    LessonFrontController controller;

    @Test
    void startMenuShouldShowLessonWhereIdIs1() {

        Room expectedRoom = Room.builder()
                .withId(1)
                .withCampus("Some campus")
                .withAddress("Some address")
                .withRoomNumber(10)
                .build();

        Lesson expectedLesson = Lesson.builder()
                .withId(1)
                .withRoom(expectedRoom)
                .build();
        String expectedString = "Lesson ID: 1, Group: null, " +
                "Room: , Room{id=1, campus='Some campus', address='Some address', " +
                "roomNumber=10}, teacher=null, course=null, timetable=null, calendar=null}" +
                "Teacher: null, Course: null, Timetable: null, Calendar: null";

        when(viewProvider.readInt()).thenReturn(1);
        when(lessonDao.findById(1)).thenReturn(Optional.of(expectedLesson));
        when(viewProvider.provideView(expectedLesson)).thenReturn(expectedString);
        controller.startMenu();
        verify(lessonDao).findById(1);
        verify(viewProvider).provideView(lessonDao.findById(1).get());
    }

    @Test
    void startMenuShouldShowAllLessons() {

        Room expectedRoom = Room.builder()
                .withId(1)
                .withCampus("Some campus")
                .withAddress("Some address")
                .withRoomNumber(10)
                .build();

        Lesson firstLesson = Lesson.builder()
                .withId(1)
                .withRoom(expectedRoom)
                .build();
        Lesson secondLesson = Lesson.builder()
                .withId(1)
                .withRoom(expectedRoom)
                .build();
        List<Lesson> expectedLessons = Arrays.asList(firstLesson, secondLesson);

        String firstString = "Lesson ID: 1, Group: null, " +
                "Room: , Room{id=1, campus='Some campus', address='Some address', " +
                "roomNumber=10}, teacher=null, course=null, timetable=null, calendar=null}" +
                "Teacher: null, Course: null, Timetable: null, Calendar: null";
        String secondString = "Lesson ID: 2, Group: null, " +
                "Room: , Room{id=1, campus='Some campus', address='Some address', " +
                "roomNumber=10}, teacher=null, course=null, timetable=null, calendar=null}" +
                "Teacher: null, Course: null, Timetable: null, Calendar: null";
        List<String> expectedStrings = Arrays.asList(firstString, secondString);

        when(viewProvider.readInt()).thenReturn(2);
        when(lessonDao.findAll()).thenReturn(expectedLessons);
        when(viewProvider.provideView(expectedLessons)).thenReturn(expectedStrings);
        controller.startMenu();
        verify(viewProvider).provideView(lessonDao.findAll());
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
        when(lessonDao.findById(1)).thenReturn(Optional.empty());
        when(mock.toString()).thenReturn("entity does not exist");
        controller.startMenu();

        verify(viewProvider).print("entity does not exist");
    }

}
