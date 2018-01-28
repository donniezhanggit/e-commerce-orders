Objective
=========
Existing micro Service to fetch order and product information enhanced to add a generic `Search REST API` to display a list of potential matches based on the following criteria :

Configuration, Architecture, Logic, Exception Handling, Logs, Spring, Persistence, REST, Tests, Documentation

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