package com.mycourse.Costumer;

import com.amigoscode.TestContainersAbstract;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.ApplicationContext;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CustumerRepositoryTest extends TestContainersAbstract {
@Autowired
private CustumerRepository underTest;

    @BeforeEach
    void setUp() {
    }

    @Test
    void existsByEmail() {
        Custumer custumer = new Custumer(
                "Test4",
                "Test4@gmail.com",
                22
        );
        underTest.save(custumer);

        String email = underTest.findAll().stream()
                .filter(custumer1 -> custumer1.getEmail()
                        .equals(custumer.getEmail()))
                .map(Custumer::getEmail)
                .findFirst().orElseThrow();
        boolean test = underTest.existsByEmail(email);
        assertThat(test).isTrue();
    }

    @Test
    void deleteById() {
        Custumer custumer = new Custumer(
                "Test7",
                "Test7@gmail.com",
                22
        );
        underTest.save(custumer);

        int id = underTest.findAll().stream()
                .filter(custumer1 -> custumer1.getEmail().equals("Test7@gmail.com"))
                .map(Custumer::getId)
                .findFirst().orElseThrow();
        underTest.deleteById(id);

        var actual = underTest.findById(id);
        assertThat(actual).isNotPresent();
    }

    @Test
    void existsById() {
        Custumer custumer = new Custumer(
                "Test6",
                "Test6@gmail.com",
                22
        );
        underTest.save(custumer);

        int id = underTest.findAll().stream()
                .filter(custumer1 -> custumer1.getEmail().equals("Test6@gmail.com"))
                .map(Custumer::getId)
                .findFirst().orElseThrow();

        boolean test = underTest.existsById(id);
        assertThat(test).isTrue();
    }
}