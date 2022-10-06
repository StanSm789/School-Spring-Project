package com.smirnov.springschooldatabase.dao;

import com.smirnov.springschooldatabase.domain.Group;
import com.smirnov.springschooldatabase.domain.Student;

public interface StudentDao extends CrudDao<Integer, Student> {

    void addGroupToStudent(Integer studentId, Group group);
}
