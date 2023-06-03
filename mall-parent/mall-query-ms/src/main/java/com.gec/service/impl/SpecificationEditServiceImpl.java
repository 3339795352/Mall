package com.gec.service.impl;


import com.gec.mall.mapper.TbSpecificationMapper;
import com.gec.mall.mapper.TbSpecificationOptionMapper;

import com.gec.mall.pojo.TbSpecification;
import com.gec.mall.pojo.TbSpecificationOption;
import com.gec.mall.pojo.TbSpecificationOptionExample;
import com.gec.mall.pojpgroup.Specification;
import com.gec.service.SpecificationEditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpecificationEditServiceImpl implements SpecificationEditService {

    @Autowired
    private TbSpecificationMapper specificationMapper;

    @Autowired
    private TbSpecificationOptionMapper specificationOptionMapper;

    /**
     * 增加
     * 传参：规格组合类
     *
     * @param specification
     */
    @Override
    public void addSpecification(Specification specification) {
        //保存规格 一方的数据， 规格 对象没有组件

        TbSpecification s = specification.getSpecification();
        specificationMapper.insert(s); //主键回填
        //保存规格选项 多方的数据
        for (TbSpecificationOption specificationOption : specification.getSpecificationOptionList()) {
            //设置规格的ID：主键回填
            specificationOption.setSpecId(specification.getSpecification().getId());

            specificationOptionMapper.insert(specificationOption);
        }
    }

    @Override
    public void deleteSpecification(Long id) {

    }

    /**
     * 修改
     *
     * @param specification
     */
    @Override
    public void updateSpecification(Specification specification) {
        //修改规格一方
        specificationMapper.updateByPrimaryKey(specification.getSpecification());
        //先删除规格选项，再添加规格选项
        TbSpecificationOptionExample example = new TbSpecificationOptionExample();
        TbSpecificationOptionExample.Criteria criteria = example.createCriteria();

        criteria.andSpecIdEqualTo(specification.getSpecification().getId());

        specificationOptionMapper.deleteByExample(example);

        //保存规格选项
        for (TbSpecificationOption specificationOption : specification.getSpecificationOptionList()) {
            //设置规格的ID：
            specificationOption.setSpecId(specification.getSpecification().getId());

            specificationOptionMapper.insert(specificationOption);
        }
    }


}
