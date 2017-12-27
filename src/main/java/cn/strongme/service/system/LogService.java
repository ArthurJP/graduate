package cn.strongme.service.system;

import cn.strongme.dao.system.LogDao;
import cn.strongme.entity.system.Log;
import cn.strongme.exception.ServiceException;
import cn.strongme.service.common.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by 阿水 on 2017/11/8 上午9:29.
 */
@Service
@Transactional(readOnly = true, rollbackFor = ServiceException.class)
public class LogService extends BaseService<LogDao ,Log> {

}
