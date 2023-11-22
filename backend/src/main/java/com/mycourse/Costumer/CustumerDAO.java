package com.mycourse.Costumer;

import java.util.List;
import java.util.Optional;

public interface CustumerDAO {
    List<Custumer> SelectAllCustumer();
    Optional<Custumer> SelectCustumerById(int id);
    void insertCustumer(Custumer custumer);
    boolean existWithSameEmail(String email);
    boolean existWithSameId(int id);
    void deleteWithId(int id);
    void UpdateInformation(Custumer custumer);

}
