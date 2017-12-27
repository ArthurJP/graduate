package cn.strongme.service.business;

import cn.strongme.dao.business.InfoDao;
import cn.strongme.entity.business.Info;
import cn.strongme.service.common.BaseService;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by 阿水 on 2017/9/21 下午2:13.
 */
@Service
@Transactional(readOnly = true)
public class InfoService extends BaseService<InfoDao, Info> {

    public List<Info> findListPageByType(Info info) {
        if (info.getPage() != null && info.getRows() != null) {
            PageHelper.startPage(info.getPage(), info.getRows());
        }
        return this.dao.findListByType(info);
    }


    public Info findListByMaxDate(Info info) {
        return this.dao.findListByMaxDate(info);
    }
}
