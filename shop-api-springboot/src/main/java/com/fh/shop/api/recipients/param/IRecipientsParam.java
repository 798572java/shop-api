package com.fh.shop.api.recipients.param;

import com.fh.shop.api.recipients.po.Recipients;
import lombok.Data;

import java.io.Serializable;

@Data
public class IRecipientsParam implements Serializable {

    private String name;

    private String phone;

    private String site;

}
