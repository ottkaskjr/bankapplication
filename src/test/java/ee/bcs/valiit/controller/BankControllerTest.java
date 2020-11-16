package ee.bcs.valiit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc // Need this in Spring Boot test
public class BankControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void registrationWorksThroughAllLayers() throws Exception {
        mockMvc.perform(get("/bank/testing").contentType("application/json").param("param", "YAAY"))
                .andExpect(status().isOk()).andExpect(content().string(containsString("OK")));
    }

    @Test
    void registrationWorksThroughAllLayer3() throws Exception {
        mockMvc.perform(get("/bank/testingobject").contentType("application/json")).andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("value"));
    }

    @Test
    void registrationWorksThroughAllLayer2() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        RequestJSON requestJSON = new RequestJSON("5000");
        mockMvc.perform(post("/bank/testing/post").contentType("application/json").content(mapper.writeValueAsString(requestJSON)))
                .andExpect(status().isOk()).andExpect(content().string(containsString("OK")));
    }

    @Test
    void registrationWorksThroughAllLayer4() throws Exception {
        mockMvc.perform(put("/bank/testing/put/{id}", "123456").contentType("application/json")).andExpect(status().isOk()).andExpect(content().string(containsString("OK")));
    }
}

