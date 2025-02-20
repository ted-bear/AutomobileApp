package com.toporkov.automobileapp.web.dto;

import java.util.List;

public class EnterpriseListDTO {

    private List<EnterpriseDTO> enterprises;

    public EnterpriseListDTO(final List<EnterpriseDTO> enterprises) {
        this.enterprises = enterprises;
    }

    public List<EnterpriseDTO> getEnterprises() {
        return enterprises;
    }

    public void setEnterprises(List<EnterpriseDTO> enterprises) {
        this.enterprises = enterprises;
    }
}
