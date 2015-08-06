package com.sengkim.study.sample.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.SQLDataException;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.sengkim.study.sample.config.MvcConfig;
import com.sengkim.study.sample.model.Book;

@EnableWebMvc
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { MvcConfig.class })
public class BookDaoTest {

    private static final Logger logger = LoggerFactory.getLogger(BookDaoTest.class);

    @Autowired
    private BookDao bookDao;

    @Test
    public void getAllTest() throws SQLDataException {
        List<Book> bookList = bookDao.getAll();
        logger.info("Get {} books", bookList.size());
        assertNotNull(bookList);
        assertEquals(14, bookList.size());
    }

    @Test
    public void findByIdTest() throws SQLDataException {
        Book book = bookDao.findById(200);
        logger.info("Get book: {}", book);
        assertNotNull(book);
        assertEquals(200, book.getId().intValue());

    }
}
