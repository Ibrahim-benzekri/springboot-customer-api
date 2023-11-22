package com.mycourse.Costumer;

import jakarta.persistence.*;

@Entity
@Table(
        name = "customer",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "unique_email",
                        columnNames = "email"
                )
        }
)
public class Custumer {

    @Id
    @SequenceGenerator(name = "customer_id_seq",
            sequenceName = "customer_id_seq",
            allocationSize = 1
    )

    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "customer_id_seq"
    )
    int id;

    @Column(nullable = false)
    String name;

    @Column(nullable = false)
    String email;
    @Column(nullable = false)
    int age;

    public Custumer() {
    }
    public Custumer(int id, String name, String email, int age) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
    }

    public Custumer(String name, String email, int age) {
        this.name = name;
        this.email = email;
        this.age = age;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return "Custumer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                '}';
    }
}