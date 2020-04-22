package practica8;

import java.io.Serializable;

public class Serves implements Serializable {

    private String bar;
    private String beer;
    private Double price;

    public Serves() {
    }

    public Serves(String bar, String beer, Double price) {
        this.setBar(bar);
        this.setBeer(beer);
        this.setPrice(price);
    }

    public String getBar() {
        return bar;
    }

    public String getBeer() {
        return beer;
    }

    public Double getPrice() {
        return price;
    }

    public void setBar(String bar) {
        this.bar = bar;
    }

    public void setBeer(String beer) {
        this.beer = beer;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "bar: " + this.getBar() + ", beer: " + this.getBeer() + ", price: " + this.getPrice();
    }
    
    

}
