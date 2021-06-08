package com.fh.shop.admin.biz.log;

import com.fh.shop.common.DataTableResult;
import com.fh.shop.admin.mapper.log.ILogMapper;
import com.fh.shop.admin.param.LogQueryParam;
import com.fh.shop.admin.po.log.Log;
import com.fh.shop.util.DateForMat;
import com.fh.shop.admin.vo.log.LogVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("logService")
public class ILogServiceImpl implements ILogService {

    @Autowired
    private ILogMapper logMapper;


    @Override
    public void addLog(Log log) {
        logMapper.addLog(log);
    }

    @Override
    public DataTableResult findLog(LogQueryParam logQueryParam) {

        Long count=logMapper.findLogCount(logQueryParam);

       List<Log> logList= logMapper.findLogPage(logQueryParam);

       List<LogVo> logVoList=new ArrayList<>();
        for (Log log : logList) {
            LogVo logVo =new LogVo();
            logVo.setId(log.getId());
            logVo.setUserName(log.getUserName());
            logVo.setRealName(log.getRealName());
            logVo.setInfo(log.getInfo());
            logVo.setInsertTime(DateForMat.date2str(log.getInsertTime(),DateForMat.Date_Str));
            logVo.setParamInfo(log.getParamInfo());
            logVoList.add(logVo);
        }



        return new DataTableResult(logQueryParam.getDraw(),count,count,logVoList);
    }

    @Override
    public List<Log> findLogNoPage(LogQueryParam logQueryParam) {
        return logMapper.findLogNoPage(logQueryParam);
    }
}
