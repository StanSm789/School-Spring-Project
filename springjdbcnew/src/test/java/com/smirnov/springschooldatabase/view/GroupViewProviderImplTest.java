package com.smirnov.springschooldatabase.view;

import com.smirnov.springschooldatabase.domain.Group;
import com.smirnov.springschooldatabase.view.impl.GroupViewProviderImpl;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

class GroupViewProviderImplTest {
    private final GroupViewProviderImpl viewProvider = new GroupViewProviderImpl();

    @Test
    void provideViewShouldDisplayInformationAboutGroupWhereIdIs1() {
        Group expectedGroup = Group.builder()
                .withId(1)
                .withName("ff-22")
                .build();
        String expectedGroupView = "Group ID: 1, Group Name: ff-22, Group Courses: null, Course Students: null";
        String actualGroupView = viewProvider.provideView(expectedGroup);

        assertThat(actualGroupView, is(equalTo(expectedGroupView)));
    }

    @Test
    void provideViewShouldDisplayInformationAboutAllGroups() {
        Group firstGroup = Group.builder()
                .withId(1)
                .withName("ff-22")
                .build();
        Group secondGroup = Group.builder()
                .withId(2)
                .withName("mm-99")
                .build();
        List<Group> expectedGroups = Arrays.asList(firstGroup, secondGroup);
        String firstGroupView = "Group ID: 1, Group Name: ff-22, Group Courses: null, Course Students: null\n";
        String secondGroupView = "Group ID: 2, Group Name: mm-99, Group Courses: null, Course Students: null\n";
        List<String> expectedGroupsView = Arrays.asList(firstGroupView, secondGroupView);
        List<String> actualGroupsView = viewProvider.provideView(expectedGroups);

        assertThat(actualGroupsView, is(equalTo(expectedGroupsView)));
    }

}
