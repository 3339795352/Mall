package com.gec.service.impl;



import com.gec.mall.entity.PageResult;

import com.gec.mall.mapper.TbContentMapper;
import com.gec.mall.pojo.TbContent;
import com.gec.mall.pojo.TbContentExample;
import com.gec.service.ContentService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.gec.mall.pojo.TbContentExample.Criteria;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    private TbContentMapper contentMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 查询全部
     */
    @Override
    public List<TbContent> findAll() {
        return contentMapper.selectByExample(null);
    }

    /**
     * 按分页查询
     */
    @Override
    public PageResult findPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Page<TbContent> page=   (Page<TbContent>) contentMapper.selectByExample(null);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 增加
     */
    @Override
    public void add(TbContent content) {

        contentMapper.insert(content);
        // 清除缓存
        redisTemplate.boundHashOps("content").delete(content.getCategoryId());
    }


    /**
     * 修改
     */
    @Override
    public void update(TbContent content){
        TbContent oldContent = contentMapper.selectByPrimaryKey(content.getId());
        // 清除之前分类的广告缓存
        redisTemplate.boundHashOps("content").delete(oldContent.getCategoryId());

        contentMapper.updateByPrimaryKey(content);
        // 清除缓存
        if(content.getCategoryId() != oldContent.getCategoryId()){
            redisTemplate.boundHashOps("content").delete(content.getCategoryId());
        }

    }

    /**
     * 根据ID获取实体
     * @param id
     * @return
     */
    @Override
    public TbContent findOne(Long id){
        return contentMapper.selectByPrimaryKey(id);
    }

    /**
     * 批量删除
     */
    @Override
    public void delete(Long[] ids) {
        for(Long id:ids){
            TbContent tbContent = contentMapper.selectByPrimaryKey(id);
            redisTemplate.boundHashOps("content").delete(tbContent.getCategoryId());

            contentMapper.deleteByPrimaryKey(id);
        }
    }


    @Override
    public PageResult findPage(TbContent content, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        TbContentExample example=new TbContentExample();
        Criteria criteria = example.createCriteria();

        if(content!=null){
            if(content.getTitle()!=null && content.getTitle().length()>0){
                criteria.andTitleLike("%"+content.getTitle()+"%");
            }
            if(content.getUrl()!=null && content.getUrl().length()>0){
                criteria.andUrlLike("%"+content.getUrl()+"%");
            }
            if(content.getPic()!=null && content.getPic().length()>0){
                criteria.andPicLike("%"+content.getPic()+"%");
            }
            if(content.getStatus()!=null && content.getStatus().length()>0){
                criteria.andStatusLike("%"+content.getStatus()+"%");
            }

        }

        Page<TbContent> page= (Page<TbContent>)contentMapper.selectByExample(example);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 首次访问时，如果redis没有数据，就访问数据库，把数据存到redis
     * 后续访问时，直接查询redis
     *
     *   哈希结构
     *        Key  content
     *                     field            value
     *                     categoryid 1     List<TbContent>
     *
     */
    @Override
    public List<TbContent> findByCategoryId(Long categoryId) {
        //redisTemplate.opsForHash(); List<String> list = (List<String>) redisTemplate.opsForHash().get("content",categoryId);
        // 加入缓存的代码:
        List<TbContent> list = (List<TbContent>) redisTemplate.boundHashOps("content").get(categoryId);

        if(list==null){
            System.out.println("查询数据库===================");
            TbContentExample example = new TbContentExample();
            Criteria criteria = example.createCriteria();
            // 有效广告:
            criteria.andStatusEqualTo("1");

            criteria.andCategoryIdEqualTo(categoryId);
            // 排序
            example.setOrderByClause("sort_order");

            list = contentMapper.selectByExample(example);

            redisTemplate.boundHashOps("content").put(categoryId, list);
        }else{
            System.out.println("从缓存中获取====================");
        }
        return list;
    }
}
