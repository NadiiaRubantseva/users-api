package ua.nrubantseva.api.users.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import ua.nrubantseva.api.users.exception.EntityIdNotFoundException;
import ua.nrubantseva.api.users.exception.UserAgeRestrictionException;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    /**
     * Tests that findUserByRange returns 200 when the date range is correct.
     *
     * @throws Exception if there is an error performing the MVC request.
     */
    @Test
    void findUserByRange_ShouldReturn200_WhenRangeIsCorrect() throws Exception {
        createBasicUser();

        mockMvc.perform(get("/users")
                        .param("fromDate", "2003-07-28")
                        .param("toDate", "2003-07-30"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    /**
     * Tests that findUserByRange returns 404 when the date range is incorrect.
     *
     * @throws Exception if there is an error performing the MVC request.
     */
    @Test
    void findUserByRange_ShouldReturn404_WhenRangeIsIncorrect() throws Exception {
        createBasicUser();

        MvcResult mvcResult = mockMvc.perform(get("/users")
                        .param("fromDate", "2043-07-28")
                        .param("toDate", "2003-07-30"))
                .andExpect(status().isBadRequest())
                .andReturn();

        assertThat(mvcResult.getResolvedException())
                .isInstanceOf(MethodArgumentNotValidException.class);
    }

    /**
     * Tests that createUser returns 201 when user data is correct.
     *
     * @throws Exception if there is an error performing the MVC request.
     */
    @Test
    void createUser_ShouldReturn201_WhenUserDataCorrect() throws Exception {
        mockMvc.perform(post("/users")
                        .param("email", "nadiiarubants@gmail.com")
                        .param("firstName", "nadiia")
                        .param("lastName", "rubants")
                        .param("birthDate", "1997-01-01"))
                .andExpect(status().isCreated());
    }

    /**
     * Tests that createUser returns 404 when the user has age restriction.
     *
     * @throws Exception if there is an error performing the MVC request.
     */
    @Test
    void createUser_ShouldReturn404_WhenUserHasAgeRestriction() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/users")
                        .param("email", "nadiiarubants@gmail.com")
                        .param("firstName", "nadiia")
                        .param("lastName", "rubants")
                        .param("birthDate", "2023-01-01"))
                .andExpect(status().isBadRequest())
                .andReturn();

        assertThat(mvcResult.getResolvedException())
                .isInstanceOf(UserAgeRestrictionException.class)
                .hasMessageContainingAll("User must be more than 18 age");
    }

    /**
     * Tests that createUser returns 404 when the user has an incorrect email.
     *
     * @throws Exception if there is an error performing the MVC request.
     */
    @Test
    void createUser_ShouldReturn404_WhenUserHasIncorrectEmail() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/users")
                        .param("email", "nadiiarubantsgmail.com")
                        .param("firstName", "nadiia")
                        .param("lastName", "rubants")
                        .param("birthDate", "1997-01-01"))
                .andExpect(status().isBadRequest())
                .andReturn();

        assertThat(mvcResult.getResolvedException())
                .isInstanceOf(MethodArgumentNotValidException.class);
    }

    /**
     * Tests that updateUser returns 200 when the user ID exists, and data is correct.
     *
     * @throws Exception if there is an error performing the MVC request.
     */
    @Test
    void updateUser_ShouldReturn200_WhenUserIdExistAndDataIsCorrect() throws Exception {
        String userId = createBasicUser();

        mockMvc.perform(put("/users/" + userId)
                        .param("email", "nadiiarubants@gmail.com")
                        .param("firstName", "nadiia")
                        .param("lastName", "rubants")
                        .param("birthDate", "1997-01-01"))
                .andExpect(status().isOk());
    }

    /**
     * Tests that updateUser returns 400 when the user ID does not exist.
     *
     * @throws Exception if there is an error performing the MVC request.
     */
    @Test
    void updateUser_ShouldReturn400_WhenUserIdDoesNotExist() throws Exception {
        String userId = UUID.randomUUID().toString();

        MvcResult mvcResult = mockMvc.perform(put("/users/" + userId)
                        .param("email", "nadiiarubants@gmail.com")
                        .param("firstName", "nadiia")
                        .param("lastName", "rubants")
                        .param("birthDate", "1997-01-01"))
                .andExpect(status().isNotFound())
                .andReturn();

        assertThat(mvcResult.getResolvedException())
                .isInstanceOf(EntityIdNotFoundException.class);
    }

    /**
     * Tests that updateUser returns 404 when the user has age restriction.
     *
     * @throws Exception if there is an error performing the MVC request.
     */
    @Test
    void updateUser_ShouldReturn404_WhenUserHasAgeRestriction() throws Exception {
        String userId = createBasicUser();

        MvcResult mvcResult = mockMvc.perform(put("/users/" + userId)
                        .param("email", "nadiiarubants@gmail.com")
                        .param("firstName", "nadiia")
                        .param("lastName", "rubants")
                        .param("birthDate", "2023-01-01"))
                .andExpect(status().isBadRequest())
                .andReturn();

        assertThat(mvcResult.getResolvedException())
                .isInstanceOf(UserAgeRestrictionException.class)
                .hasMessageContainingAll("User must be more than 18 age");
    }

    /**
     * Tests that updateUserEmail returns 200 when the user ID exists, and data is correct.
     *
     * @throws Exception if there is an error performing the MVC request.
     */
    @Test
    void updateUserEmail_ShouldReturn200_WhenUserIdExistAndDataIsCorrect() throws Exception {
        String userId = createBasicUser();

        mockMvc.perform(put("/users/" + userId + "/email")
                        .param("email", "email@gmail.com"))
                .andExpect(status().isOk());
    }

    /**
     * Tests that updateUserEmail returns 400 when the user ID does not exist.
     *
     * @throws Exception if there is an error performing the MVC request.
     */
    @Test
    void updateUserEmail_ShouldReturn400_WhenUserIdDoesNotExist() throws Exception {
        String userId = UUID.randomUUID().toString();

        MvcResult mvcResult = mockMvc.perform(put("/users/" + userId + "/email")
                        .param("email", "email@gmail.com"))
                .andExpect(status().isNotFound())
                .andReturn();

        assertThat(mvcResult.getResolvedException())
                .isInstanceOf(EntityIdNotFoundException.class);
    }

    /**
     * Tests that deleteUser returns 200 when the user ID exists.
     *
     * @throws Exception if there is an error performing the MVC request.
     */
    @Test
    void deleteUser_ShouldReturn200_WhenUserIdExist() throws Exception {
        String userId = createBasicUser();

        mockMvc.perform(delete("/users/" + userId))
                .andExpect(status().isOk());
    }

    /**
     * Tests that deleteUser returns 400 when the user ID does not exist.
     *
     * @throws Exception if there is an error performing the MVC request.
     */
    @Test
    void deleteUser_ShouldReturn400_WhenUserIdDoesNotExist() throws Exception {
        String userId = UUID.randomUUID().toString();

        MvcResult mvcResult = mockMvc.perform(delete("/users/" + userId))
                .andExpect(status().isNotFound())
                .andReturn();

        assertThat(mvcResult.getResolvedException())
                .isInstanceOf(EntityIdNotFoundException.class);
    }

    private String createBasicUser() throws Exception {
        return mockMvc.perform(post("/users")
                        .param("email", "email@gmail.com")
                        .param("firstName", "nadiia")
                        .param("lastName", "rubant")
                        .param("birthDate", "2003-07-28"))
                .andReturn()
                .getResponse()
                .getContentAsString().substring(7, 43);
    }
}
