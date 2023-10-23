package com.yogiga.yogiga.restaurant.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResCsvDto {
    private String name;

    private double tasty;//음식이 맛있어요
    private double friendly;//친절해요
    private double specialMenu;//특별한 메뉴가 있어요
    private double clean;//매장이 청결해요
    private double freshIngredients;//재료가 신선해요
    private double costEffective;//가성비가 좋아요
    private double generousPortions;//양이 많아요
    private double greatInterior;//인테리어가 멋져요
    private double goodForSolo;//혼밥하기 좋아요

    private String link;
    private String address;
    private String tel;
    private String menu1;
    private String price1;
    private String image1;
    private String menu2;
    private String price2;
    private String image2;
    private String menu3;
    private String price3;
    private String image3;
    private String menu4;
    private String price4;
    private String image4;

    // Getter and Setter for menu and price based on index (1, 2, or 3)
    public String getMenuName(int index) {
        return switch (index) {
            case 1 -> menu1;
            case 2 -> menu2;
            case 3 -> menu3;
            case 4 -> menu4;
            default -> null;
        };
    }

    public String getMenuPrice(int index) {
        return switch (index) {
            case 1 -> price1;
            case 2 -> price2;
            case 3 -> price3;
            case 4 -> price4;
            default -> "0";
        };
    }

    public String getMenuImage(int index) {
        return switch (index) {
            case 1 -> image1;
            case 2 -> image2;
            case 3 -> image3;
            case 4 -> image4;
            default -> "0";
        };
    }

}
