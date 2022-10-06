package com.smirnov.springschooldatabase.view.impl;

import com.smirnov.springschooldatabase.domain.Group;
import com.smirnov.springschooldatabase.view.GroupViewProvider;
import java.util.ArrayList;
import java.util.List;

public class GroupViewProviderImpl extends AbstractViewProvider<Group> implements GroupViewProvider {

    @Override
    protected String makeView(Group group) {

        return assembleString(group);
    }

    @Override
    protected List<String> makeView(List<Group> lists) {
        List<String> result = new ArrayList<>();

        for (Group group : lists) {
            result.add(assembleString(group) + "\n");
        }

        return result;
    }

    @Override
    protected String assembleString(Group group) {

        return String.format("Group ID: %d, Group Name: %s, Group Courses: %s, Course Students: %s",
                group.getId(), group.getName(), group.getCourses(), group.getStudents());
    }

}
