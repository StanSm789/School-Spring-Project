package com.smirnov.springschooldatabase.view;

import java.util.List;

public interface View<E> {

    String provideView(E entity);

    List<String> provideView(List<E> lists);

    int readInt();

    void print(String text);

    void printList(List<String> text);

}
