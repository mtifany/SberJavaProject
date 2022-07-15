package ru.sberjavaproject.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Table(name = "color")
public class Color{

        @Id
        @Column(name = "id")
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

        @Column(name ="ColorName", nullable = false, unique = true)
        @NotNull
        private String colorName;


        public Color() {
        }

        public Color(Integer id, String colorName) {
                this.id = id;
                this.colorName = colorName;
        }

        public Integer getId() {
                return id;
        }

        public void setId(Integer id) {
                this.id = id;
        }

        public String getColorName() {
                return colorName;
        }

        public void setColorName(String colorName) {
                this.colorName = colorName;
        }

        @Override
        public String toString() {
                return "Color{" +
                        "id=" + id +
                        ", colorName='" + colorName + '\'' +
                        '}';
        }

        @Override
        public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                Color color = (Color) o;
                return  Objects.equals(colorName, color.colorName);
        }

        @Override
        public int hashCode() {
                return Objects.hash(colorName);
        }
}
