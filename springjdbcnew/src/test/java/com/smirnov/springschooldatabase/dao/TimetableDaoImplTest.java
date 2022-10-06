package com.smirnov.springschooldatabase.dao;

import com.smirnov.springschooldatabase.dao.impl.TimetableDaoImpl;
import com.smirnov.springschooldatabase.dao.mappers.LessonMapper;
import com.smirnov.springschooldatabase.dao.mappers.TimetableMapper;
import com.smirnov.springschooldatabase.domain.Timetable;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

class TimetableDaoImplTest extends DaoTest {

    private final TimetableDao timetableDao = new TimetableDaoImpl(jdbcTemplate(), new TransactionTemplate(transactionManager()),
            new TimetableMapper(), new LessonMapper(jdbcTemplate()));

    @Test
    void findByIdShouldReturnTimetableWhenTimetableIdIs1() {

        Timetable expectedTimetable = Timetable.builder().withId(2).withDescription("some more description")
                .withTimetable(Collections.emptyList()).build();
        Timetable actualTimetable= timetableDao.findById(2).get();

        assertThat(expectedTimetable, is(equalTo(actualTimetable)));
    }

    @Test
    void findAllShouldFindAllTimetables() {

        Timetable expectedTimetable = Timetable.builder().withId(2).withDescription("some more description")
                .withTimetable(Collections.emptyList()).build();
        List<Timetable> expectedTimetables = Arrays.asList(expectedTimetable);
        timetableDao.deleteById(1);
        List<Timetable> actualTimetables = timetableDao.findAll();

        assertThat(expectedTimetables, is(equalTo(actualTimetables)));
    }

    @Test
    void findAllShouldFindAllTimetablesWithPagination() {

        Timetable expectedTimetable = Timetable.builder().withId(2).withDescription("some more description")
                .withTimetable(Collections.emptyList()).build();
        List<Timetable> expectedTimetables = Arrays.asList(expectedTimetable);
        timetableDao.deleteById(1);
        List<Timetable> actualTimetables = timetableDao.findAll(0, 1);

        assertThat(expectedTimetables, is(equalTo(actualTimetables)));
    }

    @Test
    void saveShouldSaveTimetable() {

        Timetable expectedTimetable = Timetable.builder().withId(3).withDescription("some other description")
                .withTimetable(Collections.emptyList()).build();
        timetableDao.save(expectedTimetable);
        Timetable actualTimetable = timetableDao.findById(3).get();

        assertThat(expectedTimetable, is(equalTo(actualTimetable)));
    }

    @Test
    void updateShouldUpdateTimetable() {

        Timetable expectedTimetable = Timetable.builder().withId(2).withDescription("some other description")
                .withTimetable(Collections.emptyList()).build();
        timetableDao.update(2, expectedTimetable);
        Timetable actualTimetable = timetableDao.findById(2).get();

        assertThat(expectedTimetable, is(equalTo(actualTimetable)));
    }

    @Test
    void deleteByIdShouldDeleteTimetableWhereTimetableIdIs1() {

        timetableDao.deleteById(2);
        Boolean actualResult = timetableDao.findById(2).isPresent();

        assertThat(false, is(equalTo(actualResult)));
    }

}
