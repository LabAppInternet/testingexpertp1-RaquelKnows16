# FGC La Pobla line: Favorite Journeys
Here you have a REST API working perfectly.
The domain of the application is public transportation (Ferrocarrils de la Generalitat de Catalunya - Catalan Trains) and their users.
It aims to give some complementary services around FGC lines.

## Classes in the domain:
* User (*userlab* in the database to avoid conflicts with the reserved word "user")
* Station (name and position)
* Journey (consists of two stations: the origin and the destination. Both stations must be different)
* DayTimeStart (consists of a dayOfWeek and a start time)
* FavoriteJourney (a journey that a user travels frequently and the moment he uses it: consists of a Journey and a list of DayTimeStart)
* Friends (a username, existing as a user in the system, that declares who are her friends. The later names don't need to be in the system).
  A user cannot have the same friend twice.

## Notes on the Architecture
This application has an anemic domain model, that is, it has very few logics. It doesn't follow our architectural rules
completely: basically, it exposes (as output) the domain classes. It uses a few DTOs, declared in the application layer, as input data.

At this moment it is not using de Spring Data JPA repository pattern. It is using the *Data Access Object (DAO)* pattern instead, with the JDBC template that
gives the developer full control of database queries. At the application layer, the DAOs interfaces are defined, and the implementation is in the
persistence layer.

Also, note that in the resources package, you'll find the files *schema.sql* and *data.sql*. These files are used by Spring to create the tables and
populate the database, respectively.

## API entry points
In the resources package you'll find the file *calls.http* with all the possible different calls to the API. Note that there are also
calls that trigger exceptions and input validation errors.

You can run the file *calls.http* directly from within IntelliJ. It provides an HTTP client that behaves like Postman or other similar clients.

## Testing Experts: TODO
Add integration tests to the REST controller. You need to test the happy path (all worked fine) as well as the different exceptions and input validation errors.
You can use the *calls.http* file as a guide to the tests you need to implement.

### Input data validations
These are the current validations:
* The *username* of the Friends (see FriendsDTO) must be between 3 and 255 characters long and must contain only lowercase letters
* The *origin* and *destination* of favorite journeys (see FavoriteJourneyDTO) must be between 4 and 25 characters long
* The *dayOfWeek* of the DayTimeStartDTO must be a weekday name beginning in a capital letter
* The *time* of the DayTimeStartDTO must follow the pattern 00:00
  Tests must check that the validations are working properly. That is an HTTP 400 status is returned when the validations fail, and the appropriate message
  for each of the violations. Note that a DTO may have more than one input error.

### Business validations
Test that when the following business error occurs the appropriate HTTP status and message are returned:
* User or other resources don't exist: returned  NOT_FOUND 404 as status and the exception message
* SameOriginDestinationException: return CONFLICT 409 as status and "Origin and destination must be different" as message
* FriendAlreadyExistsException: return CONFLICT 409 as status and "Friend already exists" as message
  Visit files ControllerAdvice and ErrorValidationAdvice to see how the error and their corresponding exceptions are handled.

## JPA Experts: TODO
Currently, this application is using the DAO pattern with the JDBC template. You need to change it to use the Spring Data JPA repository pattern.
One of the first things you'll need to do is to remove the *spring-boot-starter-jdbc* dependency and add the *spring-boot-starter-data-jpa* one.
As we said before, note
that the application layer contains a package called *persistence* with the DAO interfaces. Also, see that the persistence layer contains the implementation
of the DAO interfaces. You need to change the DAO interfaces to Spring Data JPA repositories, move them to the persistence layer, and delete the
current DAO implementations.

You will also need to add the appropriate JPA annotations to the domain classes. Design thoughtfully the relationships between the entities, that is,
their cardinality, directionality, lazy or eager fetching, and cascading. Recall that you have the *schema.sql* file with the current database design. While
it may be a useful resource to design relationships, you don't need to follow it strictly. Your design must avoid possible JPA inefficiencies.
Once you have the JPA annotations, you can delete the *schema.sql* file. Additionally, you'll need to add some queries to the JPA repository interfaces.
You may also need to change some of the insert queries in the *data.sql* file, depending on the entity and relationships you define.

Your goal is to make the application work as it is now but with the JPA for persistence.
You shouldn't change the API entry points, or the application layer (except for removing the DAOs interfaces). Your colleagues are working on adding tests
to the current application and the very same tests should pass in your new version.



