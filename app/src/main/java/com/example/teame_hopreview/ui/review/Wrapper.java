package com.example.teame_hopreview.ui.review;

public class Wrapper<T> {

    private T item;

    public Wrapper(T item) {
        this.item = item;
    }


    public T getItem() {
        return item;
    }

    public void setItem(T item) {
        this.item = item;
    }
}

