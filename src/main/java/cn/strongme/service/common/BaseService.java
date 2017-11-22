package cn.strongme.service.common;

import cn.strongme.entity.common.BaseEntity;
import cn.strongme.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by 阿水 on 2017/11/8 上午8:51.
 */
@Transactional(readOnly = true, rollbackFor = ServiceException.class)
public abstract class BaseService<T extends BaseEntity> {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    public abstract List<T> findList(T t);


}
