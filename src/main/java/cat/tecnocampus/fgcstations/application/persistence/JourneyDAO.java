package cat.tecnocampus.fgcstations.application.persistence;

import cat.tecnocampus.fgcstations.domain.Journey;
public interface JourneyDAO {
    int saveJourney(Journey journey);

    String getJourneyId(Journey journey);

    Journey findJourney(String journeyId);
}
