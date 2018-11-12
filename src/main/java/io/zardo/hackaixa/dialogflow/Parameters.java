package io.zardo.hackaixa.dialogflow;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Parameters {

    private String nome;

    private List<String> sobrenome;

    private String placeId;

    private Double lat1;

    private Double lat2;

    private Double lng1;

    private Double lng2;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<String> getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(List<String> sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public Double getLat1() {
        return lat1;
    }

    public void setLat1(Double lat1) {
        this.lat1 = lat1;
    }

    public Double getLat2() {
        return lat2;
    }

    public void setLat2(Double lat2) {
        this.lat2 = lat2;
    }

    public Double getLng1() {
        return lng1;
    }

    public void setLng1(Double lng1) {
        this.lng1 = lng1;
    }

    public Double getLng2() {
        return lng2;
    }

    public void setLng2(Double lng2) {
        this.lng2 = lng2;
    }
}
