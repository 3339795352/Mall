package com.gec.service.impl;

import com.gec.mall.mapper.TbBrandMapper;
import com.gec.mall.mapper.TbTypeTemplateMapper;

import com.gec.mall.pojo.TbTypeTemplate;
import com.gec.service.TemplateQueryService;
import com.gec.mall.pojo.TbTypeTemplateExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TemplateServiceImpl  implements TemplateQueryService {
    @Autowired
    private TbTypeTemplateMapper tbTypeTemplateMapper;

    @Autowired
    private TbBrandMapper tbBrandMapper;

    @Override
    public List<TbTypeTemplate> queryTemplate() {

        return tbTypeTemplateMapper.selectByExample(null);


    }

    @Override
    public List<TbTypeTemplate> findTemplateById(Long id) {
        TbTypeTemplateExample example=new TbTypeTemplateExample();
        TbTypeTemplateExample.Criteria criteria=example.createCriteria();
        criteria.andIdEqualTo(id);
        List<TbTypeTemplate> tbTypeTemplates = tbTypeTemplateMapper.selectByExample(example);

        return tbTypeTemplates;
    }
}
