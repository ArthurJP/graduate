package cn.strongme.service.system;

import cn.strongme.dao.system.LogDao;
import cn.strongme.entity.system.Log;
import cn.strongme.exception.ServiceException;
import cn.strongme.service.common.BaseService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by 阿水 on 2017/11/8 上午9:29.
 */
@Service
@Transactional(readOnly = true, rollbackFor = ServiceException.class)
public class LogService extends BaseService<Log> {

    @Autowired
    private LogDao logDao;

    public PageInfo<Log> findListPage(Log log) {
        if (log.getPage() != null && log.getRows() != null) {
            PageHelper.startPage(log.getPage(), log.getRows());
        }
        PageInfo<Log> pageInfo = new PageInfo<>(this.logDao.findList(log));
        return pageInfo;
    }


    @Override
    public List<Log> findList(Log log) {
        return this.logDao.findList(log);
    }
}
