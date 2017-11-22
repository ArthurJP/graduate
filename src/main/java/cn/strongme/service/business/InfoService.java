package cn.strongme.service.business;

import cn.strongme.dao.business.InfoDao;
import cn.strongme.entity.business.Info;
import cn.strongme.exception.ServiceException;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by 阿水 on 2017/9/21 下午2:13.
 */
@Service
@Transactional(readOnly = true)
public class InfoService {

    @Autowired
    private InfoDao infoDao;

    public Info get(Info info) {
        return this.infoDao.get(info);
    }

    public List<Info> findList(Info info) {
        return this.infoDao.findList(info);
    }

    public List<Info> findListPage(Info info) {
        if (info.getPage() != null && info.getRows() != null) {
            PageHelper.startPage(info.getPage(), info.getRows());
        }
        return this.infoDao.findList(info);
    }

    public List<Info> findListPageByType(Info info) {
        if (info.getPage() != null && info.getRows() != null) {
            PageHelper.startPage(info.getPage(), info.getRows());
        }
        return this.infoDao.findListByType(info);
    }

    @Transactional(readOnly = false)
    public void save(Info info) {
        if (StringUtils.isBlank(info.getId())) {
            info.preInsert();
            try {
                this.infoDao.insert(info);
            } catch (Exception e) {
                e.printStackTrace();
                throw new ServiceException("保存资讯失败", e);
            }
        } else {
            info.preUpdate();
            try {
                this.infoDao.update(info);
            } catch (Exception e) {
                e.printStackTrace();
                throw new ServiceException("更新资讯失败", e);
            }
        }
    }

    @Transactional(readOnly = false)
    public void delete(Info info) {
        try {
            this.infoDao.delete(info);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("删除资讯失败", e);
        }
    }

    public Info findListByMaxDate(Info info) {
        return this.infoDao.findListByMaxDate(info);
    }
}
