package com.example.bmicalculator;
import java.io.Serializable;
public class Person implements Serializable {
    String bmi,name,height,weight;
    String uri;
    public Person(String name, String bmi,String height, String weight,String uri)
    {
        this.name = name;
        this.bmi=bmi;
        this.height =height;
        this.weight=weight;
        this.uri =uri;

    }
}
