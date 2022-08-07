package com.example.finalProject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "Name shouldn't be null")
    @NotBlank
    private String name;

    @DecimalMin("0.01")
    private Double price;

    @NotNull(message = "Category shouldn't be null")
    @NotBlank
    private String category;

    @Column(unique=true)
    @NotNull(message = "Unique code shouldn't be null")
    @NotBlank
    private String uniqueCode;

    @NotNull(message = "Measure unit shouldn't be null")
    @NotBlank
    private String measureUnit;

    @Min(1)
    private Integer quantity;

    @JsonIgnore
    @ManyToMany(mappedBy = "products")
    private List<Invoice> invoices;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", category='" + category + '\'' +
                ", uniqueCode='" + uniqueCode + '\'' +
                ", measureUnit='" + measureUnit + '\'' +
                ", quantity=" + quantity +
                ", supplier=" + supplier +
                '}';
    }
}
