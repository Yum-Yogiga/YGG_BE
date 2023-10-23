package com.yogiga.yogiga.restaurant.entity;

import com.yogiga.yogiga.global.entity.BaseTimeEntity;
import com.yogiga.yogiga.restaurant.dto.RestaurantDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Restaurant extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    private String name;

    private String link;

    @Column(nullable = false)
    private String address;

    private String tel;

    private String openingHours;
    @ColumnDefault("0")
    @Column(nullable = false)
    private Integer likeCount;
    @ColumnDefault("0")
    @Column(nullable = false)
    private Integer dislikeCount;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Menu> menuList = new ArrayList<>();

    @Builder
    public static Restaurant toEntity(RestaurantDto restaurantDto) {
        return Restaurant.builder()
                .name(restaurantDto.getName())
                .address(restaurantDto.getAddress())
                .tel(restaurantDto.getTel())
                .openingHours(restaurantDto.getOpeningHours())
                .likeCount(0)
                .dislikeCount(0)
                .menuList(restaurantDto.getMenuDtoList().stream()
                        .map(Menu::toEntity)
                        .collect(Collectors.toList()))
                .build();
    }

    public void update(RestaurantDto restaurantDto) {
        this.name = restaurantDto.getName();
        this.address = restaurantDto.getAddress();
        this.tel = restaurantDto.getTel();
        this.openingHours = restaurantDto.getOpeningHours();
    }

    public void plusLikeCount() {
        this.likeCount++;
    }

    public void plusDislikeCount() {
        this.dislikeCount++;
    }

    public void setMenuList(List<Menu> menuList) {
        this.menuList = menuList;
    }

}
