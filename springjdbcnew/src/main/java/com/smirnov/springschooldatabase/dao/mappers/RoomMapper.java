package com.smirnov.springschooldatabase.dao.mappers;

import com.smirnov.springschooldatabase.domain.Room;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RoomMapper implements RowMapper<Room> {

    @Override
    public Room mapRow(ResultSet resultSet, int i) throws SQLException {

        return Room.builder().withId(resultSet.getInt("ROOM_ID"))
                .withCampus(resultSet.getString("CAMPUS"))
                .withAddress(resultSet.getString("ADDRESS"))
                .withRoomNumber(resultSet.getInt("ROOM_NUMBER"))
                .build();
    }

}
