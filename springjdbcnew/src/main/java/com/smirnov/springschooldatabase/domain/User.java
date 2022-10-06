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
public abstract class User {
    private final int id;
    private final String firstName;
    private final String lastName;

}
