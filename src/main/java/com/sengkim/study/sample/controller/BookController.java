package com.sengkim.study.sample.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sengkim.study.sample.model.Book;
import com.sengkim.study.sample.service.BookService;
import com.sengkim.study.sample.util.BookListWrapper;

@Controller
@RequestMapping("/api/v1")
public class BookController {

	@Autowired
	private BookService bookService;

	@RequestMapping(value="/books", method = RequestMethod.GET)
	@ResponseBody
	public Object getBooksByPage(@RequestParam(value = "count", required = false) String count,
			@RequestParam(value = "offset", required = false) String offset) {
		int countInt, offsetInt;
		if(count == null || ("").equals(count.trim())) {
			countInt = 0;
		} else {
			countInt = Integer.parseInt(count);
		}

		if(offset == null || "".equals(offset.trim())) {
			offsetInt = 0;
		} else {
			offsetInt = Integer.parseInt(offset);
		}

		List<Book> books = bookService.listByPages(countInt, offsetInt);
		List<BookListWrapper> returnList = new ArrayList<BookListWrapper>();
		BookListWrapper bookListWrapper;
		Iterator<Book> iterator = books.iterator();
		while (iterator.hasNext()) {
			bookListWrapper = new BookListWrapper();
			Book book = iterator.next();
			bookListWrapper.setId(book.getId());
			bookListWrapper.setLink("/api/v1/books/" + book.getId());
			bookListWrapper.setTitle(book.getTitle());
			returnList.add(bookListWrapper);
		}
		return returnList;
	}

	@RequestMapping(value="/books/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Book findById(@PathVariable int id) {
		return bookService.findById(id);
	}


}
