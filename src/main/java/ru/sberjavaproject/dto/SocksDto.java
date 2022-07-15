package ru.sberjavaproject.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

public class SocksDto {

    @NotBlank(message = "Socks should have a color!")
    private String color;

    @NotNull(message = "Wrong cottonPart")
    @Min(value = 0, message = "Cotton part cant be less then 0")
    @Max(value = 100, message = "Cotton part cant be greater then 100")
    private Integer cottonPart;

    @NotNull(message = "Wrong quantity")
    @Min(value = 1, message = "Quantity can't be less then  1")
    private Integer quantity;


    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getCottonPart() {
        return cottonPart;
    }

    public void setCottonPart(Integer cottonPart) {
        this.cottonPart = cottonPart;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public SocksDto(String color, Integer cottonPart, Integer quantity) {
        this.color = color;
        this.cottonPart = cottonPart;
        this.quantity = quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "SocksDto{" +
                "color=" + color +
                ", cottonPart=" + cottonPart +
                ", quantity='" + quantity + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SocksDto socksDto = (SocksDto) o;
        return Objects.equals(color, socksDto.color) && Objects.equals(cottonPart, socksDto.cottonPart) && Objects.equals(quantity, socksDto.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, cottonPart, quantity);
    }
}
