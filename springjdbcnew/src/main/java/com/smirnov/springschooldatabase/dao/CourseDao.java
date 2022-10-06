package com.smirnov.springschooldatabase.dao;

import com.smirnov.springschooldatabase.domain.Course;
import com.smirnov.springschooldatabase.domain.Teacher;

public interface CourseDao extends CrudDao<Integer, Course> {

    void addTeacherToCourse(int courseId, Teacher teacher);
    void removeTeacherFromCourse(int courseId, Teacher teacher);
    void removeTeachersFromCourse(int courseId);

}
