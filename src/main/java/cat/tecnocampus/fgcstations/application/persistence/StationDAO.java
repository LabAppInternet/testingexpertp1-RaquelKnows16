package cat.tecnocampus.fgcstations.application.persistence;

import cat.tecnocampus.fgcstations.domain.Station;
import java.util.List;

public interface StationDAO {
    List<Station> findAll();

    Station findByName(String nom);
}
