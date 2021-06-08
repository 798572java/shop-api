package com.fh.shop.api.recipients.biz;

import com.fh.shop.api.recipients.po.Recipients;
import com.fh.shop.common.ServerResponse;

public interface IRecipientsService {


    ServerResponse addRecipient(Recipients recipients, Long memberId);

    ServerResponse findList(Long memberId);

    ServerResponse updateStatus(Long id, Long memberId);
}
