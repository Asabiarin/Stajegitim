package com.fraud.springprac.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnTransformer;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "person_table",schema = "egitim")
public class Person{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String firstName;
    private String lastName;
    private int age;
    @Column(unique = true)
    private String email;
    @Column(name= "attributes" ,columnDefinition = "jsonb")
    @ColumnTransformer(write = "?::jsonb")
    private String attributes;

    /*
        ? -> the data you are trying to insert which is of type String
        :: -> representing type casting
        jsonb -> the target data type we wish our data to be transformed to
    */


    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", attributes='" + attributes + '\'' +
                '}';
    }
}
