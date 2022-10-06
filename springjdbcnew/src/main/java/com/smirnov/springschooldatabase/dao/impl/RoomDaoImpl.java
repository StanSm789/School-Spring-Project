package com.smirnov.springschooldatabase.dao.impl;

import com.smirnov.springschooldatabase.dao.RoomDao;
import com.smirnov.springschooldatabase.dao.mappers.RoomMapper;
import com.smirnov.springschooldatabase.domain.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import java.util.List;

@Component
@Transactional
public class RoomDaoImpl extends AbstractDao<Room> implements RoomDao {
    public static final String FIND_BY_ID_QUERY = "SELECT * FROM ROOM WHERE ROOM_ID = ?;";
    public static final String FIND_ALL_QUERY = "SELECT * FROM ROOM;";
    public static final String FIND_ALL_QUERY_WITH_LIMIT =
            "SELECT * FROM ROOM ORDER BY ROOM_ID OFFSET ? ROWS FETCH NEXT ? ROWS ONLY;";
    public static final String SAVE_QUERY = "INSERT INTO ROOM (CAMPUS, ADDRESS, ROOM_NUMBER) VALUES(?, ?, ?);";
    public static final String UPDATE_QUERY = "UPDATE ROOM SET ROOM_ID = ?, CAMPUS = ?, ADDRESS = ?, ROOM_NUMBER = ? WHERE ROOM_ID = ?;";
    public static final String DELETE_BY_ID_QUERY = "DELETE FROM ROOM WHERE ROOM_ID = ?;";
    protected RoomMapper roomMapper;

    @Autowired
    public RoomDaoImpl(JdbcTemplate jdbcTemplate, TransactionTemplate transactionTemplate,
                       RoomMapper roomMapper) {
        super(jdbcTemplate, transactionTemplate, FIND_BY_ID_QUERY, FIND_ALL_QUERY,
                SAVE_QUERY, UPDATE_QUERY, DELETE_BY_ID_QUERY);
        this.roomMapper = roomMapper;
    }

    @Override
    protected Room findByIntParam(String sql, Integer id) {

        return jdbcTemplate.query(FIND_BY_ID_QUERY,
                new Object[]{id}, roomMapper)
                .stream().findAny().orElse(null);
    }

    @Override
    protected List<Room> findAllEntities(String sql) {

        return jdbcTemplate.query(FIND_ALL_QUERY, roomMapper);
    }

    @Override
    protected List<Room> findAllEntitiesWithinScope(String sql, int leftScope, int rightScope) {

        return jdbcTemplate.query(FIND_ALL_QUERY_WITH_LIMIT,
                new Object[] { leftScope, rightScope }, roomMapper);
    }

    @Override
    protected void insert(String sql, Room room) {

        jdbcTemplate.update(SAVE_QUERY, room.getCampus(), room.getAddress(), room.getRoomNumber());
    }

    @Override
    protected void updateValue(String sql, Integer id, Room room) {

        jdbcTemplate.update(UPDATE_QUERY, room.getId(), room.getCampus(), room.getAddress(), room.getRoomNumber(), id);
    }

    @Override
    protected void deleteValue(String sql, Integer id) {

        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                try {
                    jdbcTemplate.update("update lesson set ROOM_ID = null where ROOM_ID = ?", id);
                    jdbcTemplate.update(DELETE_BY_ID_QUERY, id);
                } catch (Exception exception) {
                    status.setRollbackOnly();
                }
        }
        });
    }

}
