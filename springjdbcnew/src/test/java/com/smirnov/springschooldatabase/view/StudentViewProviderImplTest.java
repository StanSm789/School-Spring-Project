package com.smirnov.springschooldatabase.view;

import com.smirnov.springschooldatabase.domain.Student;
import com.smirnov.springschooldatabase.view.impl.StudentViewProviderImpl;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

class StudentViewProviderImplTest {
    private final StudentViewProviderImpl viewProvider = new StudentViewProviderImpl();

    @Test
    void provideViewShouldDisplayInformationAboutStudentWhereIdIs1() {
        Student expectedStudent = Student.builder()
                .withId(1)
                .withFirstName("James")
                .withLastName("Jones")
                .withStudyModeTitle("full time")
                .withStudyModeDescription("full time description")
                .build();
        String expectedStudentView = "Student ID: 1, Student First Name: James, Student Last Name: Jones, " +
                "Group ID: 0, Degree Title: full time, Degree Description: full time description";
        String actualStudentView = viewProvider.provideView(expectedStudent);

        assertThat(actualStudentView, is(equalTo(expectedStudentView)));
    }

    @Test
    void provideViewShouldDisplayInformationAboutAllStudents() {
        Student firstStudent = Student.builder()
                .withId(1)
                .withFirstName("James")
                .withLastName("Jones")
                .withStudyModeTitle("full time")
                .withStudyModeDescription("full time description")
                .build();
        Student secondStudent = Student.builder()
                .withId(2)
                .withFirstName("Stanley")
                .withLastName("Smith")
                .withStudyModeTitle("full time")
                .withStudyModeDescription("full time description")
                .build();
        List<Student> expectedStudents = Arrays.asList(firstStudent, secondStudent);
        String firstStudentView = "Student ID: 1, Student First Name: James, Student Last Name: Jones, " +
                "Group ID: 0, Degree Title: full time, Degree Description: full time description\n";
        String secondStudentView = "Student ID: 2, Student First Name: Stanley, Student Last Name: Smith, " +
                "Group ID: 0, Degree Title: full time, Degree Description: full time description\n";
        List<String> expectedStudentsView = Arrays.asList(firstStudentView, secondStudentView);
        List<String> actualStudentsView = viewProvider.provideView(expectedStudents);

        assertThat(actualStudentsView, is(equalTo(expectedStudentsView)));
    }

}
