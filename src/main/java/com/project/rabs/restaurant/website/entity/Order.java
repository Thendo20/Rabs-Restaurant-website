package com.project.rabs.restaurant.website.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Data
@Document(collection = "orders")
public class Order {

    @Id
    @Field("order_id")
    private String orderId;

    @Field("customer_id")
    private String customerId;

    private List<OrderLine> items;

    @Field("total_price")
    private BigDecimal totalPrice;

    @Field("order_status")
    private OrderStatus orderStatus;

    @Field("payment_status")
    private PaymentStatus paymentStatus;

    @Field("payment_type")
    private PaymentType paymentType;
    private Instant timestamp;

    @Data
    public static class OrderLine {
        @DBRef
        private Menu menuItem;
        private int quantity;
    }

    public enum OrderStatus   { PENDING, PREPARING, COMPLETED, CANCELLED }

    public enum PaymentStatus { UNPAID, PAID, REFUNDED }

    public enum PaymentType   { CARD, CASH }
}
