package com.smirnov.springschooldatabase.controller;

import lombok.AllArgsConstructor;
import java.util.Scanner;

@AllArgsConstructor
public class FrontController {
    private final CourseFrontController courseFrontController;
    private final GroupFrontController groupFrontController;
    private final LessonFrontController lessonFrontController;
    private final RoomFrontController roomFrontController;
    private final StudentFrontController studentFrontController;
    private final TeacherFrontController teacherFrontController;
    private final TimetableFrontController timetableFrontController;

    public void begin() {
        String inputRequest = "Course: 1\nGroup: 2\nLesson: 3\nRoom: 4\n" +
                "Student: 5\nTeacher: 6\nTimetable: 7";
        System.out.println(inputRequest);

        Scanner scanner = new Scanner(System.in);
        int choose = scanner.nextInt();

        switch (choose) {
            case 1:
                courseFrontController.startMenu();
                break;
            case 2:
                groupFrontController.startMenu();
                break;
            case 3:
                lessonFrontController.startMenu();
                break;
            case 4:
                roomFrontController.startMenu();
                break;
            case 5:
                studentFrontController.startMenu();
                break;
            case 6:
                teacherFrontController.startMenu();
                break;
            case 7:
                timetableFrontController.startMenu();
                break;
            default:
                System.out.println("wrong number");
        }

    }

}
