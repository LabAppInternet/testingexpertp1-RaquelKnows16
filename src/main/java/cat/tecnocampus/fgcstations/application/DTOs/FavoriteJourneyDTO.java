package cat.tecnocampus.fgcstations.application.DTOs;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;

import java.util.List;

public class FavoriteJourneyDTO {

    @Size(min=4, max=25, message = "Size must be between 4 and 25")
    private String origin;

    @Size(min=4, max=25, message = "Size must be between 4 and 25")
    private String destination;

    private String id;

    @Valid
    private List<DayTimeStartDTO> dayTimes;

    public FavoriteJourneyDTO() {
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<DayTimeStartDTO> getDayTimes() {
        return dayTimes;
    }

    public void setDayTimes(List<DayTimeStartDTO> dayTimes) {
        this.dayTimes = dayTimes;
    }
}

