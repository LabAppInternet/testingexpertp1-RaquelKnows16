package cat.tecnocampus.fgcstations.persistence;

import cat.tecnocampus.fgcstations.domain.DayTimeStart;
import cat.tecnocampus.fgcstations.domain.FavoriteJourney;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class FavoriteJourneyDAO implements cat.tecnocampus.fgcstations.application.persistence.FavoriteJourneyDAO {
    JdbcTemplate jdbcTemplate;
    JourneyDAO journeyDAO;

    public FavoriteJourneyDAO(JdbcTemplate jdbcTemplate, JourneyDAO journeyDAO) {
        this.jdbcTemplate = jdbcTemplate;
        this.journeyDAO = journeyDAO;
    }

    public void saveFavoriteJourney (FavoriteJourney favoriteJourney, String username) {
        jdbcTemplate.update("INSERT INTO favorite_journey(favorite_journey_id, journey, user_id) VALUES (?, ?, ?)",
                favoriteJourney.getId(), favoriteJourney.getJourney().getId(), username);

        saveDayTimeStart(favoriteJourney.getStartList(), favoriteJourney.getId());
    }

    public List<FavoriteJourney> findFavoriteJourneys (String username) {
        return jdbcTemplate.query("select * from favorite_journey where user_id = ?", new FavoriteJourneyMapper(), username);
    }

    private int[] saveDayTimeStart(List<DayTimeStart> start, String favoriteJourneyId) {
        return jdbcTemplate.batchUpdate("INSERT INTO day_time_start(daytime_id, timeStart, day_of_week, favorite_journey_id) values(?, ?, ?, ?)",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                        DayTimeStart dayTimeStart = start.get(i);
                        preparedStatement.setString(1, dayTimeStart.getId());
                        preparedStatement.setString(2, dayTimeStart.getTimeStart());
                        preparedStatement.setString(3, dayTimeStart.getDayOfWeek());
                        preparedStatement.setString(4, favoriteJourneyId);
                    }

                    @Override
                    public int getBatchSize() {
                        return start.size();
                    }
        });
    }

    private List<DayTimeStart> findDayTimeStart(String favoriteJourneyId) {
        return jdbcTemplate.query("select * from day_time_start where favorite_journey_id = ?", new DayTimeStartMapper(), favoriteJourneyId);
    }

    public final class FavoriteJourneyMapper implements RowMapper<FavoriteJourney> {
        @Override
        public FavoriteJourney mapRow(ResultSet resultSet, int i) throws SQLException {
            FavoriteJourney favoriteJourney = new FavoriteJourney();

            favoriteJourney.setId(resultSet.getString("favorite_journey_id"));
            favoriteJourney.setJourney(journeyDAO.findJourney(resultSet.getString("journey")));
            favoriteJourney.setDateTimeStarts(findDayTimeStart(favoriteJourney.getId()));
            return favoriteJourney;
        }
    }

    public final class DayTimeStartMapper implements RowMapper<DayTimeStart> {
        @Override
        public DayTimeStart mapRow(ResultSet resultSet, int i) throws SQLException {
            DayTimeStart dayTimeStart = new DayTimeStart();

            dayTimeStart.setId(resultSet.getString("daytime_id"));
            dayTimeStart.setTimeStart(resultSet.getString("timeStart"));
            dayTimeStart.setDayOfWeek(resultSet.getString("day_of_week"));

            return dayTimeStart;
        }
    }
}
