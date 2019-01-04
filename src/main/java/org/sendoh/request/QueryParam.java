package org.sendoh.request;

import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Set;

@ToString
@Getter
public class QueryParam {
    private LocalDate start;
    private LocalDate end;
    private Set<String> groupBys;

    public QueryParam(final LocalDate start, final LocalDate end, final Set<String> groupBys) {
        this.start = start;
        this.end = end;
        this.groupBys = groupBys;
    }
}
