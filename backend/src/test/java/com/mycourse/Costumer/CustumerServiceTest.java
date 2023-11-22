package com.mycourse.Costumer;

import com.mycourse.Costumer.Exceptions.EmailAlreadyUsedException;
import com.mycourse.Costumer.Exceptions.IdNotFoundException;
import com.mycourse.Costumer.Exceptions.NoChangesException;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustumerServiceTest {

    private CustumerService undertest;
    @Mock
    private CustumerDAO custumerDAO;

    @BeforeEach
    void setUp() {
        undertest = new CustumerService(custumerDAO);
    }



    @Test
    void getAllCustemers() {
        undertest.getAllCustemers();
        verify(custumerDAO).SelectAllCustumer();
    }

    @Test
    void getCustemer() {
        Custumer customer = new Custumer(
                10,
                "test",
                "test@gmail.com",
                20
        );
        Mockito.when(custumerDAO.SelectCustumerById(10)).thenReturn(Optional.of(customer));

        Custumer actual = undertest.getCustemer(10);
        assertThat(actual).isEqualTo(customer);
    }
    @Test
    void WhengetCustemerThrowsException() {

        Mockito.when(custumerDAO.SelectCustumerById(10)).thenReturn(Optional.empty());
        assertThatThrownBy(()-> undertest.getCustemer(10))
                .isInstanceOf(IdNotFoundException.class)
                .hasMessage("Custumer with id %s not found.".formatted(10));
    }

    @Test
    void postCustumerServiceWithExistingEmail() {
        CustumerRequest custumerRequest = new CustumerRequest("iba",
                "IBA@gmail.com",
                20
                );
        Mockito.when(custumerDAO.existWithSameEmail("IBA@gmail.com")).
                thenReturn(true);

        assertThatThrownBy(()->undertest.PostCustumerService(custumerRequest))
                .isInstanceOf(EmailAlreadyUsedException.class)
                .hasMessage("email already exists.");
        verify(custumerDAO,Mockito.never()).insertCustumer(any());

    }

    @Test
    void postCustumerServiceWithNonExistingEmail() {
        CustumerRequest custumerRequest = new CustumerRequest("iba",
                "IBA@gmail.com",
                20
                );


        Mockito.when(custumerDAO.existWithSameEmail("IBA@gmail.com")).thenReturn(false);
        undertest.PostCustumerService(custumerRequest);

        ArgumentCaptor<Custumer> custumerArgumentCaptor = ArgumentCaptor.forClass(Custumer.class);

        verify(custumerDAO).insertCustumer(custumerArgumentCaptor.capture());
        Custumer actualCustomer = custumerArgumentCaptor.getValue();

        assertThat(actualCustomer.getId()).isEqualTo(0);
        assertThat(actualCustomer.getName()).isEqualTo(custumerRequest.name());
        assertThat(actualCustomer.getEmail()).isEqualTo(custumerRequest.email());
        assertThat(actualCustomer.getAge()).isEqualTo(custumerRequest.age());

    }

    @Test
    void deleteCustumerByIdNotFound() {
        Mockito.when(custumerDAO.existWithSameId(10)).thenReturn(false);
        assertThatThrownBy(()->undertest.deleteCustumerById(10))
                .isInstanceOf(IdNotFoundException.class)
                .hasMessage("Custumer with id %s not found.".formatted(10));

    }

    @Test
    void deleteCustumerById() {
        Mockito.when(custumerDAO.existWithSameId(10)).thenReturn(true);
        undertest.deleteCustumerById(10);
        verify(custumerDAO).deleteWithId(10);
    }

    @Test
    void updateCustumerNameOnly() {
        int id = 10;

        CustumerRequest request = new CustumerRequest(
                "test2",
                "test@gmail.com",
                20
        );

        Custumer custumer = new Custumer(
                id,
                "test",
                "test@gmail.com",
                20) ;

        when(custumerDAO.SelectCustumerById(id)).thenReturn(Optional.of(custumer));
        Custumer actual = undertest.getCustemer(id);
        undertest.UpdateCustumer(id,request);
        verify(custumerDAO).UpdateInformation(custumer);

        assertThat(actual.getName()).isEqualTo(request.name());
        assertThat(actual.getEmail()).isEqualTo(custumer.getEmail());
        assertThat(actual.getAge()).isEqualTo(custumer.getAge());


    }



    @Test
    void updateCustumerAgeOnly() {
        int id = 10;

        CustumerRequest request = new CustumerRequest(
                "test",
                "test@gmail.com",
                22
        );

        Custumer custumer = new Custumer(
                id,
                "test",
                "test@gmail.com",
                20) ;

        when(custumerDAO.SelectCustumerById(id)).thenReturn(Optional.of(custumer));
        Custumer actual = undertest.getCustemer(id);
        undertest.UpdateCustumer(id,request);
        verify(custumerDAO).UpdateInformation(custumer);

        assertThat(actual.getName()).isEqualTo(custumer.getName());
        assertThat(actual.getEmail()).isEqualTo(custumer.getEmail());
        assertThat(actual.getAge()).isEqualTo(request.age());


    }

    @Test
    void updateCustumerEmailOnly() {
        int id = 10;

        CustumerRequest request = new CustumerRequest(
                "test",
                "test2@gmail.com",
                20
        );

        Custumer custumer = new Custumer(
                id,
                "test",
                "test@gmail.com",
                20) ;

        when(custumerDAO.SelectCustumerById(id)).thenReturn(Optional.of(custumer));
        when(custumerDAO.existWithSameEmail("test2@gmail.com")).thenReturn(false);
        Custumer actual = undertest.getCustemer(id);
        undertest.UpdateCustumer(id,request);
        verify(custumerDAO).UpdateInformation(custumer);

        assertThat(actual.getName()).isEqualTo(custumer.getName());
        assertThat(actual.getEmail()).isEqualTo(request.email());
        assertThat(actual.getAge()).isEqualTo(custumer.getAge());


    }

    @Test
    void updateCustumerEmailDuplication() {
        int id = 10;
        CustumerRequest request = new CustumerRequest(
                "test2",
                "test3@gmail.com",
                202
        );

        Custumer custumer = new Custumer(
                id,
                "test",
                "test@gmail.com",
                20) ;

        when(custumerDAO.SelectCustumerById(id)).thenReturn(Optional.of(custumer));
        when(custumerDAO.existWithSameEmail("test3@gmail.com")).thenReturn(true);
        Custumer actual = undertest.getCustemer(id);


        verify(custumerDAO,never()).UpdateInformation(any());


        assertThatThrownBy(()->undertest.UpdateCustumer(id,request))
                .isInstanceOf(EmailAlreadyUsedException.class)
                .hasMessage("Email already used.");


    }

    @Test
    void updateAllInCustumer() {
        int id = 10;

        CustumerRequest request = new CustumerRequest(
                "test2",
                "test2@gmail.com",
                22
        );

        Custumer custumer = new Custumer(
                id,
                "test",
                "test@gmail.com",
                20) ;

        when(custumerDAO.SelectCustumerById(id)).thenReturn(Optional.of(custumer));
        Custumer actual = undertest.getCustemer(id);
        undertest.UpdateCustumer(id,request);
        verify(custumerDAO).UpdateInformation(custumer);
        assertThat(actual.getName()).isEqualTo(request.name());
        assertThat(actual.getEmail()).isEqualTo(request.email());
        assertThat(actual.getAge()).isEqualTo(request.age());


    }

    @Test
    void updateNoneInCustumer() {
        int id = 10;

        CustumerRequest request = new CustumerRequest(
                "test",
                "test@gmail.com",
                20
        );

        Custumer custumer = new Custumer(
                id,
                "test",
                "test@gmail.com",
                20) ;

        when(custumerDAO.SelectCustumerById(id)).thenReturn(Optional.of(custumer));
        Custumer actual = undertest.getCustemer(id);

        assertThatThrownBy(()->undertest.UpdateCustumer(id,request))
                .isInstanceOf(NoChangesException.class)
                .hasMessage("no changes are made.");

        verify(custumerDAO,never()).UpdateInformation(any());


    }



}