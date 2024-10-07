package com.itx.provider;

import com.itx.core.model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JPABrandRepository extends JpaRepository<Brand, Integer> {
}
