package com.fraud.springprac.api.dto;

import lombok.Data;

import java.util.List;
@Data
public class PersonResponse {
    private List<PersonDto> content;
    private int pageNO;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean last;
}
