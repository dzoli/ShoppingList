package android.novica.shoppinglist.adapters;

import android.content.Context;
import android.novica.shoppinglist.R;
import android.novica.shoppinglist.constants.PathConstants;
import android.novica.shoppinglist.model.ShoppingList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Novica on 4/22/2017.
 */

public class ShoppingListAdapter extends BaseAdapter {
    private List<ShoppingList> data = new ArrayList<>();
    private Context context;

    public ShoppingListAdapter() {
    }

    public ShoppingListAdapter(Context context) {
        this.context = context;
    }

    public ShoppingListAdapter(List<ShoppingList> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    //Get a View that displays the data at the specified position in the data set.
    //convertView  =  The old view to reuse, if possible
    @Override
    public View getView(final int position, View convertView, ViewGroup parentView) {
        //Check if the convertView is null, if it is null it probably means that this
        // is the first time the view has been displayed
        ViewHolder viewHolder;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.row_item, parentView, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
            ShoppingList currentList = (ShoppingList) getItem(position);
            viewHolder.itemName.setText(currentList.getShoppingListName());
            viewHolder.isItemActive.setText(currentList.isActive() ? "List is currently active." : "The list is not active.");
            viewHolder.purchasedArticles.setText("Purchased items: " + currentList.numberOfArticles());

            if(currentList.isActive()){
                Picasso.with(context)
                    .load(PathConstants.ACTIVE_BASKET_PHOTO_PATH)
                    .into(viewHolder.listPhotoView);
            }else{
                Picasso.with(context)
                        .load(PathConstants.COMPLETE_BASKET_PHOTO_PATH)
                        .into(viewHolder.listPhotoView);
            }

        return convertView;
    }

    private class ViewHolder{
        TextView itemName;
        TextView purchasedArticles;
        TextView isItemActive;
        ImageView listPhotoView;

        public ViewHolder(View view){
            itemName = (TextView) view.findViewById(R.id.tvNameList);
            purchasedArticles = (TextView) view.findViewById(R.id.tvPurchasedArticles);
            isItemActive = (TextView) view.findViewById(R.id.tvIsListActive);
            listPhotoView = (ImageView) view.findViewById(R.id.ivListPhoto);
        }
    }

    //data operations
    public void add(ShoppingList list){
        data.add(list);
        Collections.sort(data);
        notifyDataSetChanged();
    }

    public void update(ShoppingList usedList){

    }

    public void remove(ShoppingList art){
        data.remove(art);
        notifyDataSetChanged();
    }

    public void clear(){
        data.clear();
        notifyDataSetChanged();
    }

    public void addData(List<ShoppingList> lists){
        this.data = lists;
        notifyDataSetChanged();
    }

    public List<ShoppingList> getData(){
        return data;
    }

}
