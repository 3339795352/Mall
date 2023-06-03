package com.gec.service;

import com.gec.mall.pojo.TbTypeTemplate;
import com.github.pagehelper.Page;

import java.util.List;

public interface TemplateQueryService {
    List<TbTypeTemplate> queryTemplate();

    List<TbTypeTemplate> findTemplateById(Long id);
}
