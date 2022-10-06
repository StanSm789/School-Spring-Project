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
public class Student extends User {
    private final int groupId;
    private final String studyModeTitle;
    private final String studyModeDescription;

}
