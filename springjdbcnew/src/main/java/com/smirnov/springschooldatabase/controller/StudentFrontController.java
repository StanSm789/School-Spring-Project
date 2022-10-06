package com.smirnov.springschooldatabase.controller;

import com.smirnov.springschooldatabase.dao.StudentDao;
import com.smirnov.springschooldatabase.domain.Student;
import com.smirnov.springschooldatabase.view.StudentViewProvider;
import lombok.AllArgsConstructor;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class StudentFrontController {
    private final StudentDao studentDao;
    private final StudentViewProvider viewProvider;

    public void startMenu() {
        viewProvider.print("Press 1 to find a student by id; \n" +
                "Press 2 find all students");
        int choose = viewProvider.readInt();

        switch (choose) {
            case 1:
                viewProvider.print("Input id");
                int id = viewProvider.readInt();
                Optional<Student> value = studentDao.findById(id);

                if(value.isPresent()) {
                    Student student = value.get();
                    viewProvider.print(viewProvider.provideView(student));
                } else {
                    viewProvider.print("entity does not exist");
                }
                break;
            case 2:
                List<Student> students = studentDao.findAll();
                viewProvider.printList(viewProvider.provideView(students));
                break;
            default:
                viewProvider.print("wrong number");
        }

    }

}
