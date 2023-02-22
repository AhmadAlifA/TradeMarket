package com.TradeMarket.rest;

import com.TradeMarket.dto.order.ErrorDTO;
import com.TradeMarket.dto.product.UpsertProductDTO;
import com.TradeMarket.service.ProductService;
import com.TradeMarket.utility.MapperHelper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@CrossOrigin
@RestController
@RequestMapping("/api/product")
public class ProductRestController {

    @Autowired
    ProductService service;

    @GetMapping
    public ResponseEntity<Object> get(@RequestParam(defaultValue = "1") Integer page,
                                      @RequestParam(defaultValue = "") String name){
        try{
            var grid = service.getProductGrid(page, name);
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
    public ResponseEntity<Object> post(@Valid @RequestBody UpsertProductDTO dto, BindingResult bindingResult){
        if(!bindingResult.hasErrors()){
            var hasilEntity = service.saveProduct(dto);
            return ResponseEntity.status(201).body(hasilEntity);
        }
        var validationErrors = bindingResult.getAllErrors();
        var formattedErrors = MapperHelper.getErrors(validationErrors);
        return ResponseEntity.status(422).body(formattedErrors);
    }

    @PutMapping
    public ResponseEntity<Object> put(@Valid @RequestBody UpsertProductDTO dto, BindingResult bindingResult){
        var hasilEntity = service.getUpdate(dto.getProductId());
        return ResponseEntity.status(200).body(hasilEntity);
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<Object> delete(@PathVariable(required = true) Long productId){
        try{
            service.delete(productId);
            return ResponseEntity.status(204).body(null);
        }catch (Exception exception){
            return ResponseEntity.status(500).body(String.format("Tidak bisa hapus, ada product yang related"));
        }
    }
}
