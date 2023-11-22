package com.amigoscode.journey;
import com.mycourse.Costumer.Custumer;
import com.mycourse.Costumer.CustumerRequest;
import com.mycourse.Main;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Main.class)
public class CustomerIT {
    @Autowired
    private WebTestClient webTestClient;

    @Test
    void CanRegister() {
        // create a registration request
        Random random = new Random();
        String name = "testNameregister"+random.nextInt(100,10000);
        String email = "testregisterGmail"+random.nextInt(110,10000);
        int age = 111;
        CustumerRequest request = new CustumerRequest(name, email, age);

// send a post request
        webTestClient.post()
                .uri("custumers")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(request), CustumerRequest.class)
                .exchange()
                .expectStatus()
                .isOk();

// get all customers
        List<Custumer> list = webTestClient.get()
                .uri("custumers")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(new ParameterizedTypeReference<Custumer>() {
                })
                .returnResult()
                .getResponseBody();

// make sure the customer is present
        Custumer expectedCustomer = new Custumer(
                name, email, age
        );
        assertThat(list).usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
                .contains(expectedCustomer);

// get customer by id
        var id = list.stream().filter((custumer) -> custumer.getEmail().equals(expectedCustomer.getEmail()))
                .map(custumer -> custumer.getId())
                .findFirst()
                .orElseThrow();

        expectedCustomer.setId(id);
        webTestClient.get()
                .uri("custumers/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(new ParameterizedTypeReference<Custumer>() {
                }).equals(expectedCustomer);

    }

    @Test
    void canUpdateCustomer() {
        // create a registration request
        Random random = new Random();
        String name = "testNameupdate22"+random.nextInt(110,10000);
        String email = "testupdategmail22"+random.nextInt(230,10000);
        int age = 101;
        CustumerRequest request = new CustumerRequest(name, email, age);

// send a post request
        webTestClient.post()
                .uri("custumers")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(request), CustumerRequest.class)
                .exchange()
                .expectStatus()
                .isOk();

// get all customers
        List<Custumer> list = webTestClient.get()
                .uri("custumers")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(new ParameterizedTypeReference<Custumer>() {
                })
                .returnResult()
                .getResponseBody();

// make sure the customer is present
        Custumer expectedCustomer = new Custumer(
                name, email, age
        );
        assertThat(list).usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
                .contains(expectedCustomer);

// get customer by id
        var id = list.stream().filter((custumer) -> custumer.getEmail().equals(expectedCustomer.getEmail()))
                .map(custumer -> custumer.getId())
                .findFirst()
                .orElseThrow();

        expectedCustomer.setId(id);
        webTestClient.get()
                .uri("custumers/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(new ParameterizedTypeReference<Custumer>() {
                }).equals(expectedCustomer);

        // create a put request

        CustumerRequest updateRequest = new CustumerRequest("IBAtestupdate","IBATEST@gmail.com"+random.nextInt(0,10000),190);
        Custumer updatedCustomer = new Custumer(id,updateRequest.name(),updateRequest.email(),updateRequest.age());
        webTestClient.put()
                .uri("custumers/{Id}",id)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(updateRequest), CustumerRequest.class)
                .exchange()
                .expectStatus()
                .isOk();

        webTestClient.get()
                .uri("custumers/{id}",id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(new ParameterizedTypeReference<Custumer>() {
                })
                .equals(updatedCustomer);
    }


    @Test
    void canDeleteCustomer() {
        // create a registration request
        Random random = new Random();
        String name = "testNamedelete";
        String email = "deleteTest"+random.nextInt(210,10000);
        int age = 110;
        CustumerRequest request = new CustumerRequest(name, email, age);

// send a post request
        webTestClient.post()
                .uri("custumers")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(request), CustumerRequest.class)
                .exchange()
                .expectStatus()
                .isOk();

        // get all customers

        List<Custumer> list = webTestClient.get()
                .uri("custumers")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(new ParameterizedTypeReference<Custumer>() {
                })
                .returnResult()
                .getResponseBody();
        // make sure customer is present


        // get customer by id
        var id= list.stream().filter((customer)->customer.getEmail().equals(email))
                .map(customer -> customer.getId())
                .findFirst()
                .orElseThrow();


        webTestClient.delete()
                .uri("custumers/{id}",id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk();

        webTestClient.get()
                .uri("custumers/{id}",id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isNotFound();
    }

}
