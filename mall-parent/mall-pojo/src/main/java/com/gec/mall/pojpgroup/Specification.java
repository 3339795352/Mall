package com.gec.mall.pojpgroup;

import com.gec.mall.pojo.TbSpecification;
import com.gec.mall.pojo.TbSpecificationOption;

import java.io.Serializable;
import java.util.List;

/**
 * 规格组合实体类
 *
 * 前端 ： json={   specificationOptionList:[]   }
 * @author Administrator
 *
 */
public class Specification implements Serializable {

    private TbSpecification specification;   //tb_specification表

    private List<TbSpecificationOption> specificationOptionList; //TbSpecificationOption  ${tSpecificationOption}

    public TbSpecification getSpecification() {
        return specification;
    }

    public void setSpecification(TbSpecification specification) {
        this.specification = specification;
    }

    public List<TbSpecificationOption> getSpecificationOptionList() {
        return specificationOptionList;
    }

    public void setSpecificationOptionList(List<TbSpecificationOption> specificationOptionList) {
        this.specificationOptionList = specificationOptionList;
    }

}
