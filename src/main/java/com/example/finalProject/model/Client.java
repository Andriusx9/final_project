package com.example.finalProject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "name shouldn't be null")
    @NotBlank
    private String name;

    @Column(unique=true)
    @Pattern(regexp = "(LT-?)?([0-9]{9}|[0-9]{12})")
    private String pvmCode;

    @Column(unique=true)
    @NotNull(message = "Company code shouldn't be null")
    @NotBlank
    private String companyCode;

    @NotNull(message = "Address shouldn't be null")
    @NotBlank
    private String address;

    @Pattern(regexp = "(86|\\+3706)\\d{7}")
    private String phoneNumber;

    @Email
    private String email;

    @JsonIgnore
    @OneToMany(mappedBy = "client")
    private List<Invoice> invoices;

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", pvmCode='" + pvmCode + '\'' +
                ", companyCode='" + companyCode + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
