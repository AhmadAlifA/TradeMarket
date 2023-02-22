package com.TradeMarket.service;

import com.TradeMarket.dao.OrderRepository;
import com.TradeMarket.dao.ProductRepository;
import com.TradeMarket.dto.DropdownDTO;
import com.TradeMarket.dto.order.OrderGridDTO;
import com.TradeMarket.dto.order.UpsertOrderDTO;
import com.TradeMarket.dto.product.ProductGridDTO;
import com.TradeMarket.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    public Page<OrderGridDTO> getOrderGrid(Integer pageNumber, String searchName){
        var pageable = PageRequest.of(pageNumber - 1, 10, Sort.by("orderId"));
        var hasilGrid = orderRepository.getOrder(searchName, pageable);
        return hasilGrid;
    }

    public List<DropdownDTO> getProductDropdown(){
        return productRepository.getAllProductById();
    }

    public UpsertOrderDTO getUpdate(Long id){
        var product = productRepository.findById(id);
        var entity = orderRepository.findById(id).get();
        UpsertOrderDTO dto= new UpsertOrderDTO(
                entity.getOrderId(),
                entity.getOrderDate(),
                entity.getKonsumenName(),
                entity.getProductId(),
                entity.getQuantity(),
                entity.getUnitPrice());
        return dto;
    }

    @Transactional
    public Order saveOrder(UpsertOrderDTO dto){
        var product = productRepository.findById(dto.getProductId()).get();
        var entity = new Order(dto.getOrderId(),
                dto.getOrderDate(),
                dto.getKonsumenName(),
                dto.getProductId(),
                dto.getQuantity(),
                product.getPriceForBuy());
        return orderRepository.save(entity);
    }

    public void deleteOrder(Long orderId) {
        orderRepository.deleteById(orderId);
    }
}
