package com.example.delivery.entity;

import com.example.delivery.entity.enums.TgState;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class TgUser {
    @Id
    private Long chatId;
    private String fullName;
    private String phone;
    private TgState state;

}
