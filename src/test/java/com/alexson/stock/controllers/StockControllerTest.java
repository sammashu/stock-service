package com.alexson.stock.controllers;

import com.alexson.stock.StockApplication;
import com.alexson.stock.configs.H2Config;
import com.alexson.stock.models.DowJonesDataSetDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebAppConfiguration
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {StockApplication.class, H2Config.class})
public class StockControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Test
    public void uploadFileTest() throws Exception {
        MockMultipartFile firstFile = new MockMultipartFile("file", "filename.txt", "text/plain", "some xml".getBytes());
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mockMvc.perform(MockMvcRequestBuilders.multipart("/v1/stock/upload")
                .file(firstFile))
                .andExpect(status().is(200));
    }

    @Test
    public void insertNewItem() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        DowJonesDataSetDTO dowJonesDataSetDTO = new DowJonesDataSetDTO();
        dowJonesDataSetDTO.setDate("1/1/2019");
        dowJonesDataSetDTO.setQuarter("1");
        dowJonesDataSetDTO.setStock("AA");
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(dowJonesDataSetDTO);
        mockMvc.perform(post("/v1/stock/newStockTicker")
         .contentType(MediaType.APPLICATION_JSON)
         .content(requestJson)).andExpect(status().isOk());
    }
}
