package com.smirnov.springschooldatabase.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.List;

@Getter
@Setter
@Builder(setterPrefix = "with")
@ToString
@EqualsAndHashCode
public class Group {
    private final int id;
    private final String name;
    private List<Course> courses;
    private List<Student> students;

}
