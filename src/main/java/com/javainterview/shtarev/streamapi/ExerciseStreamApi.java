package com.javainterview.shtarev.streamapi;

import com.javainterview.shtarev.streamapi.dto.Order;
import com.javainterview.shtarev.streamapi.dto.Product;
import org.apache.commons.math3.util.Pair;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class ExerciseStreamApi {

    //  Перед выполнением задач рекомендую почитать теорию из
    //https://habr.com/ru/companies/otus/articles/862134/
    //https://struchkov.dev/blog/ru/java-stream-api/
    //https://sky.pro/wiki/java/ispolzovanie-group-by-i-collect-v-java-stream-api/

    //  Задача 1.
    //  Рассчитать полную стоимость заказа.
    public Double calculateOrderTotalEx(Order order) {

        return null;

    }

    //  Задача 2.
    //  Получить список наименований товаров из заказа.
    public Set<String> getUniqueProductNamesEx(List<Order> orders) {

        return Set.of();

    }

    //  Задача 3.
    //  Вернуть наименования N популярных товаров в порядке уменьшения популярности.
    //  Популярный товар - товар - который меньше остальных покупается.
    public List<String> findMostPopularProductsEx(List<Order> orders, int topN) {


        return List.of();

    }

    //  Задача 4.
    //  Рассчитать сколько заплатили за каждый продукт в заказе (продукт - общая стоимость).
    public List<Pair<String, Double>> findAveragePriceEx(List<Order> orders, int topN) {

        return List.of();

    }


    /**
     * РЕШЕНИЕ
     */

    //  Задача 1.
    //  Рассчитать полную стоимость заказа.

    //  Комментарий к задаче.
    //  Уточнить что у заказа NotNull quantity и price (если это не так, написать доп обработку и умножать только если и то и другое присутствует).
    //  Можно сказать что для стоимости лучше использовать BigDecimal (Удобно работать с суммами, из-за встроенного механизма скейлинга к нужному кол-ву символов после запятой)
    public Double calculateOrderTotal(Order order) {

        return order.getProducts().stream()
                .mapToDouble(product -> product.getPrice() * product.getQuantity())
                .reduce(Double::sum)
                .orElse(0);

    }

    //  Задача 2.
    //  Получить список наименований товаров из заказа.
    public Set<String> getUniqueProductNames(List<Order> orders) {

        return orders.stream()
                .flatMap(order -> order.getProducts().stream())
                .map(Product::getName)
                .collect(Collectors.toSet());

    }

    //  Задача 3.
    //  Вывести наименования N популярных товаров в порядке уменьшения популярности.
    //  Популярный товар - товар - который меньше остальных покупается.
    public List<String> findMostPopularProducts(List<Order> orders, int topN) {


        return orders.stream()
                .flatMap(order -> order.getProducts().stream())
                .collect(Collectors.toMap(Product::getName, i -> 1, Integer::sum))
                .entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(topN)
                .map(Map.Entry::getKey)
                .toList();

    }

    //  Задача 4.
    //  Рассчитать сколько заплатили за каждый продукт в заказе (продукт - общая стоимость).
    public List<Pair<String, Double>> findAveragePrice(List<Order> orders, int topN) {

        return orders.stream()
                .flatMap(order -> order.getProducts().stream())
                .collect(Collectors.groupingBy(Product::getName, Collectors.summarizingDouble(Product::getPrice)))
                .entrySet()
                .stream()
                .map(res -> new Pair<>(res.getKey(), res.getValue().getSum()))
                .toList();

    }


}


