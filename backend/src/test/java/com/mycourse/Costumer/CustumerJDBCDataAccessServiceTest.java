package com.mycourse.Costumer;

import com.amigoscode.TestContainersAbstract;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CustumerJDBCDataAccessServiceTest extends TestContainersAbstract {
    private  CustumerJDBCDataAccessService underTest;
    private final CustumerRowMapper custumerRowMapper = new CustumerRowMapper();



    @BeforeEach
    void setUp() {
        underTest = new CustumerJDBCDataAccessService(
                getJdbcTemplate()
                ,custumerRowMapper);
    }

    @Test
    void selectAllCustumer() {
        Custumer custumer = new Custumer(
                "Test",
                "Test@gmail.com",
                22
        );
        underTest.insertCustumer(custumer);
        List<Custumer> custumers = underTest.SelectAllCustumer();
        assertThat(custumers).isNotEmpty();

    }

    @Test
    void selectCustumerById() {
        Custumer custumer = new Custumer(
                "Test2",
                "Test2@gmail.com",
                22
        );
        underTest.insertCustumer(custumer);

        int id = underTest.SelectAllCustumer().stream().filter(cus -> cus.getEmail().
                        equals("Test2@gmail.com")).
                        map(Custumer::getId).
                        findFirst().orElseThrow();

        Optional<Custumer> tested = underTest.SelectCustumerById(id);
        assertThat(tested).isPresent().hasValueSatisfying(c -> {
               assertThat(c.getId()).isEqualTo(id);
               assertThat(c.getEmail()).isEqualTo(custumer.getEmail());
               assertThat(c.getName()).isEqualTo(custumer.getName());
               assertThat(c.getAge()).isEqualTo(custumer.getAge());
        });
    }

    @Test
    void selectCustumerByIdNotExisting() {
        int id = -1;
        var actual = underTest.SelectCustumerById(id);
        assertThat(actual).isEmpty();
    }



    @Test
    void insertCustumer() {
        Custumer custumer = new Custumer(
                "Test3",
                "Test3@gmail.com",
                22
        );
        underTest.insertCustumer(custumer);
        int id = underTest.SelectAllCustumer().stream()
                .filter(custumer1 -> custumer1.getEmail().equals("Test3@gmail.com"))
                .map(Custumer::getId)
                .findFirst().orElseThrow();

        var actual = underTest.SelectCustumerById(id);
        assertThat(actual).isPresent();
    }



    @Test
    void existWithSameEmailTrue() {
        Custumer custumer = new Custumer(
                "Test4",
                "Test4@gmail.com",
                22
        );
        underTest.insertCustumer(custumer);

        String email = underTest.SelectAllCustumer().stream()
                        .filter(custumer1 -> custumer1.getEmail()
                        .equals(custumer.getEmail()))
                        .map(Custumer::getEmail)
                        .findFirst().orElseThrow();
        boolean test = underTest.existWithSameEmail(email);
        assertThat(test).isTrue();
    }

@Test
    void existWithSameEmailFalse() {
        Custumer custumer = new Custumer(
                "Test5",
                "Test5@gmail.com",
                22
        );
        underTest.insertCustumer(custumer);

        String email = "newemail@gmail.com";
        boolean test = underTest.existWithSameEmail(email);
        assertThat(test).isFalse();
    }

    @Test
    void existWithSameId() {
        Custumer custumer = new Custumer(
                "Test6",
                "Test6@gmail.com",
                22
        );
        underTest.insertCustumer(custumer);

        int id = underTest.SelectAllCustumer().stream()
                .filter(custumer1 -> custumer1.getEmail().equals("Test6@gmail.com"))
                .map(Custumer::getId)
                .findFirst().orElseThrow();

        boolean test = underTest.existWithSameId(id);
        assertThat(test).isTrue();
    }

    @Test
    void deleteWithId() {
        Custumer custumer = new Custumer(
                "Test7",
                "Test7@gmail.com",
                22
        );
        underTest.insertCustumer(custumer);

        int id = underTest.SelectAllCustumer().stream()
                .filter(custumer1 -> custumer1.getEmail().equals("Test7@gmail.com"))
                .map(Custumer::getId)
                .findFirst().orElseThrow();
        underTest.deleteWithId(id);

        var actual = underTest.SelectCustumerById(id);
        assertThat(actual).isNotPresent();
    }

    @Test
    void updateInformation() {
        Custumer custumer = new Custumer(
                "Test8",
                "Test8@gmail.com",
                22
        );
        underTest.insertCustumer(custumer);
        int id = underTest.SelectAllCustumer().stream()
                .filter(custumer1 -> custumer1.getEmail().equals("Test8@gmail.com"))
                .map(Custumer::getId)
                .findFirst().orElseThrow();

        Custumer custumer2 = new Custumer(
                id,
                "Test9",
                "Test9@gmail.com",
                30
        );


        underTest.UpdateInformation(custumer2);


        var actual = underTest.SelectCustumerById(id);

        assertThat(actual).isPresent().hasValueSatisfying(c ->{
            assertThat(c.getEmail()).isEqualTo(custumer2.getEmail());
            assertThat(c.getName()).isEqualTo(custumer2.getName());
            assertThat(c.getAge()).isEqualTo(custumer2.getAge());
                });
    }
}