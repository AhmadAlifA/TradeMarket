package com.TradeMarket.dao;

import com.TradeMarket.dto.DropdownDTO;
import com.TradeMarket.dto.product.ProductGridDTO;
import com.TradeMarket.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("""
        SELECT new com.TradeMarket.dto.product.ProductGridDTO
            (pro.productId, 
            pro.productName, 
            pro.priceForBuy, 
            pro.priceForSell, 
            pro.satuan, 
            pro.category)
        FROM Product AS pro
        WHERE pro.productName LIKE %:cariNama%
        """)
    public Page<ProductGridDTO> findByName(@Param("cariNama") String searchName, Pageable pageable);

    @Query("""
			SELECT new com.TradeMarket.dto.DropdownDTO(pro.productId, pro.productName)
			FROM Product AS pro
			ORDER By pro.productId 
			""")
    public List<DropdownDTO> getAllProductById();
}
