package com.TradeMarket.rest;

import com.TradeMarket.dto.order.ErrorDTO;
import com.TradeMarket.dto.order.UpsertOrderDTO;
import com.TradeMarket.service.OrderService;
import com.TradeMarket.utility.MapperHelper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@CrossOrigin
@RestController
@RequestMapping("/api/order")
public class OrderRestController {

    @Autowired
    OrderService service;

    @GetMapping
    public ResponseEntity<Object> get(@RequestParam(defaultValue = "1") Integer page,
                                      @RequestParam(defaultValue = "") String searchName){
        try{
            var grid = service.getOrderGrid(page, searchName);
            return ResponseEntity.status(200).body(grid);
        } catch (Exception exception){
            var cause = exception.getCause().getCause().toString();
            var errorObject = new ErrorDTO(
                    cause,
                    exception.getMessage(),
                    LocalDateTime.now()
            );
            return ResponseEntity.status(500).body(errorObject);
        }
    }

    @PostMapping
    public ResponseEntity<Object> post(@Valid @RequestBody UpsertOrderDTO dto, BindingResult bindingResult){
        if(!bindingResult.hasErrors()){
            var hasilEntity = service.saveOrder(dto);
            return ResponseEntity.status(201).body(hasilEntity);
        }
        var validationErrors = bindingResult.getAllErrors();
        var formattedErrors = MapperHelper.getErrors(validationErrors);
        return ResponseEntity.status(422).body(formattedErrors);
    }

    @PutMapping
    public ResponseEntity<Object> put(@Valid @RequestBody UpsertOrderDTO dto, BindingResult bindingResult){
        var hasilEntity = service.getUpdate(dto.getOrderId());
        return ResponseEntity.status(200).body(hasilEntity);
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<Object> delete(@PathVariable(required = true) Long orderId){
            service.deleteOrder(orderId);
            return ResponseEntity.status(204).body(null);
    }

}
