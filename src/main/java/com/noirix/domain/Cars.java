package com.noirix.domain;

import java.util.Objects;

public class Cars {

    private Long id;

    private Long owner;

    private String name;

    private String model;

    private String production_date;

    private Integer price;

    private Long dealer_id;

    public Cars() {
    }

    public Cars(Long id, Long owner, String name, String model, String production_date, Integer price, Long dealer_id) {
        this.id = id;
        this.owner = owner;
        this.name = name;
        this.model = model;
        this.production_date = production_date;
        this.price = price;
        this.dealer_id = dealer_id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOwner() {
        return owner;
    }

    public void setOwner(Long owner) {
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getProduction_date() {
        return production_date;
    }

    public void setProduction_date(String production_date) {
        this.production_date = production_date;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Long getDealer_id() {
        return dealer_id;
    }

    public void setDealer_id(Long dealer_id) {
        this.dealer_id = dealer_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cars cars = (Cars) o;
        return Objects.equals(id, cars.id) && Objects
                .equals(owner, cars.owner) && Objects
                .equals(name, cars.name) && Objects
                .equals(model, cars.model) && Objects
                .equals(production_date, cars.production_date) && Objects
                .equals(price, cars.price) && Objects
                .equals(dealer_id, cars.dealer_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, owner, name, model,
                production_date, price, dealer_id);
    }
}
