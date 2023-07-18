package com.yogiga.yogiga.keyword.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Keyword {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "keyword", cascade = CascadeType.MERGE, orphanRemoval = true)
    private List<RestaurantKeyword> restaurantKeywords = new ArrayList<>();

    @OneToMany(mappedBy = "keyword", cascade = CascadeType.MERGE, orphanRemoval = true)
    private List<UserKeyword> userKeywords = new ArrayList<>();


}
