package com.smirnov.springschooldatabase.view;

import com.smirnov.springschooldatabase.domain.Teacher;
import com.smirnov.springschooldatabase.view.impl.TeacherViewProviderImpl;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

class TeacherViewProviderImplTest {

    private final TeacherViewProviderImpl viewProvider = new TeacherViewProviderImpl();

    @Test
    void provideViewShouldDisplayInformationAboutTeacherWhereIdIs1() {

        Teacher expectedTeacher = Teacher.builder()
                .withId(1)
                .withFirstName("James")
                .withLastName("Smith")
                .withDegreeTitle("PhD")
                .withDegreeDescription("some description")
                .withSocialMedia("linkedIn")
                .build();
        String expectedTeacherView = "Teacher ID: 1, First Name: James, Last Name: Smith, " +
                "Degree Title: PhD, Degree Description: some description, Social Media: linkedIn";
        String actualTeacherView = viewProvider.provideView(expectedTeacher);

        assertThat(actualTeacherView, is(equalTo(expectedTeacherView)));
    }

    @Test
    void provideViewShouldDisplayInformationAboutAllTeachers() {

        Teacher firstTeacher = Teacher.builder()
                .withId(1)
                .withFirstName("James")
                .withLastName("Smith")
                .withDegreeTitle("PhD")
                .withDegreeDescription("some description")
                .withSocialMedia("linkedIn")
                .build();
        Teacher secondTeacher = Teacher.builder()
                .withId(2)
                .withFirstName("Alex")
                .withLastName("Cronin")
                .withDegreeTitle("PhD")
                .withDegreeDescription("some description")
                .withSocialMedia("linkedIn")
                .build();
        List<Teacher> expectedTeachers = Arrays.asList(firstTeacher, secondTeacher);

        String firstString = "Teacher ID: 1, First Name: James, Last Name: Smith, " +
                "Degree Title: PhD, Degree Description: some description, Social Media: linkedIn\n";
        String secondString = "Teacher ID: 2, First Name: Alex, Last Name: Cronin, " +
                "Degree Title: PhD, Degree Description: some description, Social Media: linkedIn\n";
        List<String> expectedTeachersView = Arrays.asList(firstString, secondString);
        List<String> actualTeachersView = viewProvider.provideView(expectedTeachers);

        assertThat(actualTeachersView, is(equalTo(expectedTeachersView)));
    }

}
