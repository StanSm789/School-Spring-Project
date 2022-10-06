package com.smirnov.springschooldatabase.view.impl;

import com.smirnov.springschooldatabase.domain.Lesson;
import com.smirnov.springschooldatabase.view.LessonViewProvider;
import java.util.ArrayList;
import java.util.List;

public class LessonViewProviderImpl extends AbstractViewProvider<Lesson> implements LessonViewProvider {

    @Override
    protected String makeView(Lesson lesson) {

        return assembleString(lesson);
    }

    @Override
    protected List<String> makeView(List<Lesson> lists) {

        List<String> result = new ArrayList<>();

        for (Lesson lesson : lists) {
            result.add(assembleString(lesson) + "\n");
        }

        return result;
    }

    @Override
    protected String assembleString(Lesson lesson) {

        return String.format("Lesson ID: %d, Group: %s, Room: %s, " +
                        "Teacher: %s, Course: %s, Timetable: %s, Calendar: %s",
                lesson.getId(), lesson.getGroup(), lesson.getRoom(),
                lesson.getTeacher(), lesson.getCourse(), lesson.getTimetable(), lesson.getCalendar());
    }

}
