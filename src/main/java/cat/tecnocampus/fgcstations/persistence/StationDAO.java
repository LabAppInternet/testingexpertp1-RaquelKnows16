package cat.tecnocampus.fgcstations.persistence;


import cat.tecnocampus.fgcstations.domain.Station;
import org.simpleflatmapper.jdbc.spring.JdbcTemplateMapperFactory;
import org.simpleflatmapper.jdbc.spring.ResultSetExtractorImpl;
import org.simpleflatmapper.jdbc.spring.RowMapperImpl;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StationDAO implements cat.tecnocampus.fgcstations.application.persistence.StationDAO {

    private final JdbcTemplate jdbcTemplate;

    public StationDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    ResultSetExtractorImpl<Station> stationsRowMapper =
            JdbcTemplateMapperFactory
                    .newInstance()
                    .addKeys("nom")
                    .newResultSetExtractor(Station.class);

    RowMapperImpl<Station> stationRowMapper =
            JdbcTemplateMapperFactory
                    .newInstance()
                    .addKeys("nom")
                    .newRowMapper(Station.class);

    public List<Station> findAll() {
        final String findStations =  "SELECT * FROM STATION";

        return jdbcTemplate.query(findStations, stationsRowMapper);
    }

    public Station findByName(String nom) {
        final String findStation = "select * from station where nom = ?";

        return jdbcTemplate.queryForObject(findStation, stationRowMapper, nom);
    }
}
