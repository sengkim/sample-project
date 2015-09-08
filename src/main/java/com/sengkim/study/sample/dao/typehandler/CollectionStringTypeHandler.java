package com.sengkim.study.sample.dao.typehandler;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@MappedTypes(Collection.class)
public class CollectionStringTypeHandler implements TypeHandler<Collection<String>> {

    protected static final Logger LOG = LoggerFactory.getLogger(CollectionStringTypeHandler.class);

    @SuppressWarnings("unchecked")
    private Collection<String> stringToCollection(String s) {

        Collection<String> results = null;
        // LOG.debug("convert String to  Collection : sources : {}",s);
        if (s != null) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                results = mapper.readValue(s, Collection.class);
            } catch (Exception e) {
                LOG.error(e.getMessage(), e);
            }
        }
        return results;
    }

    @SuppressWarnings("unchecked")
    private String collectionToString(Collection<String> list) {
        ObjectMapper mapper = new ObjectMapper();
        String result = null;

        if (list == null || list.isEmpty()) {
            return null;
        }

        try {
            result = mapper.writeValueAsString(list);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void setParameter(PreparedStatement ps, int i, Collection<String> parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, collectionToString(parameter));
    }

    public Collection<String> getResult(ResultSet rs, String columnName) throws SQLException {
        String s = rs.getString(columnName);
        return stringToCollection(s);
    }

    public Collection<String> getResult(ResultSet rs, int columnIndex) throws SQLException {
        String s = rs.getString(columnIndex);
        return stringToCollection(s);
    }

    public Collection<String> getResult(CallableStatement cs, int columnIndex) throws SQLException {
        String s = cs.getString(columnIndex);
        return stringToCollection(s);
    }

}
