package com.solt.jdc.boot.domains;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.hibernate.annotations.Cascade;
import org.hibernate.validator.constraints.NotBlank;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "bus")
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Bus {

    @Id
    private Integer id;

    @NotBlank(message = "Bus Number cannot be blank")
    private String busNumber;

    @Min(value = 20, message = "Maximum seats must be atleast 20 seats")
    private int maxSeats;

    private int takenSeats = 0;
    @NotBlank(message = "Please enter name of the bus company")
    private String busCompany;
    @NotBlank(message = "Please enter bus code")
    private String busCode;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bus_type_id")
    private BusType busType;

    @OneToMany(mappedBy = "bus", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Services> servicesList = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "bus_station_id")
    private Station station;


}
