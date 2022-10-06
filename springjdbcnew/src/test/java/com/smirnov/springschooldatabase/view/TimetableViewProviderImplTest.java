package com.smirnov.springschooldatabase.view;

import com.smirnov.springschooldatabase.domain.Teacher;
import com.smirnov.springschooldatabase.domain.Timetable;
import com.smirnov.springschooldatabase.view.impl.TeacherViewProviderImpl;
import com.smirnov.springschooldatabase.view.impl.TimetableViewProviderImpl;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

class TimetableViewProviderImplTest {

    private final TimetableViewProviderImpl viewProvider = new TimetableViewProviderImpl();

    @Test
    void provideViewShouldDisplayInformationAboutTimetableWhereIdIs1() {

        Timetable expectedTimetable = Timetable.builder()
                .withId(1)
                .withDescription("some description")
                .build();
        String expectedTimetableView = "Timetable ID: 1, Timetable Description: some description, Full Timetable: null";
        String actualTimetableView = viewProvider.provideView(expectedTimetable);

        assertThat(actualTimetableView, is(equalTo(expectedTimetableView)));
    }

    @Test
    void provideViewShouldDisplayInformationAboutAllTimetables() {

        Timetable firstTimetable = Timetable.builder()
                .withId(1)
                .withDescription("some description")
                .build();
        Timetable secondTimetable = Timetable.builder()
                .withId(2)
                .withDescription("some description")
                .build();
        List<Timetable> expectedTimetables = Arrays.asList(firstTimetable, secondTimetable);

        String firstString = "Timetable ID: 1, Timetable Description: some description, Full Timetable: null\n";
        String secondString = "Timetable ID: 2, Timetable Description: some description, Full Timetable: null\n";
        List<String> expectedTimetablesView = Arrays.asList(firstString, secondString);
        List<String> actualTimetablesView = viewProvider.provideView(expectedTimetables);

        assertThat(actualTimetablesView, is(equalTo(expectedTimetablesView)));
    }

}
