package com.solt.jdc.boot.domains;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import com.solt.jdc.boot.validators.customAnnotations.CharacterConstraint;
import com.solt.jdc.boot.validators.customAnnotations.ContactNumberConstraint;



@Entity(name = "customer")
@Table(uniqueConstraints=@UniqueConstraint(columnNames="email"))
@Data
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Size(min = 4)
    private String username;

    @CharacterConstraint(message = "Your's frist name should be only character")//moe
    private String firstName;

    @CharacterConstraint(message = "Your's last name should be only character")//moe
    private String lastName;

    @NotNull
    @NotBlank(message = "Please enter password")
    @Size(min = 8)
    private String password;

    @Transient
    private String matchPassword;

    @ContactNumberConstraint
    private String phone;

    @ManyToOne
    private Role role;

    @NotNull
    @NotBlank(message = "Please enter  your address ")
    private String address;

    @Email(message = "This is not email type")
    private String email;

    private String nrcNumber;

    @Column(name = "enabled")
    private boolean enabled;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
    private List<Booking> bookingList=new ArrayList<>();

    private String tempPassword;
    
}

