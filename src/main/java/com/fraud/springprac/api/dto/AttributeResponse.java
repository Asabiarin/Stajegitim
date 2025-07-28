package com.fraud.springprac.api.dto;

import lombok.Data;

@Data
public class AttributeResponse {
private String key;
private String value;
private int pageNo;
private int pageSize;
}
