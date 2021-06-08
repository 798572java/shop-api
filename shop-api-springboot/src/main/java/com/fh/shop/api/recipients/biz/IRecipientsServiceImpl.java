package com.fh.shop.api.recipients.biz;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.shop.api.recipients.mapper.IRecipientsMapper;
import com.fh.shop.api.recipients.po.Recipients;
import com.fh.shop.common.ServerResponse;
import com.fh.shop.common.SystemConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("recipientService")
@Transactional(rollbackFor = Exception.class)
public class IRecipientsServiceImpl implements IRecipientsService {

    @Autowired
    private IRecipientsMapper recipientsMapper;

    @Override
    public ServerResponse addRecipient(Recipients recipients, Long memberId) {
        recipientsMapper.insert(recipients);
        return ServerResponse.success();
    }

    @Override
    @Transactional(readOnly = true)
    public ServerResponse findList(Long memberId) {
        QueryWrapper<Recipients> recipientsQueryWrapper = new QueryWrapper<>();
        recipientsQueryWrapper.eq("memberId",memberId);
        List<Recipients> recipients = recipientsMapper.selectList(recipientsQueryWrapper);
        return ServerResponse.success(recipients);
    }

    @Override
    public ServerResponse updateStatus(Long id, Long memberId) {
        //先重置
        Recipients recipients = new Recipients();
        recipients.setStatus(SystemConstant.DEFAULT);
        QueryWrapper<Recipients> updateWrapper = new QueryWrapper<>();
        updateWrapper.eq("memberId",memberId);
        recipientsMapper.update(recipients, updateWrapper);
        //再修改状态
        Recipients recipients1 = new Recipients();
        recipients1.setId(id);
        recipients1.setStatus(SystemConstant.NODEFAULT);
        recipientsMapper.updateById(recipients1);
        return ServerResponse.success();
    }
}
