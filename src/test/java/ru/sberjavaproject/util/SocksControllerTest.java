package ru.sberjavaproject.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.sberjavaproject.controllers.SocksController;
import ru.sberjavaproject.dto.SocksDto;
import ru.sberjavaproject.models.SocksRequest;
import ru.sberjavaproject.services.SocksServiceImpl;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(value = SocksController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
@AutoConfigureMockMvc(addFilters = false)
public class SocksControllerTest {


    @MockBean
    private SocksServiceImpl socksService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void incomeSocksTest() throws Exception {
        SocksDto sockDto = new SocksDto("Black", 50, 10);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/socks/income")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sockDto)))
                .andExpect(status().isOk());
    }

    @Test
    void outcomeSocksTest() throws Exception {
        SocksDto sockDto = new SocksDto("white", 100, 200);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/socks/outcome")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sockDto)))
                .andExpect(status().isOk());
    }

    @Test
    void SocksNotFoundExceptionTest() throws Exception {
        SocksDto sockDto = new SocksDto("white", 100, 200);
        Mockito.doThrow(SocksNotFoundException.class).when(socksService).outcome(sockDto);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/socks/outcome")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sockDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getSocksRequestTest() throws Exception{
        Mockito.when(socksService.getSocksRequest(new SocksRequest("blue","moreThan", 50)))
                .thenReturn(100L);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/socks")
                        .param("color", "blue")
                        .param("operation","moreThan")
                        .param("cottonPart", "50"))
                .andExpect(status().isOk()).andExpect(content().json(objectMapper.writeValueAsString(100L)));
    }
    @Test
    void InvalidRequestExceptionTest() throws Exception{
        Mockito.when(socksService.getSocksRequest(new SocksRequest("blue","empty", -50)))
                .thenThrow(InvalidRequestException.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/socks")
                        .param("color", "blue")
                        .param("operation","empty")
                        .param("cottonPart", "-50"))
                .andExpect(status().isBadRequest());
    }


}
