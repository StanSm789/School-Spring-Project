package com.smirnov.springschooldatabase.view;

import com.smirnov.springschooldatabase.view.impl.AbstractViewProvider;
import com.smirnov.springschooldatabase.view.impl.CourseViewProviderImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AbstractViewProviderTest {

    @Spy
    private final AbstractViewProvider viewProvider = new CourseViewProviderImpl();

    @Test
    void printShouldPrintInformationOnTheConsole() {
        Object mock = Mockito.mock(Object.class);
        when(mock.toString()).thenReturn("mock");
        viewProvider.print(String.valueOf(mock));

        verify(viewProvider).print("mock");
    }

    @Test
    void printListShouldPrintAllElementsOnTheConsole(){
        Object mock = Mockito.mock(Object.class);
        when(mock.toString()).thenReturn("mock");
        List<String> mocks = Collections.nCopies(5, String.valueOf(mock));
        viewProvider.printList(mocks);

        verify(viewProvider,times(1)).printList(mocks);
    }

    @Test
    void readIntShouldReadIntegerFromTheConsole() {
        int inputDigit = 5;
        InputStream in = new ByteArrayInputStream(String.valueOf(inputDigit).getBytes());
        System.setIn(in);

        assertThat(5, is(equalTo(viewProvider.readInt())));

        verify(viewProvider).readInt();
    }

}
