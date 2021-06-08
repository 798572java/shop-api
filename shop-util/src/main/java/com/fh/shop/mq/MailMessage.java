package com.fh.shop.mq;

import lombok.Data;

@Data
public class MailMessage {

    private String to;

    private String title;

    private String content;

    private String msgId;

}
