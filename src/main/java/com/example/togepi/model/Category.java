package com.example.togepi.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "categories")
@Data
public class Category implements Serializable {

    @Id
    @SequenceGenerator(name="category_generator", sequenceName="category_sequence", allocationSize = 1)
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="category_generator")
    private Long id;

    @Column(name = "name", length = 50, nullable = false)
    @NotBlank
    private String name;

    @Column(name = "description", columnDefinition = "text", nullable = false)
    @NotBlank
    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    private Date updatedAt;
}
