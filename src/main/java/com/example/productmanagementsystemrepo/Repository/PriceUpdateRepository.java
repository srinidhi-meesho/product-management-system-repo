package com.example.productmanagementsystemrepo.Repository;

import com.example.productmanagementsystemrepo.Model.PriceUpdateRequests;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PriceUpdateRepository extends JpaRepository<PriceUpdateRequests, Integer> {

    List<PriceUpdateRequests> findByCreatedAtBetween(LocalDateTime sixtyMinutesBack, LocalDateTime now);
}
