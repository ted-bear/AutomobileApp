package com.toporkov.automobileapp.web.dto;

import java.util.List;

public class EnterpriseListDto {

    private List<EnterpriseDto> enterprises;

    public EnterpriseListDto(final List<EnterpriseDto> enterprises) {
        this.enterprises = enterprises;
    }

    public List<EnterpriseDto> getEnterprises() {
        return enterprises;
    }

    public void setEnterprises(List<EnterpriseDto> enterprises) {
        this.enterprises = enterprises;
    }
}
