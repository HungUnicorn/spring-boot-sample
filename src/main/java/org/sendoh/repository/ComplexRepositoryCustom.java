package org.sendoh.repository;

import com.fasterxml.jackson.databind.JsonNode;
import org.sendoh.request.QueryParam;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;

public interface ComplexRepositoryCustom {

    List<JsonNode> findPagedResultByQueryParam(QueryParam queryParam, Pageable pageable) throws IOException, QueryLoaderException;

    long count(QueryParam queryParam) throws QueryLoaderException;
}
