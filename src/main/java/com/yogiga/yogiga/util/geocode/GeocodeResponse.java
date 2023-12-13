package com.yogiga.yogiga.util.geocode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GeocodeResponse {
    private String status;
    private Meta meta;
    private List<Address> addresses;
    private String errorMessage;


    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Meta {
        private int totalCount;
        private int page;
        private int count;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Address {
        private String roadAddress;
        private String jibunAddress;
        private String englishAddress;
        private List<AddressElement> addressElements;
        private String x;
        private String y;
        private double distance;

        @Getter
        @Setter
        @AllArgsConstructor
        @NoArgsConstructor
        public static class AddressElement {
            private List<String> types;
            private String longName;
            private String shortName;
            private String code;
        }
    }
}


