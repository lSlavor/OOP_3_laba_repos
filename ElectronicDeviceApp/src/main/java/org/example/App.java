package org.example;

import org.example.model.ElectronicDevice;
import org.example.model.Smartphone;
import org.example.model.Television;
import org.example.model.Owner;

import org.example.dao.ElectronicDeviceDAO;
import org.example.dao.ElectronicDeviceDAOImpl;

import org.example.dao.OwnerDAO;
import org.example.dao.OwnerDAOImpl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Scanner;
import java.util.List;

public class App {
    private static final Scanner scanner = new Scanner(System.in);
    private static final ElectronicDeviceDAO<Television> TelevisionDAO = new ElectronicDeviceDAOImpl<>(Television.class);
    private static final ElectronicDeviceDAO<Smartphone> SmartphoneDAO = new ElectronicDeviceDAOImpl<>(Smartphone.class);

    private static final OwnerDAO ownerDAO = new OwnerDAOImpl();

    public static void main(String[] args) {
        adminDirectorMenu();
    }

    private static void adminDirectorMenu() {
        int choice;
        do {
            System.out.println("\n        Фабрика Электроники v1.0");
            System.out.println("Вход успешен! Добро пожаловать");
            System.out.println("1. Посмотреть склад");
            System.out.println("2. Добавить товар");
            System.out.println("3. Изменить склад (удаление или изменение товара)");
            System.out.println("5. Тестирование методов классов");
            System.out.println("7. Показать товары по ВЛАДЕЛЬЦУ");
            System.out.println("9. О программе");
            System.out.println("0. Завершить сеанс работы");
            System.out.print("Выберите действие: ");
            choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> viewStorage();
                case 2 -> addStorageItem();
                case 3 -> changeStorage();
                case 5 -> checkDevices();
                case 7 -> showDevicesByOwner();
                case 9 -> showAbout();
                case 0 -> System.out.println("Выход...");
                default -> System.out.println("Некорректный выбор!");
            }
        } while (choice != 0);
    }

    private static void viewStorage() {
        System.out.println("Выберите категорию товаров:");
        System.out.println("1 - Телевизоры");
        System.out.println("2 - Смартфоны");
        System.out.println("0 - Назад в меню");
        System.out.print("Ваш выбор: ");
        int choice = Integer.parseInt(scanner.nextLine());

        if (choice == 0) return;

        if (choice == 1) {
            List<Television> tvs = TelevisionDAO.findAll();
            if (tvs.isEmpty()) System.out.println("Нет телевизоров на складе.");
            for (Television tv : tvs) {
                System.out.println("Владелец: " + (tv.getOwner() != null ? tv.getOwner().getName() : "Не указан"));
                System.out.println(tv); // тут вызов toString, сразу (так же как и tv.toString() или tv.displayInfo() );
                System.out.println("---------------------------");
            }
        } else if (choice == 2) {
            List<Smartphone> phones = SmartphoneDAO.findAll();
            if (phones.isEmpty()) System.out.println("Нет смартфонов на складе.");
            for (Smartphone phone : phones) {
                System.out.println("Владелец: " + (phone.getOwner() != null ? phone.getOwner().getName() : "Не указан"));
                System.out.println(phone.displayInfo());
                System.out.println("---------------------------");
            }
        }
        System.out.println("Нажмите Enter для продолжения...");
        scanner.nextLine();
    }

    private static void addStorageItem() {
        System.out.println("Выберите категорию товара:");
        System.out.println("1 - Телевизор");
        System.out.println("2 - Смартфон");
        System.out.println("0 - Назад");
        System.out.print("Ваш выбор: ");
        int choice = Integer.parseInt(scanner.nextLine());

        if (choice == 0) return;

        System.out.print("Введите бренд: ");
        String brand = scanner.nextLine();

        System.out.print("Введите цену: ");
        double price = Double.parseDouble(scanner.nextLine());

        System.out.print("Введите размер экрана (дюймы): ");
        double screenSize = Double.parseDouble(scanner.nextLine());

        System.out.print("Введите разрешение экрана: ");
        String resolution = scanner.nextLine();

        if (choice == 1) {
            System.out.print("Введите количество каналов: ");
            int channels = Integer.parseInt(scanner.nextLine());

            System.out.print("Есть Smart TV? (1 - да, 0 - нет): ");
            boolean smartTV = scanner.nextLine().equals("1");

            System.out.print("Есть HDR? (1 - да, 0 - нет): ");
            boolean hdr = scanner.nextLine().equals("1");

            System.out.print("Введите имя владельца устройства: ");
            String ownerName = scanner.nextLine();

            // Проверка — существует ли владелец
            Owner owner = ownerDAO.findByName(ownerName);
            if (owner == null) {
                owner = new Owner(ownerName);
                ownerDAO.save(owner);
            }


            Television tv = new Television(brand, price, channels, smartTV, hdr, screenSize, resolution);
            tv.setOwner(owner);
            TelevisionDAO.save(tv);
            System.out.println("Телевизор добавлен!");

        } else if (choice == 2) {
            System.out.print("Введите емкость батареи (мАч): ");
            int battery = Integer.parseInt(scanner.nextLine());

            System.out.print("Есть 5G? (1 - да, 0 - нет): ");
            boolean has5G = scanner.nextLine().equals("1");

            System.out.print("Есть быстрая зарядка? (1 - да, 0 - нет): ");
            boolean fastCharge = scanner.nextLine().equals("1");

            System.out.print("Введите тип биометрической защиты: ");
            String security = scanner.nextLine();

            System.out.print("Введите имя владельца устройства: ");
            String ownerName = scanner.nextLine();

            // Проверка — существует ли владелец
            Owner owner = ownerDAO.findByName(ownerName);
            if (owner == null) {
                owner = new Owner(ownerName);
                ownerDAO.save(owner);
            }

            Smartphone phone = new Smartphone(brand, price, battery, has5G, fastCharge, security, screenSize, resolution);
            phone.setOwner(owner);
            SmartphoneDAO.save(phone);
            System.out.println("Смартфон добавлен!");
        }
    }

    private static void changeStorage() {
        System.out.println("1 - Удалить товар");
        System.out.println("2 - Изменить товар");
        System.out.println("0 - Назад");
        System.out.print("Ваш выбор: ");
        int option = Integer.parseInt(scanner.nextLine());

        if (option == 0) return;

        System.out.println("1 - Телевизор");
        System.out.println("2 - Смартфон");
        System.out.print("Категория: ");
        int cat = Integer.parseInt(scanner.nextLine());

        if (cat == 1) {
            List<Television> tvs = TelevisionDAO.findAll();
            for (int i = 0; i < tvs.size(); i++) {
                Television tv = tvs.get(i);
                String ownerName = tv.getOwner() != null ? tv.getOwner().getName() : "Не указан";
                System.out.println((i + 1) + ". Владелец: " + ownerName);
                System.out.println("    " + tv.displayInfo());
            }
            System.out.print("Выберите номер: ");
            int idx = Integer.parseInt(scanner.nextLine()) - 1;

            if (option == 1) {
                TelevisionDAO.delete(tvs.get(idx));
            } else {
                Television tv = tvs.get(idx);
                int editChoice;
                do {
                    System.out.println("Выберите, что изменить:");
                    System.out.println("1 - Бренд");
                    System.out.println("2 - Цена");
                    System.out.println("3 - Размер экрана");
                    System.out.println("4 - Разрешение");
                    System.out.println("5 - Количество каналов");
                    System.out.println("6 - Smart TV");
                    System.out.println("7 - HDR");
                    System.out.println("8 - Изменить владельца");
                    System.out.println("0 - Сохранить и выйти");
                    System.out.print("Ваш выбор: ");
                    editChoice = Integer.parseInt(scanner.nextLine());

                    switch (editChoice) {
                        case 1 -> {
                            System.out.print("Новый бренд: ");
                            tv.setBrand(scanner.nextLine());
                        }
                        case 2 -> {
                            System.out.print("Новая цена: ");
                            tv.setPrice(Double.parseDouble(scanner.nextLine()));
                        }
                        case 3 -> {
                            System.out.print("Новый размер экрана: ");
                            tv.setScreenSize(Double.parseDouble(scanner.nextLine()));
                        }
                        case 4 -> {
                            System.out.print("Новое разрешение: ");
                            tv.setResolution(scanner.nextLine());
                        }
                        case 5 -> {
                            System.out.print("Новое количество каналов: ");
                            tv.setChannelCount(Integer.parseInt(scanner.nextLine()));
                        }
                        case 6 -> {
                            System.out.print("Есть Smart TV? (1 - да, 0 - нет): ");
                            tv.setHasSmartTV(scanner.nextLine().equals("1"));
                        }
                        case 7 -> {
                            System.out.print("Есть HDR? (1 - да, 0 - нет): ");
                            tv.setHasHDR(scanner.nextLine().equals("1"));
                        }
                        case 8 -> {
                            System.out.print("Введите новое имя владельца: ");
                            String newOwnerName = scanner.nextLine();

                            Owner newOwner = ownerDAO.findByName(newOwnerName);
                            if (newOwner == null) {
                                newOwner = new Owner(newOwnerName);
                                ownerDAO.save(newOwner);
                            }
                            tv.setOwner(newOwner);
                            System.out.println("Новый владелец выбран.");
                        }
                        case 0 -> System.out.println("Изменения сохранены.");
                        default -> System.out.println("Неверный выбор.");
                    }
                } while (editChoice != 0);

                TelevisionDAO.update(tv);
            }

        } else if (cat == 2) {
            List<Smartphone> phones = SmartphoneDAO.findAll();
            for (int i = 0; i < phones.size(); i++) {
                Smartphone phone = phones.get(i);
                String ownerName = phone.getOwner() != null ? phone.getOwner().getName() : "Не указан";
                System.out.println((i + 1) + ". Владелец: " + ownerName);
                System.out.println("    " + phone.displayInfo());
            }
            System.out.print("Выберите номер: ");
            int idx = Integer.parseInt(scanner.nextLine()) - 1;

            if (option == 1) {
                SmartphoneDAO.delete(phones.get(idx));
            } else {
                Smartphone phone = phones.get(idx);
                int editChoice;
                do {
                    System.out.println("Выберите, что изменить:");
                    System.out.println("1 - Бренд");
                    System.out.println("2 - Цена");
                    System.out.println("3 - Размер экрана");
                    System.out.println("4 - Разрешение");
                    System.out.println("5 - Батарея");
                    System.out.println("6 - 5G");
                    System.out.println("7 - Быстрая зарядка");
                    System.out.println("8 - Биометрия");
                    System.out.println("9 - Изменить владельца");
                    System.out.println("0 - Сохранить и выйти");
                    System.out.print("Ваш выбор: ");
                    editChoice = Integer.parseInt(scanner.nextLine());

                    switch (editChoice) {
                        case 1 -> {
                            System.out.print("Новый бренд: ");
                            phone.setBrand(scanner.nextLine());
                        }
                        case 2 -> {
                            System.out.print("Новая цена: ");
                            phone.setPrice(Double.parseDouble(scanner.nextLine()));
                        }
                        case 3 -> {
                            System.out.print("Новый размер экрана: ");
                            phone.setScreenSize(Double.parseDouble(scanner.nextLine()));
                        }
                        case 4 -> {
                            System.out.print("Новое разрешение: ");
                            phone.setResolution(scanner.nextLine());
                        }
                        case 5 -> {
                            System.out.print("Новая батарея (мАч): ");
                            phone.setBatteryCapacity(Integer.parseInt(scanner.nextLine()));
                        }
                        case 6 -> {
                            System.out.print("Есть 5G? (1 - да, 0 - нет): ");
                            phone.setHas5G(scanner.nextLine().equals("1"));
                        }
                        case 7 -> {
                            System.out.print("Есть быстрая зарядка? (1 - да, 0 - нет): ");
                            phone.setHasFastCharging(scanner.nextLine().equals("1"));
                        }
                        case 8 -> {
                            System.out.print("Новый тип биометрии: ");
                            phone.setBiometricSecurity(scanner.nextLine());
                        }
                        case 9 -> {
                            System.out.print("Введите новое имя владельца: ");
                            String newOwnerName = scanner.nextLine();

                            Owner newOwner = ownerDAO.findByName(newOwnerName);
                            if (newOwner == null) {
                                newOwner = new Owner(newOwnerName);
                                ownerDAO.save(newOwner);
                            }
                            phone.setOwner(newOwner);
                            System.out.println("Новый владелец выбран.");
                        }
                        case 0 -> System.out.println("Изменения сохранены.");
                        default -> System.out.println("Неверный выбор.");
                    }
                } while (editChoice != 0);

                SmartphoneDAO.update(phone);
            }
        }
    }


    private static void checkDevices() {
        System.out.println("1 - Телевизоры");
        System.out.println("2 - Смартфоны");
        System.out.println("0 - Назад");
        System.out.print("Категория: ");
        int cat = Integer.parseInt(scanner.nextLine());

        if (cat == 0) return;

        List<? extends ElectronicDevice> devices =
                (cat == 1) ? TelevisionDAO.findAll() : SmartphoneDAO.findAll();

        for (int i = 0; i < devices.size(); i++) {
            System.out.println((i + 1) + ". " + devices.get(i).displayInfo());
        }

        System.out.print("Выберите устройство: ");
        int idx = Integer.parseInt(scanner.nextLine()) - 1;
        if (idx < 0 || idx >= devices.size()) {
            System.out.println("Неверный номер устройства.");
            return;
        }

        ElectronicDevice device = devices.get(idx);
        System.out.println(device.displayInfo());

        while (true) {
            System.out.println("\nВыберите действие:");
            System.out.println("1 - Включить");
            System.out.println("2 - Выключить");
            System.out.println("3 - Обновить систему");
            System.out.println("4 - Сбросить настройки");

            if (device instanceof Television) {
                System.out.println("5 - Показать каналы");
                System.out.println("6 - Радио");
                System.out.println("7 - Переключить канал");
                System.out.println("8 - Открыть Smart-приложение");
            } else if (device instanceof Smartphone) {
                System.out.println("5 - Позвонить");
                System.out.println("6 - Сделать фото");
                System.out.println("7 - Воспроизвести музыку");
                System.out.println("8 - Смотреть видео");
            }

            System.out.println("0 - Назад");
            System.out.print("Выбор: ");
            int act = Integer.parseInt(scanner.nextLine());

            if (act == 0) break;

            switch (act) {
                case 1 -> System.out.println(device.turnOn());
                case 2 -> System.out.println(device.turnOff());
                case 3 -> device.updateSystem();
                case 4 -> System.out.println(device.resetSettings());

                case 5 -> {
                    if (device instanceof Television tv) {
                        System.out.println(tv.showChannels());
                    } else if (device instanceof Smartphone phone) {
                        System.out.print("Введите номер для звонка: ");
                        String number = scanner.nextLine();
                        System.out.println(phone.makeCall(number));
                    } else {
                        System.out.println("Недопустимое действие.");
                    }
                }

                case 6 -> {
                    if (device instanceof Television tv) {
                        System.out.println(tv.playRadio());
                    } else if (device instanceof Smartphone phone) {
                        System.out.println(phone.takePhoto());
                    } else {
                        System.out.println("Недопустимое действие.");
                    }
                }

                case 7 -> {
                    if (device instanceof Television tv) {
                        System.out.print("Введите номер канала: ");
                        int ch = Integer.parseInt(scanner.nextLine());
                        System.out.println(tv.switchChannel(ch));
                    } else if (device instanceof Smartphone phone) {
                        System.out.println(phone.playMusic());
                    } else {
                        System.out.println("Недопустимое действие.");
                    }
                }

                case 8 -> {
                    if (device instanceof Television tv) {
                        System.out.println(tv.openSmartApp());
                    } else if (device instanceof Smartphone phone) {
                        System.out.println(phone.streamVideo());
                    } else {
                        System.out.println("Недопустимое действие.");
                    }
                }

                default -> System.out.println("Неизвестное действие");
            }
        }
    }

    private static void showAbout() {
        System.out.println("Фабрика Электроники v1.0. Разработано в 2025 году.");
    }

    private static void showDevicesByOwner() {
        System.out.print("Введите имя владельца: ");
        String ownerName = scanner.nextLine();

        Owner owner = ownerDAO.findByName(ownerName);
        if (owner == null) {
            System.out.println("Такой владелец не найден.");
            return;
        }

        List<ElectronicDevice> devices = owner.getDevices(); // все устройства владельца

        System.out.println("\nТелевизоры владельца " + ownerName + ":");
        devices.stream()
                .filter(device -> device instanceof Television)
                .map(device -> (Television) device)
                .forEach(tv -> System.out.println(tv.displayInfo()));

        System.out.println("\nСмартфоны владельца " + ownerName + ":");
        devices.stream()
                .filter(device -> device instanceof Smartphone)
                .map(device -> (Smartphone) device)
                .forEach(phone -> System.out.println(phone.displayInfo()));

        System.out.println("Нажмите Enter для продолжения...");
        scanner.nextLine();
    }

}

