package com.smirnov.springschooldatabase.view;

import com.smirnov.springschooldatabase.domain.Course;
import com.smirnov.springschooldatabase.view.impl.CourseViewProviderImpl;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

class CourseViewProviderImplTest {
    private final CourseViewProviderImpl viewProvider = new CourseViewProviderImpl();

    @Test
    void provideViewShouldDisplayInformationAboutMathCourse() {
        Course expectedCourse = Course.builder()
                .withId(1)
                .withName("Mathematics")
                .withDescription("Math studies for students")
                .build();
        String expectedCourseView = "Course ID: 1, Course Name: Mathematics, " +
                "Course Description: Math studies for students, " +
                "Course Teachers: null";

        String actualCourseView = viewProvider.provideView(expectedCourse);
        assertThat(actualCourseView, is(equalTo(expectedCourseView)));
    }

    @Test
    void provideViewShouldDisplayInformationAboutAllCourses() {
        Course firstCourse = Course.builder()
                .withId(1)
                .withName("Mathematics")
                .withDescription("Math studies for students")
                .build();
        Course secondCourse = Course.builder()
                .withId(2)
                .withName("English")
                .withDescription("English studies for students")
                .build();
        List<Course> expectedCourses = Arrays.asList(firstCourse, secondCourse);
        String firstCourseView = "Course ID: 1, Course Name: Mathematics, " +
                "Course Description: Math studies for students, " +
                "Course Teachers: null\n";
        String secondCourseView = "Course ID: 2, Course Name: English, " +
                "Course Description: English studies for students, " +
                "Course Teachers: null\n";
        List<String> expectedCoursesView = Arrays.asList(firstCourseView, secondCourseView);

        List<String> actualCoursesView = viewProvider.provideView(expectedCourses);

        assertThat(actualCoursesView, is(equalTo(expectedCoursesView)));
    }

}
