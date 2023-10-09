package cat.tecnocampus.fgcstations.application.persistence;

import cat.tecnocampus.fgcstations.domain.FavoriteJourney;
import java.util.List;

public interface FavoriteJourneyDAO {
    void saveFavoriteJourney(FavoriteJourney favoriteJourney, String username);

    List<FavoriteJourney> findFavoriteJourneys(String username);
}
