package com.example.inventorysystem.Tag;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public class TagJdbcRepository {
    private final JdbcTemplate jdbcTemplate;

    public TagJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private RowMapper<Tag> tagRowMapper = (rs, rowNum) -> {
        Tag tag = new Tag();
        tag.setId(rs.getLong( "id"));
        tag.setName(rs.getString("name"));
        return tag;
    };

    public List<Tag> findTagsByItemId(Long itemId) {
        String sql = """
                SELECT t.id, t.name
                FROM TAGS t
                JOIN ITEM_TAGS it ON t.id = it.TAG_ID
                WHERE it.ITEM_ID = ?
                """;
        return jdbcTemplate.query(sql, tagRowMapper, itemId);
    };
}
