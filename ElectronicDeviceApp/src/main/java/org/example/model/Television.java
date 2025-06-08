package org.example.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "television")
@EqualsAndHashCode(callSuper = true)
public class Television extends ElectronicDevice {

    private int channelCount;
    private boolean hasSmartTV;
    private boolean hasHDR;

    @ManyToOne
    @JoinColumn(name = "id", insertable = false, updatable = false)
    private ElectronicDevice electronicDevice;

    public Television(String brand, double price, int channelCount, boolean hasSmartTV, boolean hasHDR, double screenSize, String resolution) {
        super(brand, price, "Телевизор", screenSize, resolution);
        this.channelCount = channelCount;
        this.hasSmartTV = hasSmartTV;
        this.hasHDR = hasHDR;
    }

    public String showChannels() {
        if (!isOn()) return "Телевизор выключен. Включите его, чтобы посмотреть список каналов.";
        return "На экране список каналов (" + channelCount + " каналов доступно).";
    }

    public String playRadio() {
        if (!isOn()) return "Телевизор выключен. Включите его, чтобы слушать радио.";
        return "Воспроизведение радиозвука без интернета...";
    }

    public String switchChannel(int channel) {
        if (!isOn()) return "Телевизор выключен. Включите его, чтобы переключать каналы.";
        if (channel > 0 && channel <= channelCount) {
            return "Переключение на канал " + channel + "...\nКанал " + channel + " включен.";
        } else {
            return "Ошибка! Канал " + channel + " недоступен.";
        }
    }

    public String openSmartApp() {
        if (!isOn()) return "Телевизор выключен. Включите его, чтобы запускать приложения.";
        if (hasSmartTV) return "Запуск приложения Smart TV...";
        else return "Эта модель не поддерживает Smart TV!";
    }

    @Override
    public void updateSystem() {
        if (!isOn()) {
            System.out.println("Телевизор выключен. Обновление невозможно.");
            return;
        }
        System.out.println("Обновление прошивки Smart TV...");
        for (int i = 0; i <= 100; i += 10) {
            System.out.println("Обновление: " + i + "%");
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("Прошивка Smart TV обновлена!");
    }

    @Override
    public String resetSettings() {
        if (!isOn()) return "Телевизор выключен. Сброс настроек невозможен.";
        return "Телевизор сброшен к заводским настройкам.";
    }

    @Override
    public String displayInfo() {
        return super.displayInfo() +
                "\nКоличество каналов: " + channelCount +
                "\nSmart TV: " + (hasSmartTV ? "Да" : "Нет") +
                "\nHDR: " + (hasHDR ? "Да" : "Нет");
    }

    @Override
    public String toString() {
        return super.toString() +
                "\nКоличество каналов: " + channelCount +
                "\nSmart TV: " + (hasSmartTV ? "Да" : "Нет") +
                "\nHDR: " + (hasHDR ? "Да" : "Нет");
    }

}
