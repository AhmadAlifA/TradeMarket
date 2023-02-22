package com.TradeMarket.service;

import com.TradeMarket.dao.OrderRepository;
import com.TradeMarket.dao.ProductRepository;
import com.TradeMarket.dto.product.ProductGridDTO;
import com.TradeMarket.dto.product.UpsertProductDTO;
import com.TradeMarket.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    public Page<ProductGridDTO> getProductGrid(Integer pageNumber, String searchName){
        var pageable = PageRequest.of(pageNumber - 1, 10, Sort.by("productId"));
        var hasilGrid = productRepository.findByName(searchName, pageable);
        return hasilGrid;
    }

    public Product saveProduct(UpsertProductDTO dto) {
        Product entity = new Product(
                dto.getProductId(),
                dto.getProductName(),
                dto.getPriceForBuy(),
                dto.getPriceForSell(),
                dto.getSatuan(),
                dto.getCategory()
        );
        return productRepository.save(entity);
    }

    public UpsertProductDTO getUpdate(Long productId) {
        var entity = productRepository.findById(productId).get();
        UpsertProductDTO dto = new UpsertProductDTO(
                entity.getProductId(),
                entity.getProductName(),
                entity.getPriceForBuy(),
                entity.getPriceForSell(),
                entity.getSatuan(),
                entity.getCategory()
        );
        return dto;
    }

    public Long totalDependencies(Long productId){
        Long totalProducts = orderRepository.countByProductId(productId);
        return totalProducts;
    }

    public Long delete(Long productId) {
        Long totalProducts = orderRepository.countByProductId(productId);
        if (totalProducts == 0) {
            productRepository.deleteById(productId);
        }
        return totalProducts;
    }
}
