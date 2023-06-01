# Pre-Bootcamp-Private

    This is a project for Projects and Algos at Coding Dojo PH.

    Members of this project: 
    ALEX SANTOS - https://github.com/alex-s4
    ZYRUS HIYAO - https://github.com/dev-zyrushiyao

# Models

This Spring Boot project has currently seven models:

## User

The User model represents the User Login Information in the database. A User object has the following fields:

* `user_name`: a `CharField` of at least 3 characters, representing the username of the user.
* `password`: a `Charfield` of at least 5 characters, representing the user's password.
* `updatedAt`: a `DateTimeField` that auto-updates when the object is updated.
* `createdAt`: a `DateTimeField` that auto-populates with the creation date and time of the object.

## User Information

The User Information model represents the User Profile Information in the database that has . A Genre object has the following field:

* `firstName`: a `CharField` minimum length of 3 and maximum length of 20, representing the first name of the User.
* `lastName`: a `CharField` minimum length of 3 and maximum length of 20, representing the last name of the User.
* `location`: a `CharField` minimum length of 3 and maximum length of 20, representing the user's location.
* `programmingLanguage`: a `CharField` minimum length of 3 and maximum length of 50, representing the user's programming language.
* `userAccount`: a `OneToOneField` to the User model, representing the User associated with the User Information.

## Role

The Role model represents a role in the database. A Role object has the following field:

* `name`: a `CharField`, representing the name of the role.

## Main Topic

The Main Topic model represents a main topic in the database. A Main Topic object has the following fields:

* `title`: a `CharField` minimum length of 3 and maximum length of 40, representing the title of the main topic.
* `description`: a `TextField` minimum length of 3 and maximum length of 40, representing the description of the Main Topic.
* `updatedAt`: a `DateTimeField` that auto-updates when the object is updated.
* `createdAt`: a `DateTimeField` that auto-populates with the creation date and time of the object.

## Sub Topic

The Sub Topic model represents a sub topic in the database. A Sub Topic object has the following fields:

* `title`: a `CharField` minimum length of 3 and maximum length of 40, representing the title of the sub topic.
* `description`: a `TextField` minimum length of 3 and maximum length of 40, representing the description of the sub topic.
* `updatedAt`: a `DateTimeField` that auto-updates when the object is updated.
* `createdAt`: a `DateTimeField` that auto-populates with the creation date and time of the object.
* `forumMainTopics`: a `ForeignKey` to the `Main Topic` model, representing the Main Topic associated with the Sub Topic.

## Thread

// (work in progress)

## Comments

// (work in progress)

## Relationship between models

* A User can be associated with one User Information, and a User Information can be associated to only one User (one-to-one relationship)
* A User can be associated with multiple Roles, and a Role can be associated to many Users (many-to-many relationship)
* A User can have many Threads, but a Thread can only be associated with one User (one-to-many relationship).
* A Thread can have many Comments, but a Comment can only be associated with one Thread (one-to-many relationship).
* Either of the two:
A Main Topic can have many sub-topics, but a Comment can only be associated with one User (one-to-many relationship).
A Main Topic can be associated with multiple Sub-topics, and a Sub-topic can be associated to many Main Topic (many-to-many relationship)
* A Sub-topic can have many Thread, but a Thread can only be associated with one Sub-topic (one-to-many relationship).
