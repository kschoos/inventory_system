package com.example.inventorysystem.Item;

import com.example.inventorysystem.Location.Location;
import com.example.inventorysystem.Tag.Tag;
import com.example.inventorysystem.Tag.TagJdbcRepository;
import jakarta.persistence.*;
import org.jspecify.annotations.NonNull;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class ItemJdbcRepository {
    private final JdbcTemplate jdbcTemplate;
    private final TagJdbcRepository tagJdbcRepository;


    public ItemJdbcRepository(JdbcTemplate jdbcTemplate, TagJdbcRepository tagJdbcRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.tagJdbcRepository = tagJdbcRepository;
    }

    private RowMapper<Item> itemRowMapper = new RowMapper<>() {
        @Override
        public Item mapRow(ResultSet rs, int rowNum) throws SQLException {
            Item item = new Item();

            item.setId(rs.getLong("id"));
            item.setName(rs.getString("name"));
            item.setImageUrl(rs.getString("image_url"));
            item.setCount(rs.getInt("count"));

            Location location = new Location();
            location.setId(rs.getLong("location_id"));
            location.setName(rs.getString("location_name"));

            item.setTags(new HashSet<>(tagJdbcRepository.findTagsByItemId(item.getId())));

            return item;
        }
    };

    public List<Item> findAll() {
        String sql = """
                SELECT 
                i.id, i.name, i.image_url, i.count, 
                l.id AS location_id, l.name AS location_name
                FROM ITEMS i
                LEFT JOIN LOCATIONS l ON i.location_id = l.id""";
        return jdbcTemplate.query(sql, itemRowMapper);
    }
}