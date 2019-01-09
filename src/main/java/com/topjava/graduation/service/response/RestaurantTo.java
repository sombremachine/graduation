package com.topjava.graduation.service.response;

import com.topjava.graduation.data.MenuItem;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

public class RestaurantTo implements Serializable {

    private Integer id;

    @NotBlank
    @Size(min = 2, max = 100)
    private String name;

    private Integer numVotes;

    private List<MenuItem> menu;

    public RestaurantTo() {
    }

    public RestaurantTo(Integer id, @NotBlank @Size(min = 2, max = 100) String name, Integer numVotes, List<MenuItem> menu) {
        this.id = id;
        this.name = name;
        this.numVotes = numVotes;
        this.menu = menu;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumVotes() {
        return numVotes;
    }

    public void setNumVotes(Integer numVotes) {
        this.numVotes = numVotes;
    }

    public List<MenuItem> getMenu() {
        return menu;
    }

    public void setMenu(List<MenuItem> menu) {
        this.menu = menu;
    }
}
