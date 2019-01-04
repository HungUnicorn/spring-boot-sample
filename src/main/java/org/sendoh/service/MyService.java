package org.sendoh.service;

import com.fasterxml.jackson.databind.JsonNode;
import org.sendoh.repository.QueryLoaderException;
import org.sendoh.request.QueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;

public interface MyService {

    Page<JsonNode> getData(QueryParam queryParam, Pageable pageable) throws QueryLoaderException, IOException;
}
