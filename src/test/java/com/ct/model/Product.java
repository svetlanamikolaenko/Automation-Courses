package com.ct.model;

public class Product {
    private String name;
    private double price;
    private String description;
    private String image;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Product() {
    }

    public Builder newBuilder(){
        return new Builder();
    }

    public Product( final Builder builder){
        name = builder.name;
        price = builder.price;
        description = builder.description;
        image = builder.image;
    }

    public static final class Builder {
        private String name;
        private double price;
        private String description;
        private String image;

        private Builder(){
        }

        public Builder withName(final String val) {
            name = val;
            return this;
        }

        public Builder withPrice (final double val) {
            price = val;
            return this;
        }

        public Builder withDescription(final String val){
            description = val;
            return this;
        }

        public Builder withImage(final String val) {
            image = val;
            return this;
        }

        public Product build(){
            return new Product(this);
        }
    }
}
