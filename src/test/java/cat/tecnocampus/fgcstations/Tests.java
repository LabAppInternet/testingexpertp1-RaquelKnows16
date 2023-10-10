package cat.tecnocampus.fgcstations;

import cat.tecnocampus.fgcstations.application.DTOs.DayTimeStartDTO;
import cat.tecnocampus.fgcstations.application.DTOs.FavoriteJourneyDTO;
import cat.tecnocampus.fgcstations.application.DTOs.FriendsDTO;
import cat.tecnocampus.fgcstations.application.FgcController;
import cat.tecnocampus.fgcstations.application.exception.FriendAlreadyExistsException;
import cat.tecnocampus.fgcstations.application.exception.UserDoesNotExistsException;
import cat.tecnocampus.fgcstations.domain.FavoriteJourney;
import cat.tecnocampus.fgcstations.domain.Journey;
import cat.tecnocampus.fgcstations.domain.Station;
import cat.tecnocampus.fgcstations.domain.exceptions.SameOriginDestinationException;
import cat.tecnocampus.fgcstations.persistence.*;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

public class Tests {

    private FgcController fgcController = new FgcController(
            new StationDAO(), new UserDAO(),
            new FavoriteJourneyDAO(), new JourneyDAO(),
            new FriendDAO()
    );

    //Input data validations
    @Test
    void usernameLengthAndLowerTest() {
        // Caso de prueba 1: Longitud menor a 3
        FriendsDTO friendsDTOTest1A = new FriendsDTO();
        friendsDTOTest1A.setUsername("ab");
        ValidationException exception1 = assertThrows(ValidationException.class, () -> {
            fgcController.saveFriends(friendsDTOTest1A);
        });
        assertEquals("El nombre de usuario debe tener al menos 3" +
                "caracteres", exception1.getMessage());


        // Caso de prueba 2: Longitud mayor a 255
        FriendsDTO friendsDTOTest1B = new FriendsDTO();
        friendsDTOTest1B.setUsername("a".repeat(256));
        ValidationException exception2 = assertThrows(ValidationException.class, () -> {
            fgcController.saveFriends(friendsDTOTest1B);
        });
        assertEquals("El nombre de usuario no debe superar los 255" +
                "caracteres", exception2.getMessage());


        // Caso de prueba 3: Contiene caracteres no minúsculos
        FriendsDTO friendsDTOTest1C = new FriendsDTO();
        friendsDTOTest1C.setUsername("abc1");
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            fgcController.saveFriends(friendsDTOTest1C);
        });
        assertEquals("El nombre de usuario debe contener solo minúsculas.", exception.getMessage());


        // Caso de prueba 4: Longitud válida y caracteres minúsculos
        FriendsDTO friendsDTOTest1D = new FriendsDTO();
        friendsDTOTest1D.setUsername("abcdefg");
        assertDoesNotThrow(() -> {
            fgcController.saveFriends(friendsDTOTest1D);
        });
    }

    @Test
    void favoriteJourneyValidationTest() {
        // Caso de prueba 1: Longitud de origen menor a 4
        FavoriteJourneyDTO favoriteJourneyDTO2A = new FavoriteJourneyDTO();
        favoriteJourneyDTO2A.setOrigin("abc");
        favoriteJourneyDTO2A.setDestination("abc");
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            fgcController.addUserFavoriteJourney("username", favoriteJourneyDTO2A);
        });
        assertEquals("El origen y el destino deben tener entre 4 y 25 caracteres", exception.getMessage());


        // Caso de prueba 2: Longitud de destino mayor a 25
        FavoriteJourneyDTO favoriteJourneyDTO2B = new FavoriteJourneyDTO();
        favoriteJourneyDTO2B.setOrigin("a".repeat(26));
        favoriteJourneyDTO2B.setDestination("a".repeat(26));
        exception = assertThrows(ValidationException.class, () -> {
            fgcController.addUserFavoriteJourney("username", favoriteJourneyDTO2B);
        });
        assertEquals("El origen y el destino deben tener entre 4 y 25 caracteres", exception.getMessage());


        // Caso de prueba 3: Longitud válida
        FavoriteJourneyDTO favoriteJourneyDTO2C = new FavoriteJourneyDTO();
        favoriteJourneyDTO2C.setOrigin("aaaa");
        favoriteJourneyDTO2C.setDestination("aaaa");
        assertDoesNotThrow(() -> {
            fgcController.addUserFavoriteJourney("username", favoriteJourneyDTO2C);
        });
    }

    @Test
    void dayOfWeekIsValidWithCapitalTest() {
        // Caso de prueba 1: Día de la semana no empieza en mayúscula
        DayTimeStartDTO dayTimeStartDTO3A = new DayTimeStartDTO();
        dayTimeStartDTO3A.setDayOfWeek("monday");
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            validateDayTimeStartName(dayTimeStartDTO3A);
        });
        assertEquals("El día de la semana debe comenzar con mayúscula.", exception.getMessage());


        // Caso de prueba 2: Día de la semana válido
        DayTimeStartDTO dayTimeStartDTO3B = new DayTimeStartDTO();
        dayTimeStartDTO3B.setDayOfWeek("Monday");
        assertDoesNotThrow(() -> {
            validateDayTimeStartName(dayTimeStartDTO3B);
        });
    }

    private void validateDayTimeStartName(DayTimeStartDTO dayTimeStartDTO) {
        if (!dayTimeStartDTO.getDayOfWeek().matches("^(Sunday|Monday|Tuesday|Wednesday|Thursday|Friday|Saturday)$")) {
            throw new ValidationException("El día de la semana debe comenzar con mayúscula.");
        }
    }

    @Test
    void dayTimeStartValidationTest() {
        // Caso de prueba 1: Hora no sigue el patrón "00:00"
        DayTimeStartDTO dayTimeStartDTO4A = new DayTimeStartDTO();
        dayTimeStartDTO4A.setDayOfWeek("Monday");
        dayTimeStartDTO4A.setTime("1234");
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            validateDayTimeStart(dayTimeStartDTO4A);
        });
        assertEquals("La hora debe seguir el patrón 00:00.", exception.getMessage());

        // Caso de prueba 2: Hora válida
        DayTimeStartDTO dayTimeStartDTO4B = new DayTimeStartDTO();
        dayTimeStartDTO4B.setDayOfWeek("Monday");
        dayTimeStartDTO4B.setTime("12:34");
        assertDoesNotThrow(() -> {
            validateDayTimeStart(dayTimeStartDTO4B);
        });
    }

    private void validateDayTimeStart(DayTimeStartDTO dayTimeStartDTO) {
        if (!dayTimeStartDTO.getTime().matches("[0-9]{2}:[0-9]{2}")) {
            throw new ValidationException("La hora debe seguir el patrón 00:00.");
        }
    }



    //Business validations
    @Test
    void userDoesNotExistTest() {
        String username = "nonexistentuser";
        UserDoesNotExistsException exception = assertThrows(UserDoesNotExistsException.class, () -> {
            fgcController.getUser(username);
        });
        assertEquals("user " + username + " doesn't exist", exception.getMessage());
    }

    @Test
    void sameOriginDestinationExceptionTest() {
        Station origin = new Station();
        Station destination = new Station();

        Journey journey = new Journey(origin, destination, "empty id");

        FavoriteJourney favoriteJourney = new FavoriteJourney();
        favoriteJourney.setJourney(journey);

        SameOriginDestinationException exception = assertThrows(SameOriginDestinationException.class, () -> {
            fgcController.saveFavoriteJourney(favoriteJourney, "username");
        });

        assertEquals("Origin and destination must be different", exception.getMessage());
    }

    @Test
    void friendAlreadyExistsExceptionTest() {
        FriendsDTO friendsDTO = new FriendsDTO();
        friendsDTO.setUsername("username");
        friendsDTO.getFriends().add("existingfriend");
        FriendAlreadyExistsException exception = assertThrows(FriendAlreadyExistsException.class, () -> {
            fgcController.saveFriends(friendsDTO);
        });
        assertEquals("Friend already exists", exception.getMessage());
    }
}
