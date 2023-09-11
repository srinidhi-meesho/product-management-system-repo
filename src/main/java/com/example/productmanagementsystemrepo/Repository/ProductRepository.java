package com.example.productmanagementsystemrepo.Repository;

import com.example.productmanagementsystemrepo.Model.MeeshoProducts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<MeeshoProducts, String > {
    public MeeshoProducts findByProductId(String  ProductId);
    public MeeshoProducts findByProductIdAndSupplierId(String  ProductId, int SupplierId);

    @Modifying
    public void deleteByProductIdAndSupplierId(String  ProductId, int SupplierId);
    public List<MeeshoProducts> findBySupplierId(int SupplierId);
}
