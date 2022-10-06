package com.smirnov.springschooldatabase.view.impl;

import com.smirnov.springschooldatabase.domain.Teacher;
import com.smirnov.springschooldatabase.view.TeacherViewProvider;
import java.util.ArrayList;
import java.util.List;

public class TeacherViewProviderImpl extends AbstractViewProvider<Teacher> implements TeacherViewProvider {

    @Override
    protected String makeView(Teacher teacher) {

        return assembleString(teacher);
    }

    @Override
    protected List<String> makeView(List<Teacher> lists) {

        List<String> result = new ArrayList<>();

        for (Teacher teacher : lists) {
            result.add(assembleString(teacher) + "\n");
        }

        return result;
    }

    @Override
    protected String assembleString(Teacher teacher) {

        return String.format("Teacher ID: %d, First Name: %s, Last Name: %s, " +
                        "Degree Title: %s, Degree Description: %s, Social Media: %s",
                teacher.getId(), teacher.getFirstName(), teacher.getLastName(),
                teacher.getDegreeTitle(), teacher.getDegreeDescription(), teacher.getSocialMedia());
    }

}
