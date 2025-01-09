package com.myapp.dto;

import java.util.List;

public class PaginationResult {
    public List<SpringReactResponse> getResponseList() {
        return responseList;
    }

    public void setResponseList(List<SpringReactResponse> responseList) {
        this.responseList = responseList;
    }

    List<SpringReactResponse> responseList;
    Long totalRecords;

    public Long getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(Long totalRecords) {
        this.totalRecords = totalRecords;
    }
}
