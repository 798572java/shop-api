package com.fh.shop.admin.mapper.log;

import com.fh.shop.admin.param.LogQueryParam;
import com.fh.shop.admin.po.log.Log;

import java.util.List;

public interface ILogMapper {

    void addLog(Log log);


    Long findLogCount(LogQueryParam logQueryParam);

    List<Log> findLogPage(LogQueryParam logQueryParam);

    List<Log> findLogNoPage(LogQueryParam logQueryParam);

}
