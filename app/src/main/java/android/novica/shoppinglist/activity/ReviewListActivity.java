package android.novica.shoppinglist.activity;

import android.novica.shoppinglist.adapters.ReviewCompletedListAdapter;
import android.novica.shoppinglist.constants.TagsConstants;
import android.novica.shoppinglist.model.ShoppingList;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.novica.shoppinglist.R;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ReviewListActivity extends AppCompatActivity {

    ListView purchasedArticles;
    ReviewCompletedListAdapter adapter;
    TextView nameList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_list);

        getViews();
        ShoppingList clickedList = getListFromLauncher(savedInstanceState);
        adapter = new ReviewCompletedListAdapter(this);//set list of articles of that list
        adapter.addData(clickedList.getArticles());
        purchasedArticles.setAdapter(adapter);
        nameList.setText(clickedList.getShoppingListName());

    }



    public void getViews() {
        purchasedArticles = (ListView) findViewById(R.id.purchasedArticles);
        nameList = (TextView) findViewById(R.id.tvListName);
    }



    public ShoppingList getListFromLauncher(Bundle savedInstanceState) {
        Bundle extras = getIntent().getExtras();
        if(!extras.isEmpty()){
            return (ShoppingList) getIntent().getSerializableExtra(TagsConstants.NEW_LIST_SO);
        }
        return null;
    }
}
