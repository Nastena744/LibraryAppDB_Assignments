Feature: As a data consumer, I want to know the genre of the most borrowed book

  @goce @db
  Scenario: verify the the common book genre that’s being borrowed
    Given Establish the database connection
    When Verify user can execute query to find most popular book genre
    Then Verify user can see "Fantasy" as the most popular book genre.