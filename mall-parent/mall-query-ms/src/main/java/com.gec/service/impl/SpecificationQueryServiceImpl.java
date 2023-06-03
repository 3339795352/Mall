package com.gec.service.impl;


import com.gec.mall.mapper.TbSpecificationMapper;
import com.gec.mall.mapper.TbSpecificationOptionMapper;
import com.gec.mall.pojo.TbSpecification;
import com.gec.mall.pojo.TbSpecificationExample;
import com.gec.mall.pojo.TbSpecificationOption;
import com.gec.mall.pojo.TbSpecificationOptionExample;
import com.gec.mall.pojpgroup.Specification;
import com.gec.service.SpecificationQueryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpecificationQueryServiceImpl implements SpecificationQueryService {


    @Autowired
    private TbSpecificationMapper tbSpecificationMapper;
    @Autowired
    private TbSpecificationOptionMapper tbSpecificationOptionMapper;

    @Override
    public List<TbSpecification> querySpecification() {
        return tbSpecificationMapper.selectByExample(null);
    }

    @Override
    public Specification querySpecifications(Long id) {
        Specification specification=new Specification();


        TbSpecification tbSpecification=tbSpecificationMapper.selectByPrimaryKey(id);

        specification.setSpecification(tbSpecification);
//        规格选项
        TbSpecificationOptionExample example=new TbSpecificationOptionExample();
        TbSpecificationOptionExample.Criteria criteria=example.createCriteria();
        criteria.andSpecIdEqualTo(id);
        List<TbSpecificationOption> tbSpecificationOptions = tbSpecificationOptionMapper.selectByExample(example);


        tbSpecificationOptions.forEach(System.out::println);
        specification.setSpecificationOptionList(tbSpecificationOptionMapper.selectByExample(example));
        return specification;
    }


}
