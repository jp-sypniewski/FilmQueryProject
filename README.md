## Film Query Project

### Week 7 Homework - Skill Distillery

#### Overview

This project provides a command-line application which retrieves and displays film data from a MySQL database.  The user can search either by film ID or by keyword over film titles and descriptions.  Upon display of film information, the user is prompted to see detailed film data.


#### Concepts

- Object-relational mapping
  - Film and actor objects created by row specific data
- SQL
  - Select statements with joins
  - Bind parameters


#### Technologies Used

- Java
- MySQL
- Maven


#### Lessons Learned

- SQL column name aliases
  - Adding tables to the original can result in naming conflicts.  To resolve these conflicts and improve the readability of the code, these columns are renamed within queries to more relevant names in the returned dataset.
- Refactoring code for readability
  - Both user options in the main menu provide for "displaying" a film, so by creating a method used under each option, the output only need be updated once to add features (e.g. a film's category).
