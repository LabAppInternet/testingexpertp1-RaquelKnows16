package cat.tecnocampus.fgcstations.application.persistence;

import cat.tecnocampus.fgcstations.domain.Friends;
import java.util.List;

public interface FriendDAO {
    Friends getFriends(String username);

    List<Friends> getFriends();

    void saveFriends(Friends friends);
}
