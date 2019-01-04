package org.sendoh.controller;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.sendoh.repository.QueryLoaderException;
import org.sendoh.request.QueryParam;
import org.sendoh.service.MyService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Set;

@RestController
@RequestMapping("/sample")
@Slf4j
public class MySimpleController {
    private final MyService myService;
    private final PagedResourcesAssembler<JsonNode> pagedResourcesAssembler;


    public MySimpleController(MyService myService, PagedResourcesAssembler<JsonNode> assembler) {
        this.myService = myService;
        this.pagedResourcesAssembler = assembler;
    }

    @GetMapping(value = "/v1", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<PagedResources> getData(@RequestParam("start")
                                           @DateTimeFormat(pattern = "yyyyMMdd") LocalDate startDate,
                                           @RequestParam("end")
                                           @DateTimeFormat(pattern = "yyyyMMdd") LocalDate endDate,
                                           @RequestParam(value = "groupBy") Set<String> groupBys,
                                           Pageable pageable) {

        try {
            QueryParam queryParam = new QueryParam(startDate, endDate, groupBys);

            Page<JsonNode> tableResponse = myService.getData(queryParam, pageable);

            //  this builds the response with self, previous, next and last
            PagedResources pagedResources = pagedResourcesAssembler.toResource(tableResponse);


            return new ResponseEntity<>(pagedResources, HttpStatus.PARTIAL_CONTENT);

        } catch (QueryLoaderException | IOException e) {
            log.error("Exception when getting data. Please contact the maintainer", e);
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
