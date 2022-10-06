package com.smirnov.springschooldatabase.controller;

import com.smirnov.springschooldatabase.dao.LessonDao;
import com.smirnov.springschooldatabase.domain.Lesson;
import com.smirnov.springschooldatabase.view.LessonViewProvider;
import lombok.AllArgsConstructor;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class LessonFrontController {
    private final LessonDao lessonDao;
    private final LessonViewProvider viewProvider;

    public void startMenu() {
        viewProvider.print("Press 1 to find a lesson by id; \n" +
                "Press 2 find all lessons");
        int choose = viewProvider.readInt();

        switch (choose) {
            case 1:
                viewProvider.print("Input id");
                int id = viewProvider.readInt();
                Optional<Lesson> value = lessonDao.findById(id);

                if(value.isPresent()) {
                    Lesson lesson = value.get();
                    viewProvider.print(viewProvider.provideView(lesson));
                } else {
                    viewProvider.print("entity does not exist");
                }
                break;
            case 2:
                List<Lesson> lessons = lessonDao.findAll();
                viewProvider.printList(viewProvider.provideView(lessons));
                break;
            default:
                viewProvider.print("wrong number");
        }
    }

}
