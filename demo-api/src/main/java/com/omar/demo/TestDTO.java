package com.omar.demo;

public class TestDTO {
    private final String name;
    private final int id;

    public TestDTO(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public int getId(){
        return  this.id;
    }

    public String getName(){
        return  this.name;
    }
}
