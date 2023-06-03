package com.gec.service.impl;


import com.gec.mall.mapper.TbBrandMapper;
import com.gec.mall.pojo.TbBrand;
import com.gec.service.BrandQueryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandQueryServiceImpl implements BrandQueryService {

    @Autowired
    private TbBrandMapper tbBrandMapper;


    @Override
    public List<TbBrand> queryBrand() {
        return tbBrandMapper.selectByExample(null);
    }

    @Override
    public TbBrand findBrandById(Integer id) {
        return null;
    }
}
