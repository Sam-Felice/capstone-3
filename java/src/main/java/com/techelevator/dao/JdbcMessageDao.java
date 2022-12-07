package com.techelevator.dao;

import com.techelevator.model.Message;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcMessageDao implements MessageDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcMessageDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<Message> findAllMessages(int user_id) {
        List<Message> messages = new ArrayList<>();
        String sql = "SELECT m.band_id, m.message_id, m.message_date, m.message_content, name\n" +
                "FROM messages AS m\n" +
                "\tJOIN bands AS b ON m.band_id = b.band_id\n" +
                "\tJOIN followers AS f ON f.band_id = b.band_id\n" +
                "WHERE f.user_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, user_id);
        while (results.next()){
            Message message = mapRowToMessage(results);
            messages.add(message);
        }


        return messages;
    }

    private Message mapRowToMessage(SqlRowSet rowSet){
        LocalDate localDate  = null;
        if(rowSet.getDate("message_date") != null){
            localDate = rowSet.getDate("message_date").toLocalDate();
        }
        Message message = new Message(localDate,
                rowSet.getString("message_content"),
                rowSet.getInt("band_id"),
                rowSet.getInt("message_id"),
                rowSet.getString("name"));

        return message;
    }

}