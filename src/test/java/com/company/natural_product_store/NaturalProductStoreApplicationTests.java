package com.company.natural_product_store;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static com.company.natural_product_store.enums.security.ApplicationUserRole.ADMIN;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class NaturalProductStoreApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    public void shouldGetHomePageForAnyUser() throws Exception {
        this.mockMvc.perform(get("/home").with(user("ThisIsATestUser").password("AndATestPassword")))
                .andExpect(status().isOk());
    }


    // GET ALL
    @Test
    public void shouldGetAdminPageIfUserAdmin() throws Exception {
        this.mockMvc.perform(get("/admin")
                        .with(httpBasic("admin", "1q2w3e")))
                .andExpect(status().isOk());

    }

    @Test
    public void shouldNotGetAdminPageIfUserIsNotAdmin() throws Exception {
        this.mockMvc.perform(get("/admin")
                        .with(user("b_user").password("1q2w3e").roles("USER")))
                .andExpect(status().isOk());

    }
}
