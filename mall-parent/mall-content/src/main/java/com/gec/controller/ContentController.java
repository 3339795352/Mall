package com.gec.controller;

import com.gec.mall.pojo.PageReq;
import com.gec.mall.pojo.PageResp;
import com.gec.mall.pojo.TbContent;
import com.gec.mall.pojo.TbContentCategory;
import com.gec.service.ContentService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ContentController {
    @Autowired
    ContentService contentService;
    @GetMapping("/findByCategoryId/{categoryId}")
    public List<TbContent> findByCategoryId(@PathVariable Long categoryId) {
        List<TbContent> contents = contentService.findByCategoryId(categoryId);
        return contents;
    }

    @GetMapping("/contentlistByPage")
    public PageResp<TbContent> contentlistByPage(PageReq pageReq){
        PageHelper.startPage(pageReq.getPage(),pageReq.getSize());
        Page<TbContent> page= (Page<TbContent>) contentService.findAll();
        PageResp pageResp = new PageResp();
        pageResp.setTotal(page.getTotal());
        pageResp.setList(page.getResult());

        return pageResp;

    }
    @PostMapping("/saveContent")
    public void saveContent(@RequestBody  TbContent tbContent){

        System.out.println(tbContent);

        contentService.update(tbContent);

    }

}
