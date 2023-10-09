package cat.tecnocampus.fgcstations.persistence;

import cat.tecnocampus.fgcstations.application.exception.FriendAlreadyExistsException;
import cat.tecnocampus.fgcstations.application.exception.UserDoesNotExistsException;
import cat.tecnocampus.fgcstations.domain.Friends;
import org.simpleflatmapper.jdbc.spring.JdbcTemplateMapperFactory;
import org.simpleflatmapper.jdbc.spring.ResultSetExtractorImpl;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class FriendDAO implements cat.tecnocampus.fgcstations.application.persistence.FriendDAO {
    private JdbcTemplate jdbcTemplate;

    ResultSetExtractorImpl<Friends> friendsRowMapper =
            JdbcTemplateMapperFactory
                    .newInstance()
                    .addKeys("username", "friend")
                    .newResultSetExtractor(Friends.class);

    public FriendDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Friends getFriends(String username) {
        List<Friends> result;
        String query = "select * from friend where username = ?";

        result = jdbcTemplate.query(query, friendsRowMapper, username);
        return result.get(0);
    }

    public List<Friends> getFriends() {
        String query = "select * from friend";
        return jdbcTemplate.query(query, friendsRowMapper);
    }

    public void saveFriends(Friends friends) {
        String query = "insert into friend(username, friend) values(?, ?)";

        try {
            jdbcTemplate.batchUpdate(query,
                    new BatchPreparedStatementSetter() {
                        @Override
                        public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                            String friend = friends.getFriends().get(i);
                            preparedStatement.setString(1, friends.getUsername());
                            preparedStatement.setString(2, friend);
                        }

                        @Override
                        public int getBatchSize() {
                            return friends.getFriends().size();
                        }
                    });        }
        catch (Exception e) {
            if (e.getMessage().contains("primary key violation"))
                throw new FriendAlreadyExistsException();
            else
                throw new RuntimeException();
        }


    }
}
