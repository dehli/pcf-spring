package io.dehli.spring.models;

public class NameResponse {

    private String name;

    private Boolean cached;

    public NameResponse(String name, Boolean cached) {
        this.name = name;
        this.cached = cached;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getCached() {
        return cached;
    }

    public void setCached(Boolean cached) {
        this.cached = cached;
    }
}
