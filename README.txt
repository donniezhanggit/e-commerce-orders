** Running the application

2 ways to run and test the application viz.

   1) Running 'LaunchApplication' class as a Spring boot configuration with profile set as 'non-prod' (for reference, 'LaunchApplication.png' image has been added)
        and then test using Postman scripts (scripts have been uploaded to repo)

   2) Running locally using maven and test by running Postman scripts (Orders.postman_collection.json has been added)

               Build : mvn clean install -Dspring.profiles.active=non-prod
               Run locally: mvn spring-boot:run -Drun.profiles=non-prod



** Running all tests
   1) Right-click on the src/test folder and run all tests

** Running individual test classes
   1) Right-click each test class and run the test