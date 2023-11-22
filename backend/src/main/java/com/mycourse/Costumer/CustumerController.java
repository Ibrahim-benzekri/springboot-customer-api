package com.mycourse.Costumer;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/custumers")
public class CustumerController {
    public CustumerService custumerService;

    public CustumerController(CustumerService custumerService) {

        this.custumerService = custumerService;
    }

    @GetMapping
    public List<Custumer> getCustumers(){

        return custumerService.getAllCustemers();
    }

    @GetMapping("{Costumerid}")
    public Custumer getCustemer(@PathVariable("Costumerid") int Costumerid){
        return custumerService.getCustemer(Costumerid) ;
    }

    @PostMapping
    public void registerCustumer(@RequestBody CustumerRequest custumerRequest){
        custumerService.PostCustumerService(custumerRequest);
    }

    @DeleteMapping("{id}")
    public void deleteCustumer(@PathVariable int id){
        custumerService.deleteCustumerById(id);
    }

    @PutMapping(path = "{id}")
    public void updateCustumer(@PathVariable int id,@RequestBody CustumerRequest custumerRequest){
        custumerService.UpdateCustumer(id,custumerRequest);
    }
}
