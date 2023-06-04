package com.crevan.task;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ProductsImplTest {

    private ProductsImpl products;

    @BeforeEach
    void init() {
        products = new ProductsImpl();
    }

    @ParameterizedTest
    @MethodSource("getLegalProducts")
    void addProduct(final Product product) {
        assertTrue(products.addProduct(product));
    }

    @Test
    void addNullProduct() {
        assertFalse(products.addProduct(null));
    }

    @Test
    void deleteProduct() {
        populateBD();
        assertTrue(products.deleteProduct(TestData.p1));
    }

    @Test
    void deleteNonExistentProduct() {
        populateBD();
        assertFalse(products.deleteProduct(TestData.notExist));
    }

    @Test
    void deleteNullProduct() {
        assertFalse(products.deleteProduct(null));
    }

    @Test
    void getName() {
        populateBD();
        assertEquals("test product 1", products.getName(TestData.ONE));
    }

    @Test
    void getNameWhenIdIsNull() {
        populateBD();
        assertThrows(IllegalArgumentException.class, () -> products.getName(null));
    }

    @Test
    void getNameWhenIdNotExist() {
        populateBD();
        assertEquals("", products.getName(TestData.NON_EXISTENT_ID));
    }

    @Test
    void findByName() {
        populateBD();
        products.addProduct(new Product("test product 1")); // make duplicate
        List<String> expected = List.of(TestData.ONE, TestData.FOUR);
        assertEquals(expected, products.findByName(TestData.p1.getName()));
    }

    @Test
    void findByNullName() {
        assertThrows(IllegalArgumentException.class, () -> products.findByName(null));
    }

    @Test
    void findByNonExistentName() {
        populateBD();
        List<String> expected = Collections.emptyList();
        assertEquals(expected, products.findByName(TestData.notExist.getName()));
    }

    private void populateBD() {
        products.addProduct(TestData.p1);
        products.addProduct(TestData.p2);
        products.addProduct(TestData.p3);
    }

    private static Stream<Product> getLegalProducts() {
        return Stream.of(
                TestData.p1,
                TestData.p2,
                TestData.p3
        );
    }
}
