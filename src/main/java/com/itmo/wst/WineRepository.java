package com.itmo.wst;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class WineRepository {
	private static final Map<String, Wine> wines = new HashMap<>();

	@PostConstruct
	public void initData() {

	}

	public Wine findWine(String name) {
		Assert.notNull(name, "The wine's name must not be null");
		return wines.get(name);
	}
}
