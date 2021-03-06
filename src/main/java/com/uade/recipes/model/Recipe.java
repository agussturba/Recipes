package com.uade.recipes.model;

import com.uade.recipes.vo.RecipeVo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@SecondaryTable(name = "recipe_addition", pkJoinColumns = @PrimaryKeyJoinColumn(name = "recipe_id"))
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_id", nullable = false)
    private Integer id;
    private String name;
    private String description;
    @Column(table = "recipe_addition")
    private Integer duration;
    private String mainPhoto;
    @OneToOne
    private User owner;
    private Integer peopleAmount;
    @Column(table = "recipe_addition")
    private boolean enabled;
    @Column(table = "recipe_addition")
    private LocalDateTime timestamp;

    private Double portions;
    @OneToOne
    private Type type;

    public RecipeVo toVO() {
        RecipeVo vo = new RecipeVo();
        vo.setId(id);
        vo.setName(name);
        vo.setDescription(description);
        vo.setOwnerId(owner.getId());
        vo.setPeopleAmount(peopleAmount);
        vo.setMainPhoto(mainPhoto);
        vo.setDuration(duration);
        vo.setOwnerUserName(owner.getUserName());
        vo.setPortions(portions);
        vo.setTypeName(type.getDescription());
        vo.setTypeId(type.getId());
        vo.setTimestamp(timestamp);
        vo.setEnabled(enabled);
        return vo;
    }

    public Integer getOwnerId() {
        return owner.getId();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Recipe recipe = (Recipe) o;
        return id != null && Objects.equals(id, recipe.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
