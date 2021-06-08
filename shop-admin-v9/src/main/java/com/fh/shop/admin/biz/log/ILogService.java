package com.fh.shop.admin.biz.log;

import com.fh.shop.common.DataTableResult;
import com.fh.shop.admin.param.LogQueryParam;
import com.fh.shop.admin.po.log.Log;

import java.util.List;

public interface ILogService {
    void addLog(Log log);

    DataTableResult findLog(LogQueryParam logQueryParam);

    List<Log> findLogNoPage(LogQueryParam logQueryParam);

}
