package ru.sberjavaproject.models;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Objects;

public class SocksRequest {

    private final String operation;
    private final String color;

    @Min(value = 0, message = "Cotton part cant be less then 0")
    @Max(value = 100, message = "Cotton part cant be greater then 100")
    private Integer cottonPart;

    public SocksRequest( String color, String operation, Integer cottonPart) {
        this.operation = operation;
        this.color = color;
        this.cottonPart = cottonPart;
    }

    public String getOperation() {
        return operation;
    }

    public String getColor() {
        return color;
    }

    public Integer getCottonPart() {
        return cottonPart;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SocksRequest that = (SocksRequest) o;
        return Objects.equals(operation, that.operation) && Objects.equals(color, that.color) && Objects.equals(cottonPart, that.cottonPart);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operation, color, cottonPart);
    }

    public void setCottonPart(Integer cottonPart) {
        this.cottonPart = cottonPart;


    }
}
