package com.smirnov.springschooldatabase.view;

import com.smirnov.springschooldatabase.domain.Room;
import com.smirnov.springschooldatabase.view.impl.RoomViewProviderImpl;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

class RoomViewProviderImplTest {

    private final RoomViewProviderImpl viewProvider = new RoomViewProviderImpl();

    @Test
    void provideViewShouldDisplayInformationAboutRoomWhereIdIs1() {

        Room expectedRoom = Room.builder()
                .withId(1)
                .withCampus("Some campus")
                .withAddress("Some address")
                .withRoomNumber(10)
                .build();
        String expectedRoomView = "Room ID: 1, Campus: Some campus, Address: Some address, Room Number: 10";
        String actualRoomView = viewProvider.provideView(expectedRoom);

        assertThat(actualRoomView, is(equalTo(expectedRoomView)));
    }

    @Test
    void provideViewShouldDisplayInformationAboutAllRooms() {

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
        List<String> expectedRoomsView = Arrays.asList(firstString, secondString);
        List<String> actualRoomsView = viewProvider.provideView(expectedRooms);

        assertThat(actualRoomsView, is(equalTo(expectedRoomsView)));
    }

}
