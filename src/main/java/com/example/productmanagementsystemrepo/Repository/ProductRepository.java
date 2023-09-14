package com.example.productmanagementsystemrepo.Repository;

import com.example.productmanagementsystemrepo.Model.MeeshoProducts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<MeeshoProducts, String > {
    public MeeshoProducts findByProductId(String  productId);
    public MeeshoProducts findByProductIdAndSupplierId(String  productId, Integer supplierId);

//    @Modifying
    @Transactional
    public void deleteByProductIdAndSupplierId(String  productId, Integer supplierId);
    public List<MeeshoProducts> findBySupplierId(Integer supplierId);
}
