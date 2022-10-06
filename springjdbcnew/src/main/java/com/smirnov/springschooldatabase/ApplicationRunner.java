package com.smirnov.springschooldatabase;

import com.smirnov.springschooldatabase.config.SpringJdbcConfig;
import com.smirnov.springschooldatabase.controller.CourseFrontController;
import com.smirnov.springschooldatabase.controller.FrontController;
import com.smirnov.springschooldatabase.controller.GroupFrontController;
import com.smirnov.springschooldatabase.controller.LessonFrontController;
import com.smirnov.springschooldatabase.controller.RoomFrontController;
import com.smirnov.springschooldatabase.controller.StudentFrontController;
import com.smirnov.springschooldatabase.controller.TeacherFrontController;
import com.smirnov.springschooldatabase.controller.TimetableFrontController;
import com.smirnov.springschooldatabase.dao.impl.CourseDaoImpl;
import com.smirnov.springschooldatabase.dao.impl.GroupDaoImpl;
import com.smirnov.springschooldatabase.dao.impl.LessonDaoImpl;
import com.smirnov.springschooldatabase.dao.impl.RoomDaoImpl;
import com.smirnov.springschooldatabase.dao.impl.StudentDaoImpl;
import com.smirnov.springschooldatabase.dao.impl.TeacherDaoImpl;
import com.smirnov.springschooldatabase.dao.impl.TimetableDaoImpl;
import com.smirnov.springschooldatabase.view.CourseViewProvider;
import com.smirnov.springschooldatabase.view.GroupViewProvider;
import com.smirnov.springschooldatabase.view.LessonViewProvider;
import com.smirnov.springschooldatabase.view.RoomViewProvider;
import com.smirnov.springschooldatabase.view.StudentViewProvider;
import com.smirnov.springschooldatabase.view.TeacherViewProvider;
import com.smirnov.springschooldatabase.view.TimetableViewProvider;
import com.smirnov.springschooldatabase.view.impl.CourseViewProviderImpl;
import com.smirnov.springschooldatabase.view.impl.GroupViewProviderImpl;
import com.smirnov.springschooldatabase.view.impl.LessonViewProviderImpl;
import com.smirnov.springschooldatabase.view.impl.RoomViewProviderImpl;
import com.smirnov.springschooldatabase.view.impl.StudentViewProviderImpl;
import com.smirnov.springschooldatabase.view.impl.TeacherViewProviderImpl;
import com.smirnov.springschooldatabase.view.impl.TimetableViewProviderImpl;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
public class ApplicationRunner {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringJdbcConfig.class);

        GroupDaoImpl groupDao = context.getBean("groupDaoImpl", GroupDaoImpl.class);
        RoomDaoImpl roomDao = context.getBean("roomDaoImpl", RoomDaoImpl.class);
        CourseDaoImpl courseDao = context.getBean("courseDaoImpl", CourseDaoImpl.class);
        TimetableDaoImpl timetableDao = context.getBean("timetableDaoImpl", TimetableDaoImpl.class);
        StudentDaoImpl studentDao = context.getBean("studentDaoImpl", StudentDaoImpl.class);
        TeacherDaoImpl teacherDao = context.getBean("teacherDaoImpl", TeacherDaoImpl.class);
        LessonDaoImpl lessonDao = context.getBean("lessonDaoImpl", LessonDaoImpl.class);

        CourseViewProvider courseViewProvider = new CourseViewProviderImpl();
        GroupViewProvider groupViewProvider = new GroupViewProviderImpl();
        LessonViewProvider lessonViewProvider = new LessonViewProviderImpl();
        RoomViewProvider roomViewProvider = new RoomViewProviderImpl();
        StudentViewProvider studentViewProvider = new StudentViewProviderImpl();
        TeacherViewProvider teacherViewProvider = new TeacherViewProviderImpl();
        TimetableViewProvider timetableViewProvider = new TimetableViewProviderImpl();

        new FrontController(new CourseFrontController(courseDao, courseViewProvider),
                new GroupFrontController(groupDao, groupViewProvider),
                new LessonFrontController(lessonDao, lessonViewProvider),
                new RoomFrontController(roomDao, roomViewProvider),
                new StudentFrontController(studentDao, studentViewProvider),
                new TeacherFrontController(teacherDao, teacherViewProvider),
                new TimetableFrontController(timetableDao, timetableViewProvider)).begin();

        context.close();
    }

}
