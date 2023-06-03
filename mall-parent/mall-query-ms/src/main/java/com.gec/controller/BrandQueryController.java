package com.gec.controller;

import com.gec.mall.pojo.PageReq;
import com.gec.mall.pojo.PageResp;
import com.gec.mall.pojo.TbBrand;
import com.gec.service.BrandQueryService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BrandQueryController {

    @Autowired
    private BrandQueryService brandQueryService;

    @GetMapping("/queryBrand")
    public List<TbBrand> queryBrand(){
        return brandQueryService.queryBrand();
    }

    @GetMapping("/listByPage")
    public PageResp<TbBrand> listByPage(PageReq pageReq){
        PageHelper.startPage(pageReq.getPage(),pageReq.getSize());
        Page<TbBrand> page = (Page<TbBrand>) brandQueryService.queryBrand();

        PageResp pageResp = new PageResp();
        pageResp.setTotal(page.getTotal());
        pageResp.setList(page.getResult());

        return pageResp;
    }
}
