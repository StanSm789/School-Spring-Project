package com.smirnov.springschooldatabase.view;

import com.smirnov.springschooldatabase.domain.Lesson;
import com.smirnov.springschooldatabase.domain.Room;
import com.smirnov.springschooldatabase.view.impl.LessonViewProviderImpl;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

class LessonViewProviderImplTest {
    private final LessonViewProvider viewProvider = new LessonViewProviderImpl();

    @Test
    void provideViewShouldDisplayInformationAboutLessonWhereIdIs1() {

        Room expectedRoom = Room.builder()
                .withId(1)
                .withCampus("Some campus")
                .withAddress("Some address")
                .withRoomNumber(10)
                .build();

        Lesson expectedLesson = Lesson.builder()
                .withId(1)
                .withRoom(expectedRoom)
                .build();
        String expectedLessonView = "Lesson ID: 1, Group: null, Room: Room(id=1, campus=Some campus, " +
                "address=Some address, roomNumber=10), Teacher: null, Course: null, Timetable: null, Calendar: null";
        String actualLessonView = viewProvider.provideView(expectedLesson);

        assertThat(actualLessonView, is(equalTo(expectedLessonView)));
    }

    @Test
    void provideViewShouldDisplayInformationAboutAllStudents() {

        Room expectedRoom = Room.builder()
                .withId(1)
                .withCampus("Some campus")
                .withAddress("Some address")
                .withRoomNumber(10)
                .build();

        Lesson firstLesson = Lesson.builder()
                .withId(1)
                .withRoom(expectedRoom)
                .build();
        Lesson secondLesson = Lesson.builder()
                .withId(2)
                .withRoom(expectedRoom)
                .build();
        List<Lesson> expectedLessons = Arrays.asList(firstLesson, secondLesson);

        String firstString = "Lesson ID: 1, Group: null, Room: Room(id=1, campus=Some campus, " +
                "address=Some address, roomNumber=10), Teacher: null, Course: null, Timetable: null, Calendar: null\n";
        String secondString = "Lesson ID: 2, Group: null, Room: Room(id=1, campus=Some campus, " +
                "address=Some address, roomNumber=10), Teacher: null, Course: null, Timetable: null, Calendar: null\n";
        List<String> expectedLessonView = Arrays.asList(firstString, secondString);
        List<String> actualLessonView = viewProvider.provideView(expectedLessons);

        assertThat(actualLessonView, is(equalTo(expectedLessonView)));
    }

}
