package id.ac.ui.cs.advprog.eshop.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;

public class OrderTest {
    private List<Product> products;

    @BeforeEach
    void setUp() {
        this.products = new ArrayList<>();
        Product product1 = new Product();
        product1.setProductId("e1e632fa-085d-4320-ad66-6c719315627b");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(2);
        Product product2 = new Product();
        product2.setProductId("3d4d8859-9d9f-4ab0-879f-531a580a5594");
        product2.setProductName("Sampo Cap Usep");
        product2.setProductQuantity(1);

        this.products.add(product1);
        this.products.add(product2);
    }

    @Test
    void testCreateOrderEmptyProduct() {
        this.products.clear();
        assertThrows(IllegalArgumentException.class, () -> {
            Order order = new Order("31342159-9d9f-4ab0-879f-531a580a5594",
                    this.products, 1708560000L, "Safira Sudrajat");
        });
    }

    @Test
    void testCreateOrderDefaultStatus() {
        Order order = new Order("31342159-9d9f-4ab0-879f-531a580a5594",
                this.products, 1708560000L, "Safira Sudrajat");

        assertSame(this.products, order.getProducts());
        assertEquals(2, order.getProducts().size());
        assertEquals("Sampo Cap Bambang", order.getProducts().get(0).getProductName());
        assertEquals("Sampo Cap Usep", order.getProducts().get(1).getProductName());

        assertEquals("31342159-9d9f-4ab0-879f-531a580a5594", order.getId());
        assertEquals(1708560000L, order.getOrderTime());
        assertEquals("Safira Sudrajat", order.getAuthor());
        assertEquals(OrderStatus.WAITING_PAYMENT.getValue(), order.getStatus());
    }

    @Test
    void testCreateOrderSuccessStatus() {
        Order order = new Order("31342159-9d9f-4ab0-879f-531a580a5594",
                this.products, 1708560000L, "Safira Sudrajat", OrderStatus.SUCCESS.getValue());

        assertEquals(OrderStatus.SUCCESS.getValue(), order.getStatus());
    }

    @Test
    void testCreateOrderInvalidStatus() {
        assertThrows(IllegalArgumentException.class, () -> {
            Order order = new Order("31342159-9d9f-4ab0-879f-531a580a5594",
                    this.products, 1708560000L, "Safira Sudrajat", "MEOW");
        });
    }

    @Test
    void testSetStatusToCancelled() {
        Order order = new Order("31342159-9d9f-4ab0-879f-531a580a5594",
                this.products, 1708560000L, "Safira Sudrajat");

        order.setStatus("CANCELLED");
        assertEquals("CANCELLED", order.getStatus());
    }

    @Test
    void testSetStatusToInvalidStatus() {
        Order order = new Order("31342159-9d9f-4ab0-879f-531a580a5594",
                this.products, 1708560000L, "Safira Sudrajat");

        assertThrows(IllegalArgumentException.class, () -> order.setStatus("MEOW"));
    }

}
