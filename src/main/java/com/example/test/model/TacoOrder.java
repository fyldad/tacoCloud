package com.example.test.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.rest.core.annotation.RestResource;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Document
@RestResource(rel = "orders", path = "orders")
public class TacoOrder implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private String id;
    @NotBlank(message = "delivery name is required")
    private String deliveryName;
    @NotBlank(message = "street is required")
    private String deliveryStreet;
    @NotBlank(message = "city is required")
    private String deliveryCity;
    @NotBlank(message = "state is required")
    private String deliveryState;
    @NotBlank(message = "zip code is required")
    private String deliveryZip;
//    @CreditCardNumber(message="Not a valid credit card number")
    private String ccNumber;
//    @Pattern(regexp="^(0[1-9]|1[0-2])([\\/])([2-9][0-9])$", message="Must be formatted MM/YY")
    private String ccExpiration;
    @Digits(integer = 3, fraction = 0, message = "invalid CVV")
    private String ccCVV;
    private Date placedAt = new Date();
    @JsonIgnore
    private User user;

    private List<Taco> tacos = new ArrayList<>();
    public void addTaco(Taco taco) {
        this.tacos.add(taco);
    }
}
