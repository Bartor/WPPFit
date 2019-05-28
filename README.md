# WPPFit

Fitness tracker for Android created as a project for a university course. It uses **ViewModel**, **Room**, **Retrofit** and **Dagger**, is built mostly using fragments, supports multiple user sessions and more.

## Functionality
The app allows user to create a profile which consists of their age, gender, name, surname, weight and height. This data is used to calculate a calories intake for the user. The user can later choose their desired weight and how quickly he wants to achieve it. The application uses Food API to search for, calculate details and add meals to the database as well as provides several pre-defined physical activities to choose from. The calories from the meals and exercises are added up and displayed on the main screen right next to the carbs, fats and protein levels.

## Project structure
The project consists of several directories:
 - [fragments](app/src/main/java/various/coders/wppfit/fragments) - all fragments used in the application with interfaces to interact within them and subdirectories `adapters` and `dialogs` which hold recycler adapters and dialogs which pop up to configure something respectively
 - [model](app/src/main/java/various/coders/wppfit/model) - a data model used by the application as well as some utilities to manipulate the data. It holds several more directories:
    - [calc](app/src/main/java/various/coders/wppfit/model/calc) - calculators
    - [database](app/src/main/java/various/coders/wppfit/model/database) - entities and daos for the Room library
    - [di](app/src/main/java/various/coders/wppfit/model/di) - dependency injection stuff related to Dagger
    - [web](app/src/main/java/various/coders/wppfit/model/web) - Retrofit pojos and services
- [support](app/src/main/java/various/coders/wppfit/support) - support files, mainly `SynchronizedCaller` which is used to synchronize a function call which requires data from different async sources

## Documentation
All files are or are to be properly documented.