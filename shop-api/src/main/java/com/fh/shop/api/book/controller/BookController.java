package com.fh.shop.api.book.controller;
import com.fh.shop.api.book.biz.IBookService;
import com.fh.shop.api.book.param.BookQueryParam;
import com.fh.shop.api.book.po.Book;
import com.fh.shop.common.DataTableResult;
import com.fh.shop.common.ServerResponse;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;


@RequestMapping("/book")
@CrossOrigin
@RestController
public class BookController {

    @Resource(name = "bookService")
    private IBookService bookService;

    @RequestMapping(value = "/findList",method = RequestMethod.POST)
    public DataTableResult findList(BookQueryParam bookQueryParam){
        return bookService.findList(bookQueryParam);
    }

    @RequestMapping(value = "/book",method = RequestMethod.POST)
    public ServerResponse addBook(Book book){
        return bookService.addBook(book);
    }

    @DeleteMapping(value = "/{id}")
    public ServerResponse deleteBook(@PathVariable("id")Long id){
        return bookService.deleteBook(id);
    }

    @RequestMapping(value = "/deleteBatch",method = RequestMethod.POST)
    public ServerResponse deleteBatch(String ids){
        return bookService.deleteBatch(ids);
    }

    @RequestMapping(value = "/updateBook",method = RequestMethod.POST)
    public  ServerResponse updateBook(Book book){
        return bookService.updateBook(book);
    }

   // @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    @GetMapping(value = "/{id}")
    public ServerResponse findBookById(@PathVariable("id")Long id){
            return bookService.findBookById(id);
    }


}
