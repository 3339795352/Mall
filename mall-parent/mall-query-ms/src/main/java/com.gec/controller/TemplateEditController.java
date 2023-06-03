package com.gec.controller;

import com.gec.mall.pojo.TbSpecification;
import com.gec.mall.pojo.TbTypeTemplate;
import com.gec.service.TbTemplateEditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TemplateEditController {
    @Autowired
    TbTemplateEditService tbTemplateEditService;
    @PostMapping("/saveTemplate")
    public void  saveTemplate(@RequestBody TbTypeTemplate tbTypeTemplate){

            tbTemplateEditService.saveTemplate(tbTypeTemplate);

    }
}
