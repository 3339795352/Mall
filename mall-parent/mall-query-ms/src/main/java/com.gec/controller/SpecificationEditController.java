package com.gec.controller;

import com.gec.mall.pojo.TbSpecification;
import com.gec.mall.pojpgroup.Specification;
import com.gec.service.SpecificationEditService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpecificationEditController {

    @Autowired
    private SpecificationEditService specificationEditService;

    @PostMapping("/saveSpecification")
    public void  saveSpecification(@RequestBody Specification specification){
        TbSpecification tbSpecification=specification.getSpecification();

        if(tbSpecification.getId()==null){
            specificationEditService.addSpecification(specification);
        }else {
            specificationEditService.updateSpecification(specification);
        }
    }
}
