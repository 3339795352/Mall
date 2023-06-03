package com.gec.service.impl;

import com.gec.mall.mapper.TbContentCategoryMapper;
import com.gec.mall.pojo.TbContentCategory;
import com.gec.service.ContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {
    @Autowired
    TbContentCategoryMapper tbContentCategoryMapper;

    @Override
    public List<TbContentCategory> findAllContentCategory() {
        return tbContentCategoryMapper.selectByExample(null);
    }
}
