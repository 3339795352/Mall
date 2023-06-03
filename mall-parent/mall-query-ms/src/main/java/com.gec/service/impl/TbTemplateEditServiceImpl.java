package com.gec.service.impl;

import com.gec.mall.mapper.TbTypeTemplateMapper;
import com.gec.mall.pojo.TbTypeTemplate;
import com.gec.service.TbTemplateEditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TbTemplateEditServiceImpl implements TbTemplateEditService {
    @Autowired
    TbTypeTemplateMapper tbTypeTemplateMapper;

    @Override
    public void saveTemplate(TbTypeTemplate tbTypeTemplate) {

        if (tbTypeTemplate.getId() == null) {
            tbTypeTemplateMapper.insert(tbTypeTemplate);
        }else {
            tbTypeTemplateMapper.updateByPrimaryKey(tbTypeTemplate);
        }
    }
}
