package de.mydomain.drools.model;

public class DroolsOrder {

    private Integer id;
    private String type;
    private Integer price;
    private Integer discount;

    public DroolsOrder() {
    }

    public DroolsOrder(Integer id, String type, Integer price) {
        this.id = id;
        this.type = type;
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }
}
