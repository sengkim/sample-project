package com.sengkim.study.sample.dao;

import java.sql.SQLDataException;
import java.util.List;

import com.sengkim.study.sample.model.Book;

public interface BookDao {

	public List<Book> getAll() throws SQLDataException;

	public Book findById(int id) throws SQLDataException;

	public List<Book> getByPage(int count, int offset) throws SQLDataException;
}
