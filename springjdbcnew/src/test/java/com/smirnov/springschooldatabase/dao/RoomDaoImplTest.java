package com.smirnov.springschooldatabase.dao;

import com.smirnov.springschooldatabase.dao.impl.RoomDaoImpl;
import com.smirnov.springschooldatabase.dao.mappers.RoomMapper;
import com.smirnov.springschooldatabase.domain.Room;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Arrays;
import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

class RoomDaoImplTest extends DaoTest {

    private final RoomDao roomDao = new RoomDaoImpl(jdbcTemplate(), new TransactionTemplate(transactionManager()),
            new RoomMapper());

    @Test
    void findByIdShouldReturnRoomWhenRoomIdIs1() {

        Room expectedRoom = Room.builder().withId(1).withCampus("Nathan")
                .withAddress("Kessel Road").withRoomNumber(10).build();
        Room actualRoom= roomDao.findById(1).get();

        assertThat(expectedRoom, is(equalTo(actualRoom)));
    }

    @Test
    void findAllShouldFindAllRooms() {

        Room expectedRoom = Room.builder().withId(1).withCampus("Nathan")
                .withAddress("Kessel Road").withRoomNumber(10).build();
        List<Room> expectedRooms = Arrays.asList(expectedRoom);
        List<Room> actualRooms = roomDao.findAll();

        assertThat(expectedRooms, is(equalTo(actualRooms)));
    }

    @Test
    void findAllShouldFindAllRoomsWithPagination() {

        Room expectedRoom = Room.builder().withId(1).withCampus("Nathan")
                .withAddress("Kessel Road").withRoomNumber(10).build();
        List<Room> expectedRooms = Arrays.asList(expectedRoom);
        List<Room> actualRooms = roomDao.findAll(0, 1);

        assertThat(expectedRooms, is(equalTo(actualRooms)));
    }

    @Test
    void saveShouldSaveRoom() {

        Room expectedRoom = Room.builder().withId(2).withCampus("Nathan")
                .withAddress("Kessel Road").withRoomNumber(100).build();
        roomDao.save(expectedRoom);
        Room actualRoom = roomDao.findById(2).get();

        assertThat(expectedRoom, is(equalTo(actualRoom)));
    }

    @Test
    void updateShouldUpdateRoom() {

        Room expectedRoom = Room.builder().withId(1).withCampus("Nathan")
                .withAddress("Kessel Road").withRoomNumber(125).build();

        roomDao.update(1, expectedRoom);
        Room actualRoom = roomDao.findById(1).get();

        assertThat(expectedRoom, is(equalTo(actualRoom)));
    }

    @Test
    void deleteByIdShouldDeleteRoomWhereRoomIdIs1() {

        roomDao.deleteById(1);
        Boolean actualResult = roomDao.findById(1).isPresent();

        assertThat(false, is(equalTo(actualResult)));
    }

}
