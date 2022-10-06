package com.smirnov.springschooldatabase.controller;

import com.smirnov.springschooldatabase.dao.TimetableDao;
import com.smirnov.springschooldatabase.domain.Timetable;
import com.smirnov.springschooldatabase.view.TimetableViewProvider;
import lombok.AllArgsConstructor;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class TimetableFrontController {
    private final TimetableDao timetableDao;
    private final TimetableViewProvider viewProvider;

    public void startMenu() {
        viewProvider.print("Press 1 to find a timetable by id; \n" +
                "Press 2 find all timetables");
        int choose = viewProvider.readInt();

        switch (choose) {
            case 1:
                viewProvider.print("Input id");
                int id = viewProvider.readInt();
                Optional<Timetable> value = timetableDao.findById(id);

                if(value.isPresent()) {
                    Timetable timetable = value.get();
                    viewProvider.print(viewProvider.provideView(timetable));
                } else {
                    viewProvider.print("entity does not exist");
                }
                break;
            case 2:
                List<Timetable> timetables = timetableDao.findAll();
                viewProvider.printList(viewProvider.provideView(timetables));
                break;
            default:
                viewProvider.print("wrong number");
        }

    }

}
