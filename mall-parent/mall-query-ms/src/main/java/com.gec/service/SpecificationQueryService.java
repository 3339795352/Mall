package com.gec.service;


import com.gec.mall.pojo.TbSpecification;
import com.gec.mall.pojo.TbSpecificationOption;
import com.gec.mall.pojpgroup.Specification;

import java.util.List;

public interface SpecificationQueryService {
    /**
     * 查询所有规格列表
     * @return
     */
    List<TbSpecification> querySpecification();


   Specification querySpecifications(Long id);
}
