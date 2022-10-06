package com.smirnov.springschooldatabase.view.impl;

import com.smirnov.springschooldatabase.view.View;
import java.util.List;
import java.util.Scanner;

public abstract class AbstractViewProvider<E> implements View<E> {

    @Override
    public String provideView(E entity) {

        return makeView(entity);
    }

    @Override
    public List<String> provideView(List<E> lists) {

        return makeView(lists);
    }

    @Override
    public int readInt() {
        Scanner scanner = new Scanner(System.in);

        return scanner.nextInt();
    }

    @Override
    public void print(String text){

        System.out.println(text);
    }

    @Override
    public void printList(List<String> text) {

        for (String entity : text) {
            System.out.println(entity);
        }
    }

    protected abstract String makeView(E entity);

    protected abstract List<String> makeView(List<E> entity);

    protected abstract String assembleString(E entity);

}
