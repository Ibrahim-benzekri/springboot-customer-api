package com.mycourse.Costumer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class CustumerJPADataAccessServiceTest {
    private CustumerJPADataAccessService undertest;
    @Mock
    CustumerRepository custumerRepository;
    AutoCloseable autoCloseable ;


    @BeforeEach
    void setUp() {
      autoCloseable = MockitoAnnotations.openMocks(this);
      undertest = new CustumerJPADataAccessService(custumerRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void selectAllCustumer() {
        undertest.SelectAllCustumer();

        Mockito.verify(custumerRepository).findAll();
    }

    @Test
    void selectCustumerById() {
        undertest.SelectCustumerById(1);
        Mockito.verify(custumerRepository).findById(1);
    }

    @Test
    void insertCustumer() {
        Custumer custumer = new Custumer(
                1,
                "test",
                "test@gmail.com",
                20
        );
        undertest.insertCustumer(custumer);
        Mockito.verify(custumerRepository).save(custumer);
    }

    @Test
    void existWithSameEmail() {
        String email = "test@gmail.com";
        undertest.existWithSameEmail(email);
        Mockito.verify(custumerRepository).existsByEmail(email);
    }

    @Test
    void existWithSameId() {
        undertest.existWithSameId(1);
        Mockito.verify(custumerRepository).existsById(1);
    }

    @Test
    void deleteWithId() {
        undertest.deleteWithId(1);
        Mockito.verify(custumerRepository).deleteById(1);
    }

    @Test
    void updateInformation() {
        Custumer custumer = new Custumer(
                1,
                "test",
                "test@gmail.com",
                20
        );
        undertest.UpdateInformation(custumer);
        Mockito.verify(custumerRepository).save(custumer);
    }
}