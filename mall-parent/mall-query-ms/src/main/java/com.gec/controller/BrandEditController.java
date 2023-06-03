package com.gec.controller;

import com.gec.mall.pojo.TbBrand ;
import com.gec.service.BrandEditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class BrandEditController {

    @Autowired
    private BrandEditService brandEditService;

    @PostMapping("/saveBrand")
    public void saveBrand(@RequestBody TbBrand tbBrand){



        brandEditService.saveBrand(tbBrand);


    }

    @DeleteMapping("/deleteBrand/{id}")
    public void deleteBrand(@PathVariable Long id){
        brandEditService.deleteBrand(id);
    }
}
