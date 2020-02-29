package com.itmo.wst.model;

import org.springframework.util.StringUtils;

public class Helper {

    public static Wine createNewWine(CreateWineRequest request) {
        Wine wine = new Wine();
        wine.setName(request.getName());
        wine.setSugar(request.getSugar());
        wine.setColor(request.getColor());
        wine.setRating(request.getRating());
        return wine;
    }

    public static void updateWine(Wine wineToUpdate, UpdateWineRequest request) {
        Wine requestWine = request.getWine();
        if (!StringUtils.isEmpty(requestWine.getName()))
            wineToUpdate.setName(requestWine.getName());
        if (!StringUtils.isEmpty(requestWine.getSugar()))
            wineToUpdate.setSugar(requestWine.getSugar());
        if (!StringUtils.isEmpty(requestWine.getColor()))
            wineToUpdate.setColor(requestWine.getColor());
        if (!StringUtils.isEmpty(requestWine.getRating()))
            wineToUpdate.setRating(requestWine.getRating());
    }
}
