package com.project.rabs.restaurant.website.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;

@Data
@Document(collection = "orders")
public class Order {
    @Id
    private int orderId;
    private int customerId;
    private List<OrderLine> items;
    private float total_price;
    private OrderStatus order_status;
    private PaymentStatus payment_status;
    private PaymentType payment_type;
    private Instant timestamp;

    @Data
    public static class OrderLine {
        @DBRef
        private Menu menuItem;
        private int quantity;
    }

    public enum PaymentType { CARD, CASH }
    public enum PaymentStatus { UNPAID, PAID, REFUNDED }
    public enum OrderStatus  { PENDING, PREPARING, COMPLETED, CANCELLED }
}




