package cn.strongme.dao.common;

import cn.strongme.entity.common.TreeEntity;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;
import java.util.Map;

/**
 * Created by 阿水 on 2017/12/2 下午8:24.
 */
public interface TreeMapper<T extends TreeEntity> extends MySqlMapper<T> {

    /**
     * 根据Id获取实体
     *
     * @param id
     * @return
     */
    T get(@Param("id") String id);

    T get(T t);

    /**
     * 根据自定义条件查询实体列表
     *
     * @param map
     * @return
     */
    List<T> findList(Map<String, Object> map);

    /**
     * 根据实体参数查询实体列表
     *
     * @param t
     * @return
     */
    List<T> findList(T t);

    /**
     * 根据实体保存信息
     *
     * @param t
     */
    void insert(T t);

    /**
     * 根据自定义参数保存信息
     *
     * @param map
     */
    void insert(Map<String, Object> map);

    /**
     * 批量保存实体信息
     *
     * @param list
     */
    void insertBatch(List<T> list);

    /**
     * 根据Id更新实体信息
     *
     * @param t
     * @return
     */
    int update(T t);

    /**
     * 根据Id以及自定义参数更新实体信息
     *
     * @param map
     * @return
     */
    int update(Map<String, Object> map);

    /**
     * 根据ID删除实体信息
     *
     * @param id
     * @return
     */
    int delete(@Param("id") String id);

    int delete(T t);

    /**
     * 根据自定义参数删除
     *
     * @param map
     * @return
     */
    int delete(Map<String, Object> map);

    /**
     * 批量删除
     *
     * @param list
     * @return
     */
    int deleteBatch(List<String> list);

}
