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
public class ProductControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void whenGetProducts_thenStatus200()
            throws Exception {
        mvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].upc", is("1257833283")))
                .andExpect(jsonPath("$[0].sku", is("9394550220002")))
                .andExpect(jsonPath("$[0].description", is("Diva Jeans")))
                .andExpect(jsonPath("$[0].price", is(39.99)))
                .andExpect(jsonPath("$[1].upc", is("1358743283")))
                .andExpect(jsonPath("$[1].sku", is("7394650110003")))
                .andExpect(jsonPath("$[1].description", is("Polo Shirt")))
                .andExpect(jsonPath("$[1].price", is(19.99)));
    }

    @Test
    public void givenProducts_whenGetProduct_thenStatus200()
            throws Exception {
        mvc.perform(get("/products" + "/1"))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("upc", is("1257833283")))
                .andExpect(jsonPath("sku", is("9394550220002")))
                .andExpect(jsonPath("description", is("Diva Jeans")))
                .andExpect(jsonPath("price", is(39.99)));
    }

}
