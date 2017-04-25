package android.novica.shoppinglist.activity;

import android.content.Intent;
import android.nfc.Tag;
import android.novica.shoppinglist.R;
import android.novica.shoppinglist.adapters.ShoppingListAdapter;
import android.novica.shoppinglist.constants.TagsConstants;
import android.novica.shoppinglist.model.Article;
import android.novica.shoppinglist.model.ShoppingList;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class LauncherActivity extends AppCompatActivity {

    FloatingActionButton btnGoToShopping;
    ListView listView;
    Button deleteAll;
    private ShoppingList clickedList;

    public ShoppingListAdapter getAdapter() {
        return adapter;
    }

    private ShoppingListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        getViews();

        createSomeShoppingLists();
        goToShopping();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view,
                                    int position, long id) {
                clickedList = (ShoppingList) listView.getItemAtPosition(position);
                if(clickedList.isLocked()){
                    Toast.makeText(LauncherActivity.this, "List is protected with a password.", Toast.LENGTH_SHORT).show();
                }else{
                    if(!clickedList.isActive()){ //not active go to review
                        Intent intent = new Intent(LauncherActivity.this, ReviewListActivity.class);
                        intent.putExtra(TagsConstants.NEW_LIST_SO, clickedList);
                        startActivity(intent);
                    }else{ //active, continue to shopping
                        clickedList = (ShoppingList) listView.getItemAtPosition(position);
                        Intent intent = new Intent(LauncherActivity.this, ShoppingActivity.class);
                        intent.putExtra(TagsConstants.USED_LIST_SO, clickedList);
                        startActivityForResult(intent, TagsConstants.USED_LIST_REQUEST);
                    }
                }
            }
        });

        deleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.clear();
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //check which request we responding to
        //and if result is ok
        if(requestCode == TagsConstants.NEW_LIST_REQVEST && resultCode == RESULT_OK){
            ShoppingList list = (ShoppingList) data.getSerializableExtra(TagsConstants.NEW_LIST_SO);
            adapter.add(list);
        }else if(requestCode == TagsConstants.USED_LIST_REQUEST  && resultCode == RESULT_OK){
            //replace active and new completed list
            ShoppingList usedList = (ShoppingList) data.getSerializableExtra(TagsConstants.USED_LIST_SO);
            adapter.remove(clickedList);
            adapter.add(usedList);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    private void goToShopping() {
        btnGoToShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LauncherActivity.this, ShoppingActivity.class);
                startActivityForResult(i, TagsConstants.NEW_LIST_REQVEST);
            }
        });
    }

    private void createSomeShoppingLists() {
        List<Article> articles = new ArrayList<Article>();
        articles.add( new Article("a",11.2,"1"));
        ShoppingList l4 = new ShoppingList("Test 1", true, articles);
        ShoppingList l6 = new ShoppingList("Test 2", false, new ArrayList<Article>());

        //set base adapter to list
        adapter = new ShoppingListAdapter(this);
        adapter.add(l4);
        adapter.add(l6);
        listView.setAdapter(adapter);
    }

    private void getViews() {
        deleteAll = (Button) findViewById(R.id.btnDelete);
        listView = (ListView) findViewById(R.id.lvShoppingLists);
        btnGoToShopping = (FloatingActionButton) findViewById(R.id.btnGoToShop);
    }
}
