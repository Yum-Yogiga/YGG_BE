package com.yogiga.yogiga.restaurant.csv;

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
    private String menu2;
    private String price2;
    private String menu3;
    private String price3;
    private String menu4;
    private String price4;

    // Getter and Setter for menu and price based on index (1, 2, or 3)
    public String getMenuName(int index) {
        switch (index) {
            case 1:
                return menu1;
            case 2:
                return menu2;
            case 3:
                return menu3;
            case 4:
                return menu4;
            default:
                return null;
        }
    }

    public String getMenuPrice(int index) {
        switch (index) {
            case 1:
                return price1;
            case 2:
                return price2;
            case 3:
                return price3;
            case 4:
                return price4;
            default:
                return "0";
        }
    }

}
