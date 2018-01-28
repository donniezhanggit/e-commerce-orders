Summary
=======
Existing micro Service to fetch order and product information enhanced to add a generic `Search REST API` to display a list of potential matches (utilizing JPA Sepcifications) based on the following parameters :

1) Filter all the orders based on shipment status
2) Filter all the orders based on discount status
3) Filter all the orders based on number of products
4) Filter all the products based on price


API Endpoints
======================
1) List Orders:
   **[GET]** `http://localhost:8088/orders`

2) Fetch Order Details:
   **[GET]** `http://localhost:8088/orders/{order_id}`

3) Search Orders:
   **[GET]** `http://localhost:8088/orders/search?orderStatus={orderStatus}&minimumProductPrice={minimumProductPrice}&productCount={productCount}`

4) List Products:
   **[GET]** `http://localhost:8088/products`

5) Fetch Product Details:
   **[GET]** `http://localhost:8088/products/{product_id}`


Tech Stack
==========
 * Java 8.x
 * Maven 3.x
 * Spring Framework 4.x
 * Spring Boot 1.5.6
 * Hibernate
 * JPA
 * H2 database
 * JUnit 4.x
 * Mockito 2.x
 * Spring Integration Tests
 
 
Running Application
===================
2 ways to run and test the application viz.

1) Running 'LaunchApplication' class as a Spring boot configuration with profile set as 'non-prod' (for reference, 'LaunchApplication.png' image has been added) and then test using Postman scripts (scripts have been uploaded to repo)

2) Running locally using maven and test by running Postman scripts (Orders.postman_collection.json has been added)

               Build : mvn clean install -Dspring.profiles.active=non-prod
               Run locally: mvn spring-boot:run -Drun.profiles=non-prod


Running Unit/Integration Tests
==============================
** Running all tests
   1) Right-click on the src/test folder and run all tests

** Running individual test classes
   1) Right-click each test class and run the test
