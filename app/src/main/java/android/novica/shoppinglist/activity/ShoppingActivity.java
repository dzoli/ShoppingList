package android.novica.shoppinglist.activity;

import android.app.Activity;
import android.content.Intent;
import android.novica.shoppinglist.adapters.ArticleAdapter;
import android.novica.shoppinglist.constants.TagsConstants;
import android.novica.shoppinglist.model.Article;
import android.novica.shoppinglist.model.ShoppingList;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.novica.shoppinglist.R;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ShoppingActivity extends AppCompatActivity {

    ListView articlesList;
    ArticleAdapter articlesAdapter;
    FloatingActionButton btnDone;   //save list
    EditText etNameList;
    private List<Article> checkedArticles = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);

        getViews();
        createSomeArticles();

        btnDone.setOnClickListener(new View.OnClickListener() { //enter to activity from btn
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShoppingActivity.this, LauncherActivity.class);
                ShoppingList newShoppingList = new ShoppingList();
                getCheckedArticles();
                if(etNameList.getText().toString().isEmpty()){
                    Toast.makeText(ShoppingActivity.this, "Add name to list...", Toast.LENGTH_SHORT).show();
                }else{
                    newShoppingList.setArticles(checkedArticles);
                    newShoppingList.setShoppingListName(etNameList.getText().toString());
                    if(newShoppingList.isActive()){
                        //enter from item onClick, but terminating the session
                        ShoppingList usedShoppingList = new ShoppingList();
                        usedShoppingList.setIsActive(false);
                        usedShoppingList.setArticles(checkedArticles);
                        usedShoppingList.setShoppingListName(etNameList.getText().toString());
                        intent.putExtra(TagsConstants.USED_LIST_SO, newShoppingList);
                        setResult(Activity.RESULT_OK, intent);
                        finish();
                    }
                    newShoppingList.setIsActive(false);
                    intent.putExtra(TagsConstants.NEW_LIST_SO, newShoppingList);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
            }
        });

        //enter to activity from active list item
        Bundle extras = getIntent().getExtras();
        if(!(extras == null)){
            ShoppingList list = (ShoppingList) getIntent().getSerializableExtra(TagsConstants.NEW_LIST_SO);
            for(Article adapterArt : articlesAdapter.getData()){
                for(Article listArt : list.getArticles()){
                    if(listArt.equals(adapterArt)){
                        articlesAdapter.setNewArticle(listArt);
                    }
                }
            }
            etNameList.setText(list.getShoppingListName());
        }
    }

    private void getCheckedArticles() {
        checkedArticles = new ArrayList<>();
        for (Article a : articlesAdapter.getData()){
          if (a.isBought()){
              checkedArticles.add(a);
          }
        }
    }

    @Override
    public void onBackPressed() { //list stay active
            Intent intent = new Intent(ShoppingActivity.this, LauncherActivity.class);
            ShoppingList newShoppingList = new ShoppingList();
            getCheckedArticles();
            newShoppingList.setIsActive(true);
            newShoppingList.setArticles(checkedArticles);
            newShoppingList.setShoppingListName(etNameList.getText().toString());
            intent.putExtra(TagsConstants.NEW_LIST_SO, newShoppingList);
            setResult(Activity.RESULT_OK, intent);
            super.onBackPressed();
            finish();
    }

    private void createSomeArticles() {
        articlesAdapter = new ArticleAdapter(this);
        for(int i = 1; i < 20; i++){
            Article a = new Article("n1",randomGenerator(),String.valueOf(i));
            articlesAdapter.add(a);
        }

        articlesList.setAdapter(articlesAdapter);
    }

    private Double randomGenerator(){
        Double rnd = new Random().nextDouble() * 100 + 20;
        DecimalFormat decim = new DecimalFormat("#.##");
        Double rndF = Double.parseDouble(decim.format(rnd));
        return rndF;
    }


    private void getViews() {
        btnDone = (FloatingActionButton) findViewById(R.id.floatingBtnDone);
        articlesList = (ListView) findViewById(R.id.lvArticles);
        etNameList = (EditText) findViewById(R.id.etListName);
    }
}
