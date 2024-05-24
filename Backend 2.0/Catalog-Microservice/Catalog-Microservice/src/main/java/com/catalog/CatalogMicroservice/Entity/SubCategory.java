package com.catalog.CatalogMicroservice.Entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;


@Entity
@Table(name = "sub-categorie")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SubCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@Column(unique = true)
    private String name;
    // @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    @OneToMany(mappedBy = "subCategory",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Product> products;


}
