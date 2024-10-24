package com.tyme.vietnam;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ProductInventory {

    // 1. Calculate the total inventory value
    private static double calculateTotalInventory(List<Product> products) {
        double total = 0;
        for (Product p : products) {
            total += p.getPrice() * p.getQuantity();
        }
        return Math.round(total * 100.0) / 100.0;
    }

    // 2. Find the most expensive product
    private static String findTheMostExpsProduct(List<Product> products) {
        String res = "";
        double maxPrice = 0;

        for (Product p : products) {
            if (p.getPrice() > maxPrice) {
                maxPrice = p.getPrice();
                res = p.getName();
            }
        }
        return res;
    }

    // 3. Check if a product named "Headphones" is in stock
    private static boolean isProductInStock(List<Product> products, String name) {
        for (Product p : products) {
            if (p.getName().equals(name) && p.getQuantity() > 0) {
                return true;
            }
        }
        return false;
    }

    // 4. Sort products in descending/ascending order with options like by price, quantity
    private static List<Product> sortProducts(List<Product> products, String option, boolean ascending) {
        if (option.equalsIgnoreCase("price")) {
            products.sort(ascending ? Comparator.comparing(Product::getPrice) : Comparator.comparing(Product::getPrice).reversed());
        } else if (option.equalsIgnoreCase("quantity")) {
            products.sort(ascending ? Comparator.comparing(Product::getQuantity) : Comparator.comparing(Product::getQuantity).reversed());
        }
        return products;
    }

    // Print Product List
    private static StringBuilder printProductList(List<Product> products) {
        StringBuilder productList = new StringBuilder();
        for (Product product : products) {
            productList.append(product.toString());
        }
        return productList;
    }

    public static void main(String[] args) {
        Product laptop = new Product("Laptop", 999.99, 5);
        Product smartphone = new Product("Smartphone", 499.99, 10);
        Product tablet = new Product("Tablet", 299.99, 0);
        Product smartwatch = new Product("Smartwatch", 199.99, 3);

        List<Product> list = new ArrayList<>();
        list.add(laptop);
        list.add(smartphone);
        list.add(tablet);
        list.add(smartwatch);

        // Print product list
        StringBuilder productListString = printProductList(list);
        System.out.println("Product List:\n\n" + productListString);

        // 1. Calculate the total inventory value
        double totalInventory = calculateTotalInventory(list);
        System.out.println("Total inventory: " + totalInventory);

        // 2. Find the most expensive product
        String mostExpensive = findTheMostExpsProduct(list);
        System.out.println("The most expensive product: " + mostExpensive);

        // 3. Check if a product named "Headphones" is in stock
        boolean isInStock = isProductInStock(list, "Headphones");
        System.out.println("Product Headphones is in stock: " + isInStock);

        // 4.1. Sort by price (ascending)
        List<Product> sortedByPriceAsc = sortProducts(list, "price", true);
        System.out.println("\nSorted by price (ascending):");
        sortedByPriceAsc.forEach(System.out::print);

        // 4.2. Sort by quantity (descending)
        List<Product> sortedByQuantityDesc = sortProducts(list, "quantity", false);
        System.out.println("\nSorted by quantity (descending):");
        sortedByQuantityDesc.forEach(System.out::print);
    }
}