package com.company.natural_product_store.component;

import com.company.natural_product_store.entity.AppUser;
import com.company.natural_product_store.entity.Order;
import com.company.natural_product_store.entity.Product;
import com.company.natural_product_store.enums.security.ApplicationUserRole;
import com.company.natural_product_store.repository.OrderRepository;
import com.company.natural_product_store.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.Optional;

import static com.company.natural_product_store.mocks.OrderMock.getProducts;
import static com.company.natural_product_store.mocks.UserMock.getMockUser;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class OrderComponentTest {

    private static final String BASE_PATH = "/";
    private static final String ORDERS = "orders";
    private static final String USERNAME_DEFAULT_ADMIN = "admin";
    private static final String USERNAME_DEFAULT_USER = "b_user";
    private static final ApplicationUserRole ADMIN_ROLE = ApplicationUserRole.ADMIN;
    private static final ApplicationUserRole USER_ROLE = ApplicationUserRole.BASIC_USER;
    private static final ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;
    @MockBean
    private OrderRepository orderRepository;

//    public static String parseFromObject(Object objectToBeParsed) throws JsonProcessingException {
//        return mapper.writeValueAsString(objectToBeParsed);
//    }

    @Test
    void registerOrder_successfully() throws Exception {
        List<Product> products = getProducts();
        AppUser user = getMockUser(USERNAME_DEFAULT_ADMIN, ADMIN_ROLE);

        when(userRepository.findByUsername(USERNAME_DEFAULT_ADMIN)).thenReturn(Optional.of(user));
        var order = when(orderRepository.save(any())).thenReturn(AppUser.builder().id(13L).build());

        final MvcResult mvcResult = mockMvc.perform(
                        post(BASE_PATH + ORDERS + ((Order)order.getMock()).getId())
                                .servletPath(BASE_PATH + ORDERS)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                        // .content(parseFromObject(products))
                )
                .andExpect(status().isCreated())
                .andReturn();

        assertNotNull(mvcResult);
        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    @Test
    void getOrders_forAdminUser() throws Exception {
        AppUser user = getMockUser(USERNAME_DEFAULT_ADMIN, ADMIN_ROLE);

        //when(userRepository.findByUsername(USERNAME_DEFAULT_ADMIN)).thenReturn(Optional.of(user));
        //when(orderRepository.save(any())).thenReturn(User.builder().id(13L).build());

        final MvcResult mvcResult = mockMvc.perform(
                        get(BASE_PATH + ORDERS)
                                .servletPath(BASE_PATH + ORDERS)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .with(httpBasic("admin", "1q2w3e")))
                .andExpect(status().isOk())
                .andReturn();

        assertNotNull(mvcResult);
        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    @Test
    void getOrders_forBasicUser() throws Exception {
        AppUser user = getMockUser(USERNAME_DEFAULT_USER, USER_ROLE);

        final MvcResult mvcResult = mockMvc.perform(
                        get(BASE_PATH + ORDERS)
                                .with(httpBasic("b_user", "1q2w3e"))
                                .servletPath(BASE_PATH + ORDERS)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isForbidden())
                .andReturn();

        assertNotNull(mvcResult);
        System.out.println(mvcResult.getResponse().getContentAsString());
    }
}
