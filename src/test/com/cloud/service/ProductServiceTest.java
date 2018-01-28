package com.cloud.service;

import com.cloud.orders.domain.Product;
import com.cloud.orders.repository.ProductRepository;
import com.cloud.orders.service.ProductService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService = new ProductService();

    private List<Product> productList;

    private Product product1;

    @Before
    public void setUp() {
        productList = new ArrayList<>();
        product1 = new Product("1257833283","9394550220002","Diva Jeans", new BigDecimal("39.99"));
        Product product2 = new Product("1358743283", "7394650110003", "Polo Shirt", new BigDecimal("19.99"));
        productList.add(product1);
        productList.add(product2);
    }

    @Test
    public void testProductService_listProducts_returnSuccess() {
        when(productRepository.findAll()).thenReturn(productList);

        List<Product> products = productService.listProducts();

        Assert.assertEquals(products.size(), 2);
        Assert.assertEquals(products.get(0).getPrice(), new BigDecimal("39.99"));
        Assert.assertEquals(products.get(0).getUpc(), "1257833283");
    }

    @Test
    public void testProductService_getProduct_returnSuccess() {
        when(productService.getProduct("1")).thenReturn(product1);

        Product response = productService.getProduct("1");

        Assert.assertEquals(response.getPrice(), new BigDecimal("39.99"));
        Assert.assertEquals(response.getUpc(), "1257833283");
    }

}
