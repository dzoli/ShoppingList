package android.novica.shoppinglist.model;

import java.io.Serializable;

/**
 * Created by Novica on 4/21/2017.
 */

public class Article implements Serializable{
    private String name;
    private Double cost;
    private Boolean isBought;
    private String photoCode;


    public Article() {

    }

    public Article(String name, Double cost, String photoCode) {
        this.isBought = false;
        this.name = name;
        this.cost = cost;
        this.photoCode = photoCode;
    }

    public Boolean isBought() {
        return isBought;
    }

    public void setBought(Boolean bought) {
        isBought = bought;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public String getPhotoCode() {
        return photoCode;
    }

    public void setPhotoCode(String photoCode) {
        this.photoCode = photoCode;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Article))
            return false;
        if (obj == this)
            return true;
        Article a = (Article) obj;
        return this.name.equals(a.name);
    }
}
