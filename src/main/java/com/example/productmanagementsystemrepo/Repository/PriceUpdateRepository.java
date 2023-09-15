package com.example.productmanagementsystemrepo.Repository;

import com.example.productmanagementsystemrepo.Model.PriceUpdateRequest;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PriceUpdateRepository extends JpaRepository<PriceUpdateRequest, Integer> {
    @Query(value = "SELECT * FROM price_update_requests p where" +
                   " p.created_at between :twentyFourHoursBack and :now" +
                   " limit :batchSize" +
                   " offset :offset", nativeQuery = true)
    List<PriceUpdateRequest> findByCreatedAtBetween(@Param("twentyFourHoursBack") LocalDateTime twentyFourHoursBack,@Param("now") LocalDateTime now,@Param("batchSize") Integer batchSize,@Param("offset") Integer offset);
}
