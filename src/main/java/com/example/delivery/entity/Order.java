package com.example.delivery.entity;

import com.example.delivery.entity.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @CreationTimestamp
    private LocalDateTime dateTime;
    @ManyToOne
    private TgUser user;

    @ManyToOne
    private User operator;

    @ManyToOne
    private Location location;
    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

}
