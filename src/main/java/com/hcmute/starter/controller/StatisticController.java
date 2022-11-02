package com.hcmute.starter.controller;

import com.hcmute.starter.service.OrderService;
import com.hcmute.starter.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/statistic")
@RequiredArgsConstructor
public class StatisticController {

    private final OrderService orderService;

    private final ProductService productService;

    @GetMapping("")
    public Map<String, Object> index() {
        Map<String, Object> data = new HashMap<>();
        data.put("countProducts", productService.countProduct());
        data.put("countOrder", orderService.countOrder());
        return data;
    }
}