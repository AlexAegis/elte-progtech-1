package com.github.alexaegis.restaurant.kitchen;

class ProductFood extends Product {

    ProductFood() {

    }

    ProductFood(String name, int quality, int value) {
        this.setName(name);
        this.setValue(value);
        this.setQuality(quality);
    }
}