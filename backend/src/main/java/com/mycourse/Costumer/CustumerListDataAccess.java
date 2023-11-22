package com.mycourse.Costumer;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository("List")
public class CustumerListDataAccess implements CustumerDAO{
    //Database and interactions with it
    public static final List<Custumer> custumerList;

    static
    {
        custumerList = new ArrayList<>();
        Custumer ibrahim = new Custumer(1,"Ibrahim","Ibrahim@gmail.com",21);
        Custumer imane = new Custumer(2,"Imane","Imane@gmail.com",26);
        custumerList.add(ibrahim);
        custumerList.add(imane);
    }
    @Override
    public List<Custumer> SelectAllCustumer() {
        return custumerList ;
    }

    @Override
    public Optional<Custumer> SelectCustumerById(int id) {
        return custumerList.stream().filter(c->c.getId() == id).findFirst();
    }

    @Override
    public void insertCustumer(Custumer custumer) {
        custumerList.add(custumer);
    }

    @Override
    public boolean existWithSameEmail(String email) {
        return custumerList.stream().anyMatch(custumer -> custumer.getEmail().equals(email));
    }

    @Override
    public boolean existWithSameId(int id) {
        return custumerList.stream().anyMatch(custumer -> custumer.getId() == id);
    }

    @Override
    public void deleteWithId(int id) {
        custumerList.remove(custumerList.stream().filter(custumer -> custumer.getId()==id));
    }

    @Override
    public void UpdateInformation(Custumer custumer) {

    }


}
