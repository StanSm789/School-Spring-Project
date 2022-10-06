package com.smirnov.springschooldatabase.controller;

import com.smirnov.springschooldatabase.dao.impl.TimetableDaoImpl;
import com.smirnov.springschooldatabase.domain.Group;
import com.smirnov.springschooldatabase.domain.Timetable;
import com.smirnov.springschooldatabase.view.TimetableViewProvider;
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
class TimetableFrontControllerTest {

    @Mock
    TimetableViewProvider viewProvider;

    @Mock
    TimetableDaoImpl timetableDao;

    @InjectMocks
    TimetableFrontController controller;

    @Test
    void startMenuShouldShowTimetableWhereIdIs1() {

        Timetable expectedTimetable = Timetable.builder()
                .withId(1)
                .withDescription("some description")
                .build();
        String expectedString = "Timetable ID: 1, Timetable Description: some description, Full Timetable: null";

        when(viewProvider.readInt()).thenReturn(1);
        when(timetableDao.findById(1)).thenReturn(Optional.of(expectedTimetable));
        when(viewProvider.provideView(expectedTimetable)).thenReturn(expectedString);
        controller.startMenu();
        verify(timetableDao).findById(1);
        verify(viewProvider).provideView(timetableDao.findById(1).get());
    }

    @Test
    void startMenuShouldShowAllTimetables() {

        Timetable firstTimetable = Timetable.builder()
                .withId(1)
                .withDescription("some description")
                .build();
        Timetable secondTimetable = Timetable.builder()
                .withId(2)
                .withDescription("some description")
                .build();
        List<Timetable> expectedTimetable = Arrays.asList(firstTimetable, secondTimetable);

        String firstString = "Timetable ID: 1, Timetable Description: some description, Full Timetable: null\n";
        String secondString = "Timetable ID: 2, Timetable Description: some description, Full Timetable: null\n";
        List<String> expectedStrings = Arrays.asList(firstString, secondString);

        when(viewProvider.readInt()).thenReturn(2);
        when(timetableDao.findAll()).thenReturn(expectedTimetable);
        when(viewProvider.provideView(expectedTimetable)).thenReturn(expectedStrings);
        controller.startMenu();
        verify(viewProvider).provideView(timetableDao.findAll());
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
        when(timetableDao.findById(1)).thenReturn(Optional.empty());
        when(mock.toString()).thenReturn("entity does not exist");
        controller.startMenu();

        verify(viewProvider).print("entity does not exist");
    }

}
