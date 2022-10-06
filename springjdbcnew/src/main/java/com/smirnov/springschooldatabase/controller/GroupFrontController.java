package com.smirnov.springschooldatabase.controller;

import com.smirnov.springschooldatabase.dao.GroupDao;
import com.smirnov.springschooldatabase.domain.Group;
import com.smirnov.springschooldatabase.view.GroupViewProvider;
import lombok.AllArgsConstructor;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class GroupFrontController {
    private final GroupDao groupDao;
    private final GroupViewProvider viewProvider;

    public void startMenu() {
        viewProvider.print("Press 1 to find a group by id; \n" +
                "Press 2 find all groups");
        int choose = viewProvider.readInt();

        switch (choose) {
            case 1:
                viewProvider.print("Input id");
                int id = viewProvider.readInt();
                Optional<Group> value = groupDao.findById(id);

                if(value.isPresent()) {
                    Group group = value.get();
                    viewProvider.print(viewProvider.provideView(group));
                } else {
                    viewProvider.print("entity does not exist");

                }
                break;
            case 2:
                List<Group> groups = groupDao.findAll();
                viewProvider.printList(viewProvider.provideView(groups));
                break;
            default:
                viewProvider.print("wrong number");
        }

    }

}
