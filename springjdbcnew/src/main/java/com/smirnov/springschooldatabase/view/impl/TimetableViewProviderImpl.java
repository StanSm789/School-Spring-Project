package com.smirnov.springschooldatabase.view.impl;

import com.smirnov.springschooldatabase.domain.Timetable;
import com.smirnov.springschooldatabase.view.TimetableViewProvider;
import java.util.ArrayList;
import java.util.List;

public class TimetableViewProviderImpl extends AbstractViewProvider<Timetable> implements TimetableViewProvider {

    @Override
    protected String makeView(Timetable timetable) {

        return assembleString(timetable);
    }

    @Override
    protected List<String> makeView(List<Timetable> lists) {
        List<String> result = new ArrayList<>();

        for (Timetable timetable : lists) {
            result.add(assembleString(timetable) + "\n");
        }

        return result;
    }

    @Override
    protected String assembleString(Timetable timetable) {

        return String.format("Timetable ID: %d, Timetable Description: %s, Full Timetable: %s",
                timetable.getId(), timetable.getDescription(), timetable.getTimetable());
    }

}
