package org.sendoh.controller;

import com.fasterxml.jackson.databind.JsonNode;
import org.sendoh.repository.QueryLoaderException;
import org.sendoh.service.MyService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(MySimpleController.class)
public class MySimpleControllerTest {

    @MockBean
    private MyService myService;

    @MockBean
    private PagedResourcesAssembler<JsonNode> pagedResourcesAssembler;

    @Autowired
    private MockMvc mvc;

    @Test
    public void getData_noRequiredDates_4xx() throws Exception {
        mvc.perform(get("/sample/v1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void getData_wrongDateFormat_4xx() throws Exception {
        mvc.perform(get("/sample/v1")
                .param("start", "2012-12-12")
                .param("end","2012-12-13")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }


    @Test
    public void getData_correctParams_success() throws Exception {
        mvc.perform(get("/sample/v1")
                .param("start", "20181201")
                .param("end","20181202")
                .param("groupBy", "country")
                .param("page", Integer.toString(0))
                .param("size", Integer.toString(20))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void getData_givenException_serverError() throws Exception {
        when(myService.getData(any(), any())).thenThrow(new QueryLoaderException("test"));

        mvc.perform(get("/sample/v1")
                .param("start", "20181212")
                .param("end","20181213")
                .param("groupBy", "country")
                .param("page", Integer.toString(1))
                .param("size", Integer.toString(10))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is5xxServerError());
    }
}
