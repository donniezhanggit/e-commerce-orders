package com.cloud.integration;

import com.cloud.orders.LaunchApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = LaunchApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-non-prod.properties")
public class OrderControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void whenGetOrders_thenStatus200()
            throws Exception {
        mvc.perform(get("/orders"))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].orderNumber", is("RTL_1001")))
                .andExpect(jsonPath("$[0].taxPercent", is(10.0)))
                .andExpect(jsonPath("$[0].status", is("SHIPPED")))
                .andExpect(jsonPath("$[1].orderNumber", is("RTL_1002")))
                .andExpect(jsonPath("$[1].taxPercent", is(10.0)))
                .andExpect(jsonPath("$[1].status", is("FULFILLED")));
    }

    @Test
    public void givenOrders_whenGetOrder_thenStatus200()
            throws Exception {
        mvc.perform(get("/orders" + "/3"))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("orderNumber", is("RTL_1003")))
                .andExpect(jsonPath("discount", is(19.99)))
                .andExpect(jsonPath("status", is("SHIPPED")));
    }

    @Test
    public void whenSearchOrders_thenStatus200()
            throws Exception {
        mvc.perform(get("/orders/search?orderStatus=SHIPPED&minimumProductPrice=30&productCount=2"))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].orderNumber", is("RTL_1003")))
                .andExpect(jsonPath("$[0].discount", is(19.99)))
                .andExpect(jsonPath("$[0].status", is("SHIPPED")));
    }

}
