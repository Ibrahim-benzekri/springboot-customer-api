package com.mycourse.Costumer;

import com.mycourse.Costumer.Exceptions.EmailAlreadyUsedException;
import com.mycourse.Costumer.Exceptions.IdNotFoundException;
import com.mycourse.Costumer.Exceptions.NoChangesException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CustumerService {
    public final CustumerDAO custumerDAO;


    public CustumerService(@Qualifier("JDBC") CustumerDAO custumerDAO) {
        this.custumerDAO = custumerDAO;

    }

    public List<Custumer> getAllCustemers(){

        return custumerDAO.SelectAllCustumer();
    }
    public Custumer getCustemer(int id){
        return custumerDAO.SelectCustumerById(id).
                orElseThrow(()->new IdNotFoundException("Custumer with id %s not found.".formatted(id)));
    }

    public void PostCustumerService(CustumerRequest custumerRequest){
        //if email exists
            if(custumerDAO.existWithSameEmail(custumerRequest.email())){
                throw new EmailAlreadyUsedException("email already exists.");
            }
        //if email doesnt exist
        Custumer custumer = new Custumer(
                custumerRequest.name(),
                custumerRequest.email(),
                custumerRequest.age()
        );
            custumerDAO.insertCustumer(custumer);
    }

    public void deleteCustumerById(int id){
        if(!custumerDAO.existWithSameId(id)){
            throw new IdNotFoundException("Custumer with id %s not found.".formatted(id));
        }
        custumerDAO.deleteWithId(id);
    }

    public void UpdateCustumer(int id,CustumerRequest custumerRequest){
        Custumer custumer = getCustemer(id);
        boolean changes = false;
        if(!custumer.getName().equals(custumerRequest.name()) && custumerRequest.name() != null){
            custumer.setName(custumerRequest.name());
            changes = true;
        }


        if (!(custumer.getAge()==custumerRequest.age()) && custumerRequest.age() != 0   ){
            custumer.setAge(custumerRequest.age());
            changes = true;
        }

        if (!custumer.getEmail().equals(custumerRequest.email()) && custumerRequest.email() != null){
            if (custumerDAO.existWithSameEmail(custumerRequest.email())) {
                throw new EmailAlreadyUsedException("Email already used.");
            }
            custumer.setEmail(custumerRequest.email());
            changes = true;
        }
        if (changes){
            custumerDAO.UpdateInformation(custumer);
        }else {
            throw new NoChangesException("no changes are made.");
        }


    }

}
