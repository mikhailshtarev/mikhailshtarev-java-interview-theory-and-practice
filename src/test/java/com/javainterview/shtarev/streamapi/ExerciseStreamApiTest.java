package com.javainterview.shtarev.streamapi;

import com.javainterview.shtarev.streamapi.dto.Order;
import com.javainterview.shtarev.streamapi.dto.Product;
import org.apache.commons.math3.util.Pair;
import org.apache.commons.math3.util.Precision;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ExerciseStreamApiTest {

    @Autowired
    private ExerciseStreamApi exerciseStreamApi;

    @Test
    void calculateOrderTotal() {

        Product p1 = createProduct("name1", 1, 100.12);
        Product p2 = createProduct("name2", 2, 10.22);
        Product p3 = createProduct("name3", 3, 5.11);

        Order order = createOrder("1", "order1", List.of(p1, p2, p3));

        Double result = exerciseStreamApi.calculateOrderTotal(order);
        System.out.println(result);

        assertThat(Precision.equals(135.89, result, 0.000001d)).isTrue();

    }

    @Test
    void getUniqueProductNames() {

        Product p1 = createProduct("name1", 1, 100.12);
        Product p2 = createProduct("name2", 2, 10.22);
        Product p3 = createProduct("name3", 3, 5.11);
        Product p4 = createProduct("name1", 3, 5.11);
        Product p5 = createProduct("name2", 3, 5.11);
        Product p6 = createProduct("name4", 3, 5.11);
        Product p7 = createProduct("name2", 3, 5.11);

        Order o1 = createOrder("1", "order1", List.of(p1, p2, p3, p4));
        Order o2 = createOrder("1", "order1", List.of(p5, p6));
        Order o3 = createOrder("1", "order1", List.of(p7));

        Set<String> result = exerciseStreamApi.getUniqueProductNames(List.of(o1, o2, o3));
        System.out.println(result);

        assertEquals(4, result.size());


    }

    @Test
    void findMostPopularProducts() {

        Product p1 = createProduct("name1", 1, 100.12);
        Product p2 = createProduct("name2", 2, 10.22);
        Product p3 = createProduct("name3", 3, 5.11);
        Product p4 = createProduct("name1", 3, 5.11);
        Product p5 = createProduct("name2", 3, 5.11);
        Product p6 = createProduct("name4", 3, 5.11);
        Product p7 = createProduct("name2", 3, 5.11);

        Order o1 = createOrder("1", "order1", List.of(p1, p2, p3, p4));
        Order o2 = createOrder("2", "order2", List.of(p5, p6));
        Order o3 = createOrder("3", "order3", List.of(p7));

        List<String> result = exerciseStreamApi.findMostPopularProducts(List.of(o1, o2, o3), 2);
        System.out.println(result);

        assertEquals(2, result.size());

        assertEquals("name2", result.get(0));
        assertEquals("name1", result.get(1));

    }

    @Test
    void findAveragePrice() {

        Product p1 = createProduct("name1", 1, 100.12);
        Product p2 = createProduct("name2", 2, 10.22);
        Product p3 = createProduct("name3", 3, 5.11);
        Product p4 = createProduct("name1", 3, 5.11);
        Product p5 = createProduct("name2", 3, 5.11);
        Product p6 = createProduct("name4", 3, 5.11);
        Product p7 = createProduct("name2", 3, 5.11);

        Order o1 = createOrder("1", "order1", List.of(p1, p2, p3, p4));
        Order o2 = createOrder("2", "order2", List.of(p5, p6));
        Order o3 = createOrder("3", "order3", List.of(p7));

        List<Pair<String, Double>> result = exerciseStreamApi.findAveragePrice(List.of(o1, o2, o3), 2);
        System.out.println(result);

        assertEquals(4, result.size());

        result.forEach(stringDoublePair -> {

            if (stringDoublePair.getKey().equals(p1.getName())) {
                assertThat(Precision.equals(105.23, stringDoublePair.getValue(), 0.000001d)).isTrue();
            }

            if (stringDoublePair.getKey().equals(p2.getName())) {
                assertThat(Precision.equals(20.44, stringDoublePair.getValue(), 0.000001d)).isTrue();
            }

            if (stringDoublePair.getKey().equals(p3.getName())) {
                assertThat(Precision.equals(5.11, stringDoublePair.getValue(), 0.000001d)).isTrue();
            }


            if (stringDoublePair.getKey().equals(p6.getName())) {
                assertThat(Precision.equals(5.11, stringDoublePair.getValue(), 0.000001d)).isTrue();
            }

        });

    }

    private Order createOrder(String orderId, String customerName, List<Product> products) {

        Order order = new Order();

        order.setOrderId(orderId);
        order.setCustomerName(customerName);
        order.setProducts(products);

        return order;

    }

    private Product createProduct(String name, Integer quantity, Double price) {

        Product product = new Product();

        product.setName(name);
        product.setQuantity(quantity);
        product.setPrice(price);

        return product;

    }


}