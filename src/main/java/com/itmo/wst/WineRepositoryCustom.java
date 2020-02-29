package com.itmo.wst;

import com.itmo.wst.model.Wine;

import java.util.List;

public interface WineRepositoryCustom {
    public List<Wine> findByWine(Wine wine);
}
