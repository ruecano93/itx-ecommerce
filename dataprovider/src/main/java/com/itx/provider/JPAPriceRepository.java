package com.itx.provider;

import com.itx.core.model.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface JPAPriceRepository extends JpaRepository<Price, Long> {

    @Query("SELECT p FROM Price p " +
            "WHERE p.product.id = :productId " +
            "AND p.brand.id = :brandId " +
            "AND :date BETWEEN p.startDate AND p.endDate " +
            "ORDER BY p.priority DESC LIMIT 1")
    Optional<Price> findBestPrice(@Param("brandId") Integer brandId,
                                  @Param("productId") Long productId,
                                  @Param("date") LocalDateTime date);

}
