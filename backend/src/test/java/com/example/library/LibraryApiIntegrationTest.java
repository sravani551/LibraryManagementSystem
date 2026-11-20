package com.example.library;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class LibraryApiIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper json;

    private long createMember(String name, String email) throws Exception {
        String body = mvc.perform(post("/members").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"" + name + "\",\"email\":\"" + email + "\"}"))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        return json.readTree(body).get("id").asLong();
    }

    private long createBook(String isbn, int copies) throws Exception {
        String body = mvc.perform(post("/books").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Clean Code\",\"author\":\"Robert C. Martin\",\"isbn\":\"" + isbn
                                + "\",\"totalCopies\":" + copies + "}"))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        return json.readTree(body).get("id").asLong();
    }

    @Test
    void borrowAndReturnLifecycle() throws Exception {
        long member = createMember("Ada", "ada@example.com");
        long book = createBook("978-0132350884", 1);

        String record = mvc.perform(post("/members/{m}/borrow/{b}", member, book))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.bookTitle").value("Clean Code"))
                .andReturn().getResponse().getContentAsString();
        long recordId = json.readTree(record).get("id").asLong();

        mvc.perform(get("/books").param("title", "clean"))
                .andExpect(jsonPath("$.content[0].availableCopies").value(0));

        // last copy is out
        long other = createMember("Grace", "grace@example.com");
        mvc.perform(post("/members/{m}/borrow/{b}", other, book))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("No copies available"));

        mvc.perform(post("/borrow-records/{id}/return", recordId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.returnDate").isNotEmpty());
        mvc.perform(post("/borrow-records/{id}/return", recordId))
                .andExpect(status().isConflict());

        mvc.perform(get("/members/{m}/borrow-records", member))
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void duplicateIsbnIsConflictNotServerError() throws Exception {
        createBook("978-0201633610", 2);
        mvc.perform(post("/books").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Dup\",\"author\":\"X\",\"isbn\":\"978-0201633610\",\"totalCopies\":1}"))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value(containsString("already exists")));
    }

    @Test
    void validationErrorsAreReturnedPerField() throws Exception {
        mvc.perform(post("/members").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"\",\"email\":\"not-an-email\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.email").value("Email should be valid"));
    }

    @Test
    void unknownResourcesAre404() throws Exception {
        mvc.perform(get("/members/{id}", 999)).andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").exists());
        mvc.perform(post("/members/{m}/borrow/{b}", 999, 999)).andExpect(status().isNotFound());
    }

    @Test
    void malformedRequestsAre4xxNotServerErrors() throws Exception {
        mvc.perform(get("/members/{id}", "abc")).andExpect(status().isBadRequest());
        mvc.perform(post("/members").contentType(MediaType.APPLICATION_JSON).content("{not json"))
                .andExpect(status().isBadRequest());
    }
}
