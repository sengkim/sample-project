package com.sengkim.study.sample.dao;

import java.sql.SQLDataException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.sengkim.study.sample.domain.Book;

@Repository
public class BookDaoImpl implements BookDao {

	@Autowired
	private DataSource dataSource;

	@Resource
	private Map<String, String> sql;

	public List<Book> getAll() throws SQLDataException {
		return new JdbcTemplate(this.dataSource).query(sql.get("query"), new BookRowMapper());
	}

	public Book findById(int id) throws SQLDataException {
		Book book = null;
		JdbcTemplate template = new JdbcTemplate(dataSource);

		try {
			book = template.queryForObject(sql.get("find"), new Object[] {id}, new BookRowMapper());
		} catch (EmptyResultDataAccessException e) {

		}
		return book;
	}

	public List<Book> getByPage(int count, int offset) throws SQLDataException {

		return new JdbcTemplate(this.dataSource).query(sql.get("queryByPage"),
				new Object[] {count,offset},  new BookRowMapper());
	}

}
