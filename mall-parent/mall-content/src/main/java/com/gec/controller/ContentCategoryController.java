package com.gec.controller;

import com.gec.mall.pojo.TbContentCategory;
import com.gec.service.ContentCategoryService;
import com.netflix.ribbon.proxy.annotation.Content;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ContentCategoryController {
    @Autowired
    ContentCategoryService contentCategoryService;
    @GetMapping("/findAllContentCategory")
    public List<TbContentCategory> findAllContentCategory(){
        List<TbContentCategory> list=contentCategoryService.findAllContentCategory();

        return list;
    }
}
