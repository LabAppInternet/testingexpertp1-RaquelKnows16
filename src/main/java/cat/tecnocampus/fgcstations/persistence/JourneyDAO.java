package cat.tecnocampus.fgcstations.persistence;

import cat.tecnocampus.fgcstations.domain.Journey;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class JourneyDAO implements cat.tecnocampus.fgcstations.application.persistence.JourneyDAO {
    JdbcTemplate jdbcTemplate;
    StationDAO stationDAO;

    public JourneyDAO(JdbcTemplate jdbcTemplate, StationDAO stationDAO) {
        this.jdbcTemplate = jdbcTemplate;
        this.stationDAO = stationDAO;
    }

    public int saveJourney(Journey journey) {
        final String query = "INSERT INTO journey(journey_id, origin, destination) VALUES (?, ?, ?)";

        return jdbcTemplate.update(query, journey.getId(), journey.getOrigin().getNom(), journey.getDestination().getNom());
    }

    public String getJourneyId(Journey journey) {
        final String query = "SELECT journey_id FROM journey WHERE origin = ? AND destination = ?";

        try {
            String id = jdbcTemplate.queryForObject(query, String.class, journey.getOrigin().getNom(),
                    journey.getDestination().getNom());
            return id;
        }
        catch (EmptyResultDataAccessException e) {
            return "-1";
        }
    }

    public Journey findJourney(String journeyId) {
        final String query = "select * from journey where journey_id = ?";
        return jdbcTemplate.queryForObject(query, new JourneyMapper(), journeyId);
    }

    public final class JourneyMapper implements RowMapper<Journey> {
        @Override
        public Journey mapRow(ResultSet resultSet, int i) throws SQLException {
            Journey journey = new Journey(stationDAO.findByName(resultSet.getString("origin")),
                    stationDAO.findByName(resultSet.getString("destination")),
                    resultSet.getString("journey_id"));

            return journey;
        }
    }
}
