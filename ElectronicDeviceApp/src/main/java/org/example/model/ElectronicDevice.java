package org.example.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "electronic_device")
public abstract class ElectronicDevice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String brand;
    private double price;
    private String deviceType;
    private double screenSize;
    private String resolution;

    @Transient
    private boolean isOn = false;

    public ElectronicDevice(String brand, double price, String deviceType, double screenSize, String resolution) {
        this.brand = brand;
        this.price = price;
        this.deviceType = deviceType;
        this.screenSize = screenSize;
        this.resolution = resolution;
    }

    public String turnOn() {
        if (!isOn) {
            isOn = true;
            return brand + " включен.";
        } else {
            return brand + " уже включен!";
        }
    }

    public String turnOff() {
        if (isOn) {
            isOn = false;
            return brand + " выключен.";
        } else {
            return brand + " уже выключен!";
        }
    }

    public String displayInfo() {
        return String.format("Устройство: %s, Цена: %.2f руб., Тип: %s, Экран: %.1f дюймов, Разрешение: %s",
                brand, price, deviceType, screenSize, resolution);
    }

    @Override
    public String toString() {
        return String.format("Устройство: %s, Цена: %.2f руб., Тип: %s, Экран: %.1f дюймов, Разрешение: %s",
                brand, price, deviceType, screenSize, resolution);
    }

    public abstract void updateSystem();
    public abstract String resetSettings();
}
