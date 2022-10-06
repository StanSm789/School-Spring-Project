package com.smirnov.springschooldatabase.view.impl;

import com.smirnov.springschooldatabase.domain.Room;
import com.smirnov.springschooldatabase.view.RoomViewProvider;
import java.util.ArrayList;
import java.util.List;

public class RoomViewProviderImpl extends AbstractViewProvider<Room> implements RoomViewProvider {

    @Override
    protected String makeView(Room room) {

        return assembleString(room);
    }

    @Override
    protected List<String> makeView(List<Room> lists) {

        List<String> result = new ArrayList<>();

        for (Room room : lists) {
            result.add(assembleString(room) + "\n");
        }

        return result;
    }

    @Override
    protected String assembleString(Room room) {

        return String.format("Room ID: %d, Campus: %s, Address: %s, Room Number: %d",
                room.getId(), room.getCampus(), room.getAddress(), room.getRoomNumber());
    }

}
