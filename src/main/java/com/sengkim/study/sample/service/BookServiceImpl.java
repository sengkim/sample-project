package com.sengkim.study.sample.service;

import java.sql.SQLDataException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sengkim.study.sample.dao.BookDao;
import com.sengkim.study.sample.domain.Book;

@Service
public class BookServiceImpl implements BookService {

	@Autowired
	private BookDao bookDao;

	public List<Book> listByPages(int count, int offset) {

		if(offset < 0) {
			offset = 0;
		}

		try {
			if(count <= 0) {
				count = bookDao.getAll().size();
			}
			return bookDao.getByPage(count, offset);
		} catch (SQLDataException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Book findById(int id) {

		if(id <= 0) {
			return null;
		}
		try {
			return bookDao.findById(id);
		} catch (SQLDataException e) {
			e.printStackTrace();
		}
		return null;
	}

}
