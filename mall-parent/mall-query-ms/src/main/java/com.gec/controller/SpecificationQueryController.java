package com.gec.controller;


import com.gec.mall.pojo.PageReq;
import com.gec.mall.pojo.PageResp;
import com.gec.mall.pojo.TbSpecificationOption;
import com.gec.mall.pojpgroup.Specification;
import com.gec.service.SpecificationQueryService;
import com.gec.mall.pojo.TbSpecification ;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SpecificationQueryController {

    @Autowired
    private SpecificationQueryService specificationQueryService;

    @GetMapping("/querySpecification")
    public List<TbSpecification> querySpecification(){
        return specificationQueryService.querySpecification();
    }

    @GetMapping("/SpecificationByPage")
    public PageResp<TbSpecification> listByPage(PageReq pageReq){
        PageHelper.startPage(pageReq.getPage(),pageReq.getSize());
        Page<TbSpecification> page = (Page<TbSpecification>) specificationQueryService.querySpecification();

        PageResp pageResp = new PageResp();
        pageResp.setTotal(page.getTotal());
        pageResp.setList(page.getResult());

        return pageResp;
    }

    @GetMapping("/querySpecification_option/{id}")
    public Specification listSpecOptions(@PathVariable Long id){
        Specification specification=specificationQueryService.querySpecifications(id);

        return specification;
    }

}
