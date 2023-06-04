package com.crevan.task;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class ProductsImpl {

    private final Map<Integer, Product> database = new ConcurrentHashMap<>();
    private final AtomicInteger idCounter = new AtomicInteger();

    public boolean addProduct(Product product) {
        if (product == null) {
            return false;
        }
        int id = idCounter.incrementAndGet();
        product.setId(String.valueOf(id));
        Product savedProduct = database.put(id, product);
        return savedProduct == null || product.equals(savedProduct);
    }

    public boolean deleteProduct(Product product) {
        return database.values().remove(product);
    }

    public String getName(String id) {
        if (id == null) {
            throw new IllegalArgumentException("ID can't be null");
        }
        Product product = database.get(Integer.parseInt(id));
        return product == null ? "" : product.getName();
    }

    public List<String> findByName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Product name can't be null");
        }
        return database.values().stream()
                .filter(product -> product.getName().equals(name))
                .map(Product::getId)
                .collect(Collectors.toList());
    }
}
