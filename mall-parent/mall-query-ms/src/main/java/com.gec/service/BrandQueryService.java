package com.gec.service;

import com.gec.mall.pojo.TbBrand;

import java.util.List;

public interface BrandQueryService {
    /**
     * 查询所有品牌列表
     * @return
     */
    List<TbBrand> queryBrand();

    /**
     * 根据id查询单条品牌
     * @param id
     * @return
     */
    TbBrand findBrandById(Integer id);




}
