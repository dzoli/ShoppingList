package android.novica.shoppinglist.model;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Novica on 4/21/2017.
 */

public class ShoppingList implements Serializable, Comparable<ShoppingList>{
    private String shoppingListName;
    private Boolean isActive = false;
    private List<Article> articles;
    private String password = null;

    public ShoppingList() {
        articles = new ArrayList<>();

    }

    public ShoppingList(String nameOfList, Boolean active, List<Article> boughtArticles) {
        this.shoppingListName = nameOfList;
        this.isActive = active;
        this.articles = boughtArticles;
    }


    public String getShoppingListName() {
        return shoppingListName;
    }

    public void setShoppingListName(String shoppingListName) {
        this.shoppingListName = shoppingListName;
    }

    public Boolean isActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public void addNewArticle(Article article){
        articles.add(article);
    }

    public Integer numberOfArticles(){
        return articles.size();
    }

    @Override
    public int compareTo(@NonNull ShoppingList shopingList) {
        return isActive ? -1 : 1;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isLocked(){
        return password != null;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ShoppingList))
            return false;
        if (obj == this)
            return true;
        ShoppingList a = (ShoppingList) obj;
        return this.shoppingListName.equals(a.shoppingListName);
    }

}
