package com.gec.service.impl;



import com.gec.mall.mapper.TbBrandMapper;
import com.gec.service.BrandEditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class BrandEditServiceImpl implements BrandEditService {

    @Autowired
    private TbBrandMapper tbBrandMapper;

    @Override
    public void saveBrand(com.gec.mall.pojo.TbBrand tbBrand) {
        //判断tbBrand id
        if(ObjectUtils.isEmpty(tbBrand.getId())){

            tbBrandMapper.insert(tbBrand);

        }else {

            tbBrandMapper.updateByPrimaryKey(tbBrand);
        }
    }

    @Override
    public void deleteBrand(Long id) {
        tbBrandMapper.deleteByPrimaryKey(id);
    }
}
