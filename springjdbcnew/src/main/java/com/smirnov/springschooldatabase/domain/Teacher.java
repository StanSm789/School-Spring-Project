package com.smirnov.springschooldatabase.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder(setterPrefix = "with")
@ToString
@EqualsAndHashCode
public class Teacher extends User {
    private final String degreeTitle;
    private final String degreeDescription;
    private final String socialMedia;

}
