package com.gec.service;

import com.gec.mall.entity.PageResult;
import com.gec.mall.pojo.TbContent;

import java.util.List;

public interface ContentService {
    public List<TbContent> findAll();


    /**
     * 返回分页列表
     * @return
     */
    public PageResult findPage(int pageNum, int pageSize);


    /**
     * 增加
     */
    public void add(TbContent content);


    /**
     * 修改
     */
    public void update(TbContent content);


    /**
     * 根据ID获取实体
     * @param id
     * @return
     */
    public TbContent findOne(Long id);


    /**
     * 批量删除
     * @param ids
     */
    public void delete(Long[] ids);

    /**
     * 分页
     * @param pageNum 当前页 码
     * @param pageSize 每页记录数
     * @return
     */
    public PageResult findPage(TbContent content, int pageNum, int pageSize);

    /**
     * 根据广告分类ID查询广告
     */
    public List<TbContent> findByCategoryId(Long categoryId);

}
