package com.smirnov.springschooldatabase.dao;

import com.smirnov.springschooldatabase.domain.Course;
import com.smirnov.springschooldatabase.domain.Group;

public interface GroupDao extends CrudDao<Integer, Group> {

    void addCourseToGroup(int groupId, Course course);

}
