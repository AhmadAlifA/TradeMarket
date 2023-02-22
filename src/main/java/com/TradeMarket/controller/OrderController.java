package com.TradeMarket.controller;

import com.TradeMarket.dto.order.UpsertOrderDTO;
import com.TradeMarket.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService service;

    @GetMapping("/index")
    public String index(@RequestParam(defaultValue =  "1") Integer page,
                        @RequestParam(defaultValue =  "") String searchName,
                        Model model){
        var grid = service.getOrderGrid(page, searchName);
        var totalHalaman = grid.getTotalPages();
        model.addAttribute("dataGrid",grid);
        model.addAttribute("totalPage",totalHalaman);
        model.addAttribute("currentPage",page);
        model.addAttribute("searchName", searchName);

        return "order/order-index";
    }

    @GetMapping("/upsertForm")
    public String insert(@RequestParam(required = false) Long orderId, Model model){
//        try{
            String actionType = "Insert";
            var dto = new UpsertOrderDTO();
            var productDropdown = service.getProductDropdown();
            if(orderId!=null){
                actionType = "Update";
                dto = service.getUpdate(orderId);
            }
            model.addAttribute("actionType",actionType);
            model.addAttribute("dto",dto);
            model.addAttribute("productDropdown",productDropdown);
            return "order/upsert-order";
//        }catch (Exception e){
//            return "redirect:/error/server";
//        }

    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("dto") UpsertOrderDTO dto,
                       BindingResult bandingResult, Model model) {
//        try{
            if (bandingResult.hasErrors()){
                var productDropdown = service.getProductDropdown();
                model.addAttribute("productDropdown",productDropdown);
                model.addAttribute("actionType","Insert");
                if (dto.getOrderId()!= null){
                    model.addAttribute("actionType","Update");
                }
                return "order/upsert-order";
            }
            service.saveOrder(dto);
            return "redirect:/order/index";
//        }catch (Exception e){
//            return "redirect:/error/server";
//        }
    }

    @GetMapping("/delete")
    public String delete(@RequestParam(required = true) Long orderId, RedirectAttributes redirectAttributes){
        try {
            service.deleteOrder(orderId);
            return "redirect:/order/index";
        }catch (Exception exception){
            String errorMessage = String.format("Jenis Exception : %s", exception.getCause().getCause());
            redirectAttributes.addAttribute("message", errorMessage);
            return "redirect:/error/server";
        }
    }
}
