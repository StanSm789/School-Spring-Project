package com.smirnov.springschooldatabase.view.impl;

import com.smirnov.springschooldatabase.domain.Course;
import com.smirnov.springschooldatabase.view.CourseViewProvider;
import java.util.ArrayList;
import java.util.List;

public class CourseViewProviderImpl extends AbstractViewProvider<Course> implements CourseViewProvider {

    @Override
    protected String makeView(Course course) {

        return assembleString(course);
    }

    @Override
    protected List<String> makeView(List<Course> lists) {

        List<String> result = new ArrayList<>();

        for (Course course : lists) {
            result.add(assembleString(course) + "\n");
        }

        return result;
    }

    @Override
    protected String assembleString(Course course) {

        return String.format("Course ID: %d, Course Name: %s, Course Description: %s, Course Teachers: %s",
                course.getId(), course.getName(), course.getDescription(),
                course.getTeachers());
    }

}
