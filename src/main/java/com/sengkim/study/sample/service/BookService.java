package com.sengkim.study.sample.service;

import java.util.List;

import com.sengkim.study.sample.domain.Book;

public interface BookService {

	public List<Book> listByPages(int count, int offset);

	public Book findById(int id);

}
