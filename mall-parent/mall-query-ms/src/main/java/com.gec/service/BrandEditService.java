package com.gec.service;


import com.gec.mall.pojo.TbBrand;

public interface BrandEditService {
    /**
     *  保存(添加、编辑)
     * @param tbBrand
     */
    void saveBrand(TbBrand tbBrand);

    /**
     *根据主键删除
     * @param id
     */
    void deleteBrand(Long id);
}
