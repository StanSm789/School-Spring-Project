package com.smirnov.springschooldatabase.controller;

import com.smirnov.springschooldatabase.dao.RoomDao;
import com.smirnov.springschooldatabase.domain.Room;
import com.smirnov.springschooldatabase.view.RoomViewProvider;
import lombok.AllArgsConstructor;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class RoomFrontController {
    private final RoomDao roomDao;
    private final RoomViewProvider viewProvider;

    public void startMenu() {
        viewProvider.print("Press 1 to find a room by id; \n" +
                "Press 2 find all rooms");
        int choose = viewProvider.readInt();

        switch (choose) {
            case 1:
                viewProvider.print("Input id");
                int id = viewProvider.readInt();
                Optional<Room> value = roomDao.findById(id);

                if(value.isPresent()) {
                    Room room = value.get();
                    viewProvider.print(viewProvider.provideView(room));
                } else {
                    viewProvider.print("entity does not exist");
                }
                break;
            case 2:
                List<Room> rooms = roomDao.findAll();
                viewProvider.printList(viewProvider.provideView(rooms));
                break;
            default:
                viewProvider.print("wrong number");
        }

    }

}
