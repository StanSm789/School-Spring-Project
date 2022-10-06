package com.smirnov.springschooldatabase.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.Calendar;

@Getter
@Setter
@Builder(setterPrefix = "with")
@ToString
@EqualsAndHashCode
public class Lesson {
    private final int id;
    private Group group;
    private Room room;
    private Teacher teacher;
    private Course course;
    private Timetable timetable;
    private Calendar calendar;

}
