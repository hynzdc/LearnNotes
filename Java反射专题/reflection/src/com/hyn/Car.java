package com.hyn;

/**
 * @Auther: hyn
 * @Date: 2022/6/3 - 06 - 11:03
 * @Description: com.hyn
 * @Version: 1.0
 */
public class Car {
    public String brand  = "宝马";
    public int price = 500000;

    @Override
    public String toString() {
        return "Car{" +
                "brand='" + brand + '\'' +
                ", price=" + price +
                ", color='" + color + '\'' +
                '}';
    }

    public String color = "red";
}
