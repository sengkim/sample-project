package com.sengkim.study.sample.dao;

import org.springframework.stereotype.Repository;

import com.sengkim.study.sample.domain.User;


/**
 * @author sengkim
 * @Date Sep 8, 2015
 * @Email sengkim.it@gmail.com
 */
@Repository
public class UserDaoImpl extends BaseDaoImpl<User, BaseDao<User>> implements UserDao {

    public UserDaoImpl() {
        super(BaseDao.class);
    }

//    @Autowired
//    private SqlSession sqlSession;
//
//    public void insert(User user) {
//        sqlSession.getMapper(UserDao.class).insert(user);
//    }
//
//    public User findById(Long id) {
//        return sqlSession.getMapper(UserDao.class).findById(id);
//    }
//
//    public List<User> getAllUsers() {
//        return sqlSession.getMapper(UserDao.class).getAllUsers();
//    }
//
//    public void update(User user) {
//        sqlSession.getMapper(UserDao.class).update(user);
//    }
//
//    public void delete(Long id) {
//        sqlSession.getMapper(UserDao.class).delete(id);
//    }

}
