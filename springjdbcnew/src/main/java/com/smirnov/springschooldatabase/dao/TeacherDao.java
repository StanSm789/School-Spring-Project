package com.smirnov.springschooldatabase.dao;

import com.smirnov.springschooldatabase.domain.Course;
import com.smirnov.springschooldatabase.domain.Teacher;
import java.util.List;

public interface TeacherDao extends CrudDao<Integer, Teacher> {

    List<Course> findTeachingCourses(Integer teacherId);

}
