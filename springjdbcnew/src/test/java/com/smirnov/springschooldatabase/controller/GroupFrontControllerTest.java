package com.smirnov.springschooldatabase.controller;

import com.smirnov.springschooldatabase.dao.impl.GroupDaoImpl;
import com.smirnov.springschooldatabase.domain.Group;
import com.smirnov.springschooldatabase.view.GroupViewProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class GroupFrontControllerTest {

    @Mock
    GroupViewProvider viewProvider;

    @Mock
    GroupDaoImpl groupDao;

    @InjectMocks
    GroupFrontController controller;

    @Test
    void startMenuShouldShowGroupWhereIdIs1() {

        Group expectedGroup = Group.builder()
                .withId(1)
                .withName("ff-22")
                .build();
        String expectedString = "Group ID: 1, Group Name: ff-22, Group Courses: null, Course Students: null";

        when(viewProvider.readInt()).thenReturn(1);
        when(groupDao.findById(1)).thenReturn(Optional.of(expectedGroup));
        when(viewProvider.provideView(expectedGroup)).thenReturn(expectedString);
        controller.startMenu();
        verify(groupDao).findById(1);
        verify(viewProvider).provideView(groupDao.findById(1).get());
    }

    @Test
    void startMenuShouldShowAllGroups() {

        Group firstGroup = Group.builder()
                .withId(1)
                .withName("ff-22")
                .build();
        Group secondGroup = Group.builder()
                .withId(2)
                .withName("mm-99")
                .build();
        List<Group> expectedGroups = Arrays.asList(firstGroup, secondGroup);

        String firstString = "Group ID: 1, Group Name: ff-22, Group Courses: null, Course Students: null\n";
        String secondString = "Group ID: 2, Group Name: mm-99, Group Courses: null, Course Students: null\n";
        List<String> expectedStrings = Arrays.asList(firstString, secondString);

        when(viewProvider.readInt()).thenReturn(2);
        when(groupDao.findAll()).thenReturn(expectedGroups);
        when(viewProvider.provideView(expectedGroups)).thenReturn(expectedStrings);
        controller.startMenu();
        verify(viewProvider).provideView(groupDao.findAll());
    }

    @Test
    void printShouldPrintInformationOnTheConsoleWhenInputNumberIsNotEqual1or2() {
        Object mock = Mockito.mock(Object.class);
        when(viewProvider.readInt()).thenReturn(3);
        when(mock.toString()).thenReturn("wrong number");
        controller.startMenu();

        verify(viewProvider).print("wrong number");
    }

    @Test
    void printShouldIndicateThatEntityDoesNotExistWhenOptionalIsEmpty() {
        Object mock = Mockito.mock(Object.class);
        when(viewProvider.readInt()).thenReturn(1);
        when(groupDao.findById(1)).thenReturn(Optional.empty());
        when(mock.toString()).thenReturn("entity does not exist");
        controller.startMenu();

        verify(viewProvider).print("entity does not exist");
    }

}
