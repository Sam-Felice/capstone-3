package com.techelevator.dao;

import com.techelevator.model.Band;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlInOutParameter;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcBandDao implements BandDao{

    private JdbcTemplate jdbcTemplate;

    public JdbcBandDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Band> getAllBands() {
        String sql = "SELECT name, description, members, user_id, band_id, profile_pic " +
                "FROM bands;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);

        List<Band> bands = new ArrayList<>();
        while(results.next()){
            Band band = new Band(results.getString("name"),results.getString("members"),
                    results.getString("description"), results.getInt("user_id"), results.getInt("band_id"),
                    results.getString("profile_pic"));
            bands.add(band);
        }
        return bands;
    }

    @Override
    public Band viewBandPage(int id) {
        String sql = "SELECT name, description, members, user_id, band_id, profile_pic from bands WHERE band_id = ?;";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);

        if (results.next()){
            Band band = new Band(results.getString("name"),results.getString("members"),
                    results.getString("description"), results.getInt("user_id"), results.getInt("band_id"),
                    results.getString("profile_pic"));
            return band;
        }

        return null;
    }
}
