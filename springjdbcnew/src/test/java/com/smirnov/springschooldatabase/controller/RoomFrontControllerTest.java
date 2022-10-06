package com.smirnov.springschooldatabase.controller;

import com.smirnov.springschooldatabase.dao.impl.RoomDaoImpl;
import com.smirnov.springschooldatabase.domain.Room;
import com.smirnov.springschooldatabase.view.RoomViewProvider;
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
class RoomFrontControllerTest {

    @Mock
    RoomViewProvider viewProvider;

    @Mock
    RoomDaoImpl roomDao;

    @InjectMocks
    RoomFrontController controller;

    @Test
    void startMenuShouldShowRoomWhereIdIs1() {

        Room expectedRoom = Room.builder()
                .withId(1)
                .withCampus("Some campus")
                .withAddress("Some address")
                .withRoomNumber(10)
                .build();
        String expectedString = "Room ID: 1, Campus: Some campus, Address: Some address, Room Number: 10";

        when(viewProvider.readInt()).thenReturn(1);
        when(roomDao.findById(1)).thenReturn(Optional.of(expectedRoom));
        when(viewProvider.provideView(expectedRoom)).thenReturn(expectedString);
        controller.startMenu();
        verify(roomDao).findById(1);
        verify(viewProvider).provideView(roomDao.findById(1).get());
    }

    @Test
    void startMenuShouldShowAllRooms() {

        Room firstRoom = Room.builder()
                .withId(1)
                .withCampus("Some campus")
                .withAddress("Some address")
                .withRoomNumber(10)
                .build();
        Room secondRoom = Room.builder()
                .withId(1)
                .withCampus("Some other campus")
                .withAddress("Some other address")
                .withRoomNumber(100)
                .build();
        List<Room> expectedRooms = Arrays.asList(firstRoom, secondRoom);

        String firstString = "Room ID: 1, Campus: Some campus, Address: Some address, Room Number: 10\n";
        String secondString = "Room ID: 1, Campus: Some other campus, Address: Some other address, Room Number: 100\n";
        List<String> expectedStrings = Arrays.asList(firstString, secondString);

        when(viewProvider.readInt()).thenReturn(2);
        when(roomDao.findAll()).thenReturn(expectedRooms);
        when(viewProvider.provideView(expectedRooms)).thenReturn(expectedStrings);
        controller.startMenu();
        verify(viewProvider).provideView(roomDao.findAll());
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
        when(roomDao.findById(1)).thenReturn(Optional.empty());
        when(mock.toString()).thenReturn("entity does not exist");
        controller.startMenu();

        verify(viewProvider).print("entity does not exist");
    }

}
