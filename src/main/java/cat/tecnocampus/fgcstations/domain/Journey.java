package cat.tecnocampus.fgcstations.domain;


import cat.tecnocampus.fgcstations.domain.exceptions.SameOriginDestinationException;

public class Journey {

    private String id;

    private Station origin;
    private Station destination;

    public Journey(Station origin, Station destination, String id) {
        if (origin.sameStation(destination)) {
            throw new SameOriginDestinationException();
        }

        this.origin = origin;
        this.destination = destination;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Station getOrigin() {
        return origin;
    }

    public void setOrigin(Station origin) {
        this.origin = origin;
    }

    public Station getDestination() {
        return destination;
    }

    public void setDestination(Station destination) {
        this.destination = destination;
    }

    @Override
    public String toString() {
        return "Journey{" +
                "id=" + id +
                ", origin=" + origin +
                ", destination=" + destination +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Journey journey = (Journey) o;

        if (origin != null ? !origin.equals(journey.origin) : journey.origin != null) return false;
        return destination != null ? destination.equals(journey.destination) : journey.destination == null;
    }

    @Override
    public int hashCode() {
        int result = origin != null ? origin.hashCode() : 0;
        result = 31 * result + (destination != null ? destination.hashCode() : 0);
        return result;
    }
}
