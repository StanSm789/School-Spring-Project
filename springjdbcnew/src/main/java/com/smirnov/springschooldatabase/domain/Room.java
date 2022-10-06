package com.smirnov.springschooldatabase.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder(setterPrefix = "with")
@ToString
@EqualsAndHashCode
public class Room {
    private final int id;
    private final String campus;
    private final String address;
    private final int roomNumber;

}
