package com.smirnov.springschooldatabase.controller;

import com.smirnov.springschooldatabase.dao.CourseDao;
import com.smirnov.springschooldatabase.domain.Course;
import com.smirnov.springschooldatabase.view.CourseViewProvider;
import lombok.AllArgsConstructor;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class CourseFrontController {
    private final CourseDao courseDao;
    private final CourseViewProvider viewProvider;

    public void startMenu() {
        viewProvider.print("Press 1 to find a course by id; \n" +
                "Press 2 find all courses");
        int choose = viewProvider.readInt();

        switch (choose) {
            case 1:
                viewProvider.print("Input id");
                int id = viewProvider.readInt();
                Optional<Course> value = courseDao.findById(id);

                if(value.isPresent()) {
                    Course course = value.get();
                    viewProvider.print(viewProvider.provideView(course));
                } else {
                    viewProvider.print("entity does not exist");
                }
                break;
            case 2:
                List<Course> courses = courseDao.findAll();
                viewProvider.printList(viewProvider.provideView(courses));
                break;
            default:
                viewProvider.print("wrong number");
        }

    }

}
