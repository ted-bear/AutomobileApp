package com.toporkov.automobileapp.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AddressData {
    // Основные поля адреса
    @JsonProperty("postal_code")
    private String postalCode;

    @JsonProperty("country")
    private String country;

    @JsonProperty("country_iso_code")
    private String countryIsoCode;

    // Региональные данные
    @JsonProperty("federal_district")
    private String federalDistrict;

    @JsonProperty("region_fias_id")
    private String regionFiasId;

    @JsonProperty("region_kladr_id")
    private String regionKladrId;

    @JsonProperty("region_iso_code")
    private String regionIsoCode;

    @JsonProperty("region_with_type")
    private String regionWithType;

    @JsonProperty("region_type")
    private String regionType;

    @JsonProperty("region_type_full")
    private String regionTypeFull;

    @JsonProperty("region")
    private String region;

    // Район
    @JsonProperty("area_fias_id")
    private String areaFiasId;

    @JsonProperty("area_kladr_id")
    private String areaKladrId;

    @JsonProperty("area_with_type")
    private String areaWithType;

    @JsonProperty("area_type")
    private String areaType;

    @JsonProperty("area_type_full")
    private String areaTypeFull;

    @JsonProperty("area")
    private String area;

    // Город
    @JsonProperty("city_fias_id")
    private String cityFiasId;

    @JsonProperty("city_kladr_id")
    private String cityKladrId;

    @JsonProperty("city_with_type")
    private String cityWithType;

    @JsonProperty("city_type")
    private String cityType;

    @JsonProperty("city_type_full")
    private String cityTypeFull;

    @JsonProperty("city")
    private String city;

    @JsonProperty("city_area")
    private String cityArea;

    // Улица
    @JsonProperty("street_fias_id")
    private String streetFiasId;

    @JsonProperty("street_kladr_id")
    private String streetKladrId;

    @JsonProperty("street_with_type")
    private String streetWithType;

    @JsonProperty("street_type")
    private String streetType;

    @JsonProperty("street_type_full")
    private String streetTypeFull;

    @JsonProperty("street")
    private String street;

    // Дом и строение
    @JsonProperty("house_fias_id")
    private String houseFiasId;

    @JsonProperty("house_kladr_id")
    private String houseKladrId;

    @JsonProperty("house_cadnum")
    private String houseCadnum;

    @JsonProperty("house_flat_count")
    private Integer houseFlatCount;

    @JsonProperty("house_type")
    private String houseType;

    @JsonProperty("house_type_full")
    private String houseTypeFull;

    @JsonProperty("house")
    private String house;

    @JsonProperty("block_type")
    private String blockType;

    @JsonProperty("block_type_full")
    private String blockTypeFull;

    @JsonProperty("block")
    private String block;

    // Квартира
    @JsonProperty("flat_fias_id")
    private String flatFiasId;

    @JsonProperty("flat_cadnum")
    private String flatCadnum;

    @JsonProperty("flat_type")
    private String flatType;

    @JsonProperty("flat_type_full")
    private String flatTypeFull;

    @JsonProperty("flat")
    private String flat;

    @JsonProperty("flat_area")
    private String flatArea;

    // Координаты
    @JsonProperty("geo_lat")
    private String geoLat;

    @JsonProperty("geo_lon")
    private String geoLon;

    // Дополнительные поля
    @JsonProperty("fias_id")
    private String fiasId;

    @JsonProperty("fias_level")
    private String fiasLevel;

    @JsonProperty("kladr_id")
    private String kladrId;

    @JsonProperty("capital_marker")
    private String capitalMarker;

    @JsonProperty("okato")
    private String okato;

    @JsonProperty("oktmo")
    private String oktmo;

    @JsonProperty("tax_office")
    private String taxOffice;

    @JsonProperty("tax_office_legal")
    private String taxOfficeLegal;

    @JsonProperty("qc_geo")
    private String qcGeo;

    // Остальные поля можно добавить по аналогии
    // Все nullable-поля оставлены как String/Integer
}
