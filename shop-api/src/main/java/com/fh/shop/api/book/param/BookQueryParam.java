package com.fh.shop.api.book.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fh.shop.common.page;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class BookQueryParam extends page implements Serializable {

    private String bookName;

    private BigDecimal minPrice;

    private BigDecimal maxPrice;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date minDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date maxDate;

    private String bookUser;
}
