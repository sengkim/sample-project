package com.sengkim.study.sample.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.sengkim.study.sample.domain.Book;

public class BookRowMapper implements RowMapper<Book> {

	public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
		Book book = new Book();
		book.setId(rs.getInt("id"));
		book.setTitle(rs.getString("title"));
		book.setAuthor(rs.getString("author"));
		book.setImage(rs.getString("image"));
		book.setPrice(rs.getDouble("price"));

		return book;
	}

}
