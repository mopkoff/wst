package com.itmo.wst.repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import com.itmo.wst.model.Wine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//@Component
@Repository
public interface WineRepository extends JpaRepository<Wine, BigInteger> {
	Optional<Wine> findById(BigInteger id);
	List<Wine> findAllByNameAndId(String name, BigInteger id);
	List<Wine> findAllByName(String name);

//	private static final Map<String, Wine> wines = new HashMap<>();
//
//	@PostConstruct
//	public void initData() {
//
//	}
//
//	public Wine findWine(String name) {
//		Assert.notNull(name, "The wine's name must not be null");
//		return wines.get(name);
//	}
}
