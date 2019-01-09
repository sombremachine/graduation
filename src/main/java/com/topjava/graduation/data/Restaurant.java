package com.topjava.graduation.data;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "restaurants", uniqueConstraints = {@UniqueConstraint(columnNames = "name", name = "restaurants_unique_name_idx")})
public class Restaurant extends AbstractBaseEntity{
    public Restaurant() {
    }

    public Restaurant(Integer id, String name) {
        super(id, name);
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    @Where(clause = "CAST(date_time AS DATE) = CAST(now() AS DATE)") //только сегодняшнее
    private List<Vote> votes;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    @JsonManagedReference
    @Where(clause = "CAST(date_time AS DATE) = CAST(now() AS DATE)") //только сегодняшнее
    private List<MenuItem> menu;

    public List<Vote> getVotes() {
        return votes;
    }

    public void setVotes(List<Vote> votes) {
        this.votes = votes;
    }

    public List<MenuItem> getMenu() {
        return menu;
    }

    public void setMenu(List<MenuItem> menu) {
        this.menu = menu;
    }
}
