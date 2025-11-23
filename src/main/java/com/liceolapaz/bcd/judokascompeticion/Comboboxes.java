package com.liceolapaz.bcd.judokascompeticion;

public enum Comboboxes {
    BELTS("src/main/resources/comboboxes/belts.txt"),
    COUNTRIES("src/main/resources/comboboxes/countries.txt"),
    COMPETITION_TYPES("src/main/resources/comboboxes/competition-types.txt"),;

    private final String url;

    public String getUrl() {
        return url;
    }

    Comboboxes(String url) {
        this.url = url;
    }
}
