package com.example.producingwebservice;

import javax.annotation.PostConstruct;
import java.util.HashMap;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class WineRepository {
	private static final Map<String, Wine> countries = new HashMap<>();

	@PostConstruct
	public void initData() {

	}

	public Wine findWine(String name) {
		Assert.notNull(name, "The wine's name must not be null");
		return countries.get(name);
	}
}
