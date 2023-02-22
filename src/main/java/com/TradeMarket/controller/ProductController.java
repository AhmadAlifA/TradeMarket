package com.TradeMarket.controller;

import com.TradeMarket.dto.product.UpsertProductDTO;
import com.TradeMarket.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping("/index")
    public String index(@RequestParam(defaultValue = "1") Integer page,
                        @RequestParam(defaultValue = "") String name,
                        Model model){
        try{
        var grid = service.getProductGrid(page, name);
        var totalHalaman = grid.getTotalPages();
        model.addAttribute("dataGrid", grid);
        model.addAttribute("totalPage", totalHalaman);
        model.addAttribute("currentPage", page);
        model.addAttribute("searchName", name);
        return "product/product-index";
        }catch (Exception exception){
            return "redirect:/error/serverError";
        }
    }

    @GetMapping("/upsertForm")
    public String insert(@RequestParam(required = false) Long productId, Model model){
        try {
            UpsertProductDTO dto = new UpsertProductDTO();
            if(productId!=null){
                dto = service.getUpdate(productId);
                if (service.totalDependencies(productId) > 0){
                    model.addAttribute("terbaik","Order");
                    model.addAttribute("dihapus","Product");
                    return "delete-error";
                }
                dto = service.getUpdate(productId);
            }
            model.addAttribute("breadCrumbs","Insert/Update");
            model.addAttribute("dto",dto);
            return "product/upsert-product";
        }catch (Exception exception){
            return "redirect:/error/server";
        }
    }
    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("dto") UpsertProductDTO dto,
                       BindingResult bandingResult) {
        try {
            if (bandingResult.hasErrors()){
                return "product/upsert-product";
            }
            service.saveProduct(dto);
            return "redirect:/product/index";
        }catch (Exception exception){
            return "redirect:/error/server";
        }
    }

    @GetMapping("/delete")
    public String delete(@RequestParam(required = true) Long productId, Model model, RedirectAttributes redirectAttributes){
        try {
            Long totalDependecies = service.delete(productId);
            if (totalDependecies == 0){
                return "redirect:/product/index";
            }
            model.addAttribute("dependencies",totalDependecies);
            model.addAttribute("terbaik","Order");
            model.addAttribute("dihapus","Product");
            return "delete-error";
        }catch (Exception exception){
            String errorMessage = String.format("Jenis Exception : %s", exception.getCause().getCause());
            redirectAttributes.addAttribute("message", errorMessage);
            return "redirect:/error/server";
        }
    }
}

