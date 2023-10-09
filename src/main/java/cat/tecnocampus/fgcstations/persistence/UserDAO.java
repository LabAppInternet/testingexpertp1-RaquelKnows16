package cat.tecnocampus.fgcstations.persistence;


import cat.tecnocampus.fgcstations.application.exception.UserDoesNotExistsException;
import cat.tecnocampus.fgcstations.domain.User;
import org.simpleflatmapper.jdbc.spring.JdbcTemplateMapperFactory;
import org.simpleflatmapper.jdbc.spring.ResultSetExtractorImpl;
import org.simpleflatmapper.jdbc.spring.RowMapperImpl;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDAO implements cat.tecnocampus.fgcstations.application.persistence.UserDAO {

    private final JdbcTemplate jdbcTemplate;

    public UserDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    RowMapperImpl<User> userRowMapper =
            JdbcTemplateMapperFactory
                    .newInstance()
                    .addKeys("username")
                    .newRowMapper(User.class);

    ResultSetExtractorImpl<User> usersRowMapper =
            JdbcTemplateMapperFactory
                    .newInstance()
                    .addKeys("username")
                    .newResultSetExtractor(User.class);

       public User findByUsername(String username) {
        final String query = "select username, name, second_name, email from userlab where username = ?";

        try {
            return jdbcTemplate.queryForObject(query, userRowMapper, username);
        }
        catch (EmptyResultDataAccessException e) {
            throw new UserDoesNotExistsException("User " + username + " doesn't exist");
        }
    }

    public List<User> getUsers() {
        final String query = "SELECT username, name, second_name, email FROM userlab";

        return jdbcTemplate.query(query, usersRowMapper);
    }

    public boolean existsUser(String username) {
        final String query = "SELECT count(*) FROM userlab WHERE username = ?";

        try {
            Integer cnt = jdbcTemplate.queryForObject(query, Integer.class, username);
            return cnt > 0;
        }
        catch (EmptyResultDataAccessException e) {
            return false;
        }
    }
}
