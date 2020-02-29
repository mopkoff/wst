package com.itmo.wst.repository;

import com.itmo.wst.model.Wine;

import java.util.List;

public interface WineRepositoryCustom {
    List<Wine> findByWine(Wine wine);
}
