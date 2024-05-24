package com.catalog.CatalogMicroservice.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "products")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "product_name",unique = true)
    private String name;
    @Column(name = "short_description")
    private String shortDescription;
    @Column(name = "long_description")
    private String longDescription;
    @Column(name = "stock_quantity")
    private int stockQuantity;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "created_at")
    @CreationTimestamp
    private Date createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private Date updatedAt;

    @Column(name = "delete_at")
    @UpdateTimestamp
    private Date deleteAt;

    @Column(name = "image")
    private String image;
    @Column(name = "is_active")
    private boolean isActive;

    @ManyToOne
   // @JoinColumn(name = "category_id")
   // @JsonBackReference
    private Category category;

    @ManyToOne
    //comment this if error
    @JoinColumn(name = "subcategory_id")
    private SubCategory subCategory;

}
