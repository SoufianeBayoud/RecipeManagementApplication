package com.cerbar.cerbar.model;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import com.fasterxml.jackson.annotation.JsonIdentityReference;


import java.util.Date;
import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "Recipe")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "Name")
    private String name;
    @Column(name = "Description")
    private String description;

    @Column(name = "Instruction")
    private String instruction;

    @CreationTimestamp
    @Column(name = "Created at", nullable = false, updatable = false)
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
    //@JsonIdentityReference(alwaysAsId = true)
    @JsonManagedReference
    private List<Ingredient> ingredients;



}
