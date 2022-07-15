package ru.sberjavaproject.models;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Table(name = "socks")
public class Socks {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "quantity")
    @NotNull
    private Integer quantity;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "color_id", unique = false)
    private Color color;

    @Column(name = "cottonPart")
    @NotNull
    @Min(value = 0, message = "Min value is 0")
    @Max(value = 100, message = "Max value is 100")
    private Integer cottonPart;

    public Socks(Integer id, Integer quantity, Color color, Integer cottonPart) {
        this.id = id;
        this.quantity = quantity;
        this.color = color;
        this.cottonPart = cottonPart;
    }

    public Socks() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Integer getCottonPart() {
        return cottonPart;
    }

    public void setCottonPart(Integer cottonPart) {
        this.cottonPart = cottonPart;
    }

    @Override
    public String toString() {
        return "Socks{" +
                "quantity='" + quantity + '\'' +
                ", color=" + color +
                ", cottonPart=" + cottonPart +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Socks socks = (Socks) o;
        return Objects.equals(quantity, socks.quantity) && Objects.equals(color, socks.color) && Objects.equals(cottonPart, socks.cottonPart);
    }

    @Override
    public int hashCode() {
        return Objects.hash(quantity, color, cottonPart);
    }
}
