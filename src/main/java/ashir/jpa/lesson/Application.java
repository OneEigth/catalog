package ashir.jpa.lesson;

import ashir.jpa.lesson.entity.Category;
import ashir.jpa.lesson.entity.Characteristic;
import ashir.jpa.lesson.entity.Option;
import ashir.jpa.lesson.entity.Product;

import javax.persistence.*;
import javax.swing.text.html.parser.Entity;
import java.util.List;
import java.util.Scanner;

public class Application {

    private static final EntityManagerFactory FACTORY = Persistence.createEntityManagerFactory("main");

    private static final Scanner IN = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("- Создание товара [1]");
        System.out.println("- Редактирование товара [2]");
        System.out.println("- Удаление товара [3]");
        System.out.println("-Выберите действие: ");
        String actionNum = IN.nextLine();
        switch (actionNum) {
            case "1" -> create();
            case "2" -> update();
            case "3" -> delete();
            default -> System.out.println("Такого действия не существует");
        }
    }

    private static void create() {
        EntityManager manager = FACTORY.createEntityManager();
        try {
            manager.getTransaction().begin();
            TypedQuery<Category> categoryTypedQuery = manager.createQuery(
                    "select c from Category c", Category.class
            );
            List<Category> categories = categoryTypedQuery.getResultList();
            for (Category category : categories) {
                System.out.println(" - " + category.getName() + "[" + category.getId() + "]");
            }
            System.out.print("Введите номер категории: ");
            String categoryIdIn = IN.nextLine();
            int categoryId = Integer.parseInt(categoryIdIn);
            Category category = manager.find(Category.class, categoryId);

            if (category == null) {
                System.out.println("Вы ввели не правильный номер категории");
            } else {

                System.out.print("Введите название товара: ");
                String productName = IN.nextLine();

                System.out.print("Введите цену товара: ");
                String productPriceIn = IN.nextLine();
                double productPrice = Double.parseDouble(productPriceIn);

                Product product = new Product();
                product.setName(productName);
                product.setPrice(productPrice);
                product.setCategory(category);
                manager.persist(product);

                for (Characteristic characteristic : category.getCharacteristics()) {

                    System.out.println(characteristic.getName() + ": ");
                    String optionIn = IN.nextLine();

                    Option option = new Option();
                    option.setProduct(product);
                    option.setOption(optionIn);
                    option.setCharacteristic(characteristic);
                    manager.persist(option);
                }
            }
            manager.getTransaction().commit();
        } catch (Exception e) {
            manager.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            manager.close();
        }
    }

    private static void update() {
        EntityManager manager = FACTORY.createEntityManager();
        try {
            manager.getTransaction().begin();
            System.out.print("Введите номер товара: ");
            String productIdIn = IN.nextLine();
            int productId = Integer.parseInt(productIdIn);
            Product product = manager.find(Product.class, productId);

            System.out.print("Введите название товара [" + product.getName() + "]: ");
            String productName = IN.nextLine();
            if (!productName.isEmpty()) {
                product.setName(productName);
            }

            System.out.print("Введите цену товара [" + product.getPrice() + "]: ");
            String productPriceIn = IN.nextLine();
            if (!productPriceIn.isEmpty()) {
                Double productPrice = Double.parseDouble(productPriceIn);
                product.setPrice(productPrice);
            }

            manager.persist(product);

            for (Characteristic characteristic : product.getCategory().getCharacteristics()) {
                TypedQuery<Option> optionTypedQuery = manager.createQuery(
                        "select o from Option o where o.product.id = :productId and o.characteristic.id = :chacteristics", Option.class
                );
                optionTypedQuery.setParameter("productId", productId);
                optionTypedQuery.setParameter("chacteristics", characteristic.getId());
                List<Option> optionList = optionTypedQuery.getResultList();
                if (!optionList.isEmpty()) {
                    Option option = optionList.get(0);
                    System.out.print(characteristic.getName() + "[" + option.getOption() + "]: ");
                    String optionIn = IN.nextLine();
                    option.setOption(optionIn);
                } else {
                    System.out.print(characteristic.getName() + ": ");
                    String optionIn = IN.nextLine();
                    Option option = new Option();
                    option.setOption(optionIn);
                    option.setProduct(product);
                    option.setCharacteristic(characteristic);
                    manager.persist(option);
                }
            }
            manager.getTransaction().commit();
        } catch (Exception e) {
            manager.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            manager.close();
        }
    }

    private static void delete() {
        EntityManager manager = FACTORY.createEntityManager();
        try {
            manager.getTransaction().begin();
            System.out.print("Введите номер товара: ");
            String productIdIn = IN.nextLine();
            int productId = Integer.parseInt(productIdIn);
            Product product = manager.find(Product.class, productId);
            manager.remove(product);
            manager.getTransaction().commit();
        } catch (Exception e) {
            manager.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            manager.close();
        }
    }
}

