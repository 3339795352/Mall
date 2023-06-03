package com.gec.controller;

import com.gec.mall.pojo.*;
import com.gec.service.BrandQueryService;
import com.gec.service.SpecificationQueryService;
import com.gec.service.TemplateQueryService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;

@RestController
public class TemplateQueryController {
    @Autowired
    private TemplateQueryService templateQueryService;
    @GetMapping("/TemplateByPage")
    public PageResp  TemplateByPage(PageReq pageReq){

        PageHelper.startPage(pageReq.getPage(),pageReq.getSize());

        Page<TbTypeTemplate> page= (Page<TbTypeTemplate>) templateQueryService.queryTemplate();

        PageResp pageResp = new PageResp();
        pageResp.setTotal(page.getTotal());
        pageResp.setList(page.getResult());


        return pageResp;
    }
    @GetMapping("/findTemplateById/{id}")
    public List<TbTypeTemplate> findTemplateById(@PathVariable Long id){

        return templateQueryService.findTemplateById(id);
    }
}
