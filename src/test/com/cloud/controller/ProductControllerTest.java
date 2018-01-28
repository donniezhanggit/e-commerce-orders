package com.cloud.controller;

import com.cloud.orders.domain.Product;
import com.cloud.orders.resource.ProductResource;
import com.cloud.orders.service.ProductService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductResource productResource = new ProductResource();

    private List<Product> productList;

    private Product product1;

    @Before
    public void setUp() {
        productList = new ArrayList<>();
        product1 = new Product("1257833283","9394550220002","Diva Jeans",new BigDecimal("39.99"));
        Product product2 = new Product("1358743283", "7394650110003", "Polo Shirt", new BigDecimal("19.99"));
        productList.add(product1);
        productList.add(product2);
    }

    @Test
    public void testProductService_listProducts_returnSuccess() {
        when(productService.listProducts()).thenReturn(productList);

        ResponseEntity<List<Product>> response = productResource.listProducts();

        Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
        Assert.assertEquals(response.getBody(), productList);
        Assert.assertEquals(((List)response.getBody()).size(), 2);
    }

    @Test
    public void testProductService_getProduct_returnSuccess() {
        when(productService.getProduct("1")).thenReturn(product1);

        ResponseEntity<Product> response = productResource.getProduct("1");

        Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
        Assert.assertEquals(response.getBody(), product1);
        Product product = (Product) response.getBody();
        Assert.assertEquals(product.getPrice().abs(), new BigDecimal("39.99").abs());
    }

    @Test
    public void testProductService_getProduct_returnFailure() {
        when(productService.getProduct(null)).thenReturn(null);

        ResponseEntity<Product> response = productResource.getProduct(null);

        Assert.assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
        Assert.assertEquals(response.getBody(), null);
    }

}
