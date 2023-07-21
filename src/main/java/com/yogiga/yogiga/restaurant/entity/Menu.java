package com.yogiga.yogiga.restaurant.entity;

import com.yogiga.yogiga.restaurant.dto.MenuDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String price;

    @ManyToOne(fetch = FetchType.LAZY)
    private Restaurant restaurant;

    public static Menu toEntity(MenuDto menuDto) {
        return Menu.builder()
                .name(menuDto.getName())
                .price(menuDto.getPrice())
                .build();
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}
