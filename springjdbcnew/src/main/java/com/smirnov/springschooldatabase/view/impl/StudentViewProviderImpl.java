package com.smirnov.springschooldatabase.view.impl;

import com.smirnov.springschooldatabase.domain.Student;
import com.smirnov.springschooldatabase.view.StudentViewProvider;
import java.util.ArrayList;
import java.util.List;

public class StudentViewProviderImpl extends AbstractViewProvider<Student> implements StudentViewProvider {

    @Override
    protected String makeView(Student student) {

        return assembleString(student);
    }

    @Override
    protected List<String> makeView(List<Student> lists) {

        List<String> result = new ArrayList<>();

        for (Student student : lists) {
            result.add(assembleString(student) + "\n");
        }

        return result;
    }

    @Override
    protected String assembleString(Student student) {

        return String.format("Student ID: %d, Student First Name: %s, Student Last Name: %s, " +
                        "Group ID: %d, Degree Title: %s, Degree Description: %s",
                student.getId(), student.getFirstName(), student.getLastName(), student.getGroupId(),
                student.getStudyModeTitle(), student.getStudyModeDescription());
    }

}
