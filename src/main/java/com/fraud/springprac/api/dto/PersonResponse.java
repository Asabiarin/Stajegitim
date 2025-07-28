package com.fraud.springprac.api.dto;

import com.fraud.springprac.api.model.Person;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;

@Data
public class PersonResponse {
    private List<PersonDto> content;
    private int pageNO;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean last;

    public static PersonResponse fromPage(
            Page<Person> page,
            Function<Person, PersonDto> mapper
    ) {
        PersonResponse response = new PersonResponse();
        response.setContent(page.getContent().stream().map(mapper).toList());
        response.setPageNO(page.getNumber());
        response.setPageSize(page.getSize());
        response.setTotalElements(page.getTotalElements());
        response.setTotalPages(page.getTotalPages());
        response.setLast(page.isLast());
        return response;
    }
}
