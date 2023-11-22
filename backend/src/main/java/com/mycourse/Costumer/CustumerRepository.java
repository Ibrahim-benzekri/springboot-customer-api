package com.mycourse.Costumer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface CustumerRepository extends JpaRepository<Custumer,Integer> {

    boolean existsByEmail(String email);
    void deleteById(int id);
    boolean existsById(int id);

}
