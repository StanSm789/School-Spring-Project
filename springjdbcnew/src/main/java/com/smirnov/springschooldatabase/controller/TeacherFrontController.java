package com.smirnov.springschooldatabase.controller;

import com.smirnov.springschooldatabase.dao.TeacherDao;
import com.smirnov.springschooldatabase.domain.Teacher;
import com.smirnov.springschooldatabase.view.TeacherViewProvider;
import lombok.AllArgsConstructor;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class TeacherFrontController {
    private final TeacherDao teacherDao;
    private final TeacherViewProvider viewProvider;

    public void startMenu() {
        viewProvider.print("Press 1 to find a teacher by id; \n" +
                "Press 2 find all teachers");
        int choose = viewProvider.readInt();

        switch (choose) {
            case 1:
                viewProvider.print("Input id");
                int id = viewProvider.readInt();
                Optional<Teacher> value = teacherDao.findById(id);

                if(value.isPresent()) {
                    Teacher teacher = value.get();
                    viewProvider.print(viewProvider.provideView(teacher));
                } else {
                    viewProvider.print("entity does not exist");
                }
                break;
            case 2:
                List<Teacher> teachers = teacherDao.findAll();
                viewProvider.printList(viewProvider.provideView(teachers));
                break;
            default:
                viewProvider.print("wrong number");
        }

    }

}
