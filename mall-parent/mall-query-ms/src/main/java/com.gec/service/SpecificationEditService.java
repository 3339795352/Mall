package com.gec.service;

import com.gec.mall.pojpgroup.Specification;

public interface SpecificationEditService {
    /**
     * 添加
     * @param specification
     */
    void addSpecification(Specification specification);

    /**
     * 根据主键删除
     * @param id
     */
    void deleteSpecification(Long id);

    /**
     * 修改
     * @param specification
     */
    void updateSpecification(Specification specification);
}
