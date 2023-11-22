package com.mycourse.Costumer;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("JPA")
public class CustumerJPADataAccessService implements CustumerDAO{
private final CustumerRepository custumerRepository;

    public CustumerJPADataAccessService(CustumerRepository custumerRepository) {
        this.custumerRepository = custumerRepository;

    }

    @Override
    public List<Custumer> SelectAllCustumer() {
        return custumerRepository.findAll();
    }

    @Override
    public Optional<Custumer> SelectCustumerById(int id) {
        return custumerRepository.findById(id);
    }

    @Override
    public void insertCustumer(Custumer custumer) {
        custumerRepository.save(custumer);
    }

    @Override
    public boolean existWithSameEmail(String email) {
        return custumerRepository.existsByEmail(email);
    }

    @Override
    public boolean existWithSameId(int id) {
        return custumerRepository.existsById(id);
    }

    @Override
    public void deleteWithId(int id) {
        custumerRepository.deleteById(id);
    }

    @Override
    public void UpdateInformation(Custumer custumer) {
        custumerRepository.save(custumer);
    }


}
