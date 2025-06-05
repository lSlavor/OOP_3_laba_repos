package org.example.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "smartphone")
@EqualsAndHashCode(callSuper = true)
public class Smartphone extends ElectronicDevice {

    private int batteryCapacity;
    private boolean has5G;
    private boolean hasFastCharging;
    private String biometricSecurity;

    public Smartphone(String brand, double price, int batteryCapacity, boolean has5G, boolean hasFastCharging,
                      String biometricSecurity, double screenSize, String resolution) {
        super(brand, price, "Смартфон", screenSize, resolution);
        this.batteryCapacity = batteryCapacity;
        this.has5G = has5G;
        this.hasFastCharging = hasFastCharging;
        this.biometricSecurity = biometricSecurity;
    }

    public String makeCall(String number) {
        if (!isOn())
            return "Смартфон выключен. Включите устройство, чтобы звонить.";

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return "Звонок на номер " + number + "...\nСоединение установлено!";
    }

    public String takePhoto() {
        if (!isOn())
            return "Смартфон выключен. Невозможно сделать фото.";

        return "Фото сделано!";
    }

    public String playMusic() {
        if (!isOn())
            return "Смартфон выключен. Воспроизведение невозможно.";

        return "Воспроизведение музыки...";
    }

    public String streamVideo() {
        if (!isOn())
            return "Смартфон выключен. Воспроизведение видео невозможно.";

        return "Просмотр видео через интернет...";
    }

    @Override
    public void updateSystem() {
        if (!isOn()) {
            System.out.println("Смартфон выключен. Обновление невозможно.");
            return;
        }
        System.out.println("Обновление ОС смартфона...");
        for (int i = 0; i <= 100; i += 10) {
            System.out.println("Обновление: " + i + "%");
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("ОС смартфона обновлена!");
    }

    @Override
    public String resetSettings() {
        if (!isOn())
            return "Смартфон выключен. Сброс настроек невозможен.";

        return "Смартфон сброшен к заводским настройкам.";
    }

    @Override
    public String displayInfo() {
        return super.displayInfo() +
                "\nБатарея: " + batteryCapacity + " мАч" +
                "\n5G: " + (has5G ? "Да" : "Нет") +
                "\nБыстрая зарядка: " + (hasFastCharging ? "Да" : "Нет") +
                "\nБиометрическая защита: " + biometricSecurity;
    }

    @Override
    public String toString() {
        return super.toString() +
                "\nБатарея: " + batteryCapacity + " мАч" +
                "\n5G: " + (has5G ? "Да" : "Нет") +
                "\nБыстрая зарядка: " + (hasFastCharging ? "Да" : "Нет") +
                "\nБиометрическая защита: " + biometricSecurity;
    }
}
