package com.fh.shop.api.book.controller;

import com.fh.shop.api.book.biz.IBookService;
import com.fh.shop.api.book.param.BookQueryParam;
import com.fh.shop.api.book.po.Book;
import com.fh.shop.common.DataTableResult;
import com.fh.shop.common.ServerResponse;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;



@CrossOrigin
@RestController
public class BookRestController {

    @Resource(name = "bookService")
    private IBookService bookService;

    //新增
    @RequestMapping(value = "/books",method = RequestMethod.POST)
    public ServerResponse addBook(Book book){
        return bookService.addBook(book);
    }
    //查询
    @RequestMapping(value = "/books",method = RequestMethod.GET)
    public DataTableResult findList(BookQueryParam bookQueryParam){
        return bookService.findList(bookQueryParam);
    }

    //删除
    @RequestMapping(value = "/books/{id}",method = RequestMethod.DELETE)
    public ServerResponse deleteBook(@PathVariable("id")Long id){
        return bookService.deleteBook(id);
    }

    //批量删除
    @RequestMapping(value = "/books",method = RequestMethod.DELETE)
    public ServerResponse deleteBatch(String ids){
        return bookService.deleteBatch(ids);
    }

    //修改
    @RequestMapping(value = "/books",method = RequestMethod.PUT)
    public  ServerResponse updateBook(@RequestBody Book book){
        return bookService.updateBook(book);
    }

    //回填的查询
    @RequestMapping(value = "/books/{id}",method = RequestMethod.GET)
    public ServerResponse findBookById(@PathVariable("id")Long id){
            return bookService.findBookById(id);
    }


}
