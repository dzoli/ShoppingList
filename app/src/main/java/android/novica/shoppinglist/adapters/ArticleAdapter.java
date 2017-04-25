package android.novica.shoppinglist.adapters;

import android.content.Context;
import android.net.Uri;
import android.novica.shoppinglist.R;
import android.novica.shoppinglist.constants.PathConstants;
import android.novica.shoppinglist.model.Article;
import android.novica.shoppinglist.model.ShoppingList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Novica on 4/23/2017.
 */

public class ArticleAdapter extends BaseAdapter {

    private List<Article> data = new ArrayList<>();
    private Context context;


    public ArticleAdapter() {
    }

    public ArticleAdapter(Context context) {
        this.context = context;
    }

    public ArticleAdapter(List<Article> data, Context context) {
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

    @Override
    public View getView(final int position, View convertView, ViewGroup parentView) {
        final ViewHolder viewHolder;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.row_article, parentView, false);
            viewHolder = new ArticleAdapter.ViewHolder(convertView);
            convertView.setTag(viewHolder);

        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final Article currArticle = (Article) getItem(position);
        viewHolder.tvNameArticle.setText(getResourceForID(currArticle.getPhotoCode()));
        viewHolder.tvCostArticle.setText("Cost: $" + currArticle.getCost() + " M");
        viewHolder.cbIsBought.setChecked(currArticle.isBought());

        Picasso.with(context)
                .load(getImagePath(currArticle.getPhotoCode()))
                .into(viewHolder.ivPhotoArticle);

        viewHolder.cbIsBought.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                final Article currArt = data.get(position);

                currArt.setBought(viewHolder.cbIsBought.isChecked());
                data.set(position, currArt);
            }
        });

        return convertView;
    }
    private class ViewHolder {
        TextView tvNameArticle;
        TextView tvCostArticle;
        CheckBox cbIsBought;
        ImageView ivPhotoArticle;

        public ViewHolder(View view) {
            tvNameArticle = (TextView) view.findViewById(R.id.tvNameArticle);
            tvCostArticle = (TextView) view.findViewById(R.id.tvCostOfArticle);
            cbIsBought = (CheckBox) view.findViewById(R.id.cbIsBoughtArticle);
            ivPhotoArticle = (ImageView) view.findViewById(R.id.ivArticlePhoto);
        }
    }


    private String getResourceForID(String photoCode){
        Field resourceField = null;
        try {
            resourceField =R.string.class.getDeclaredField("article_" + photoCode);
            int resourceId = resourceField.getInt(resourceField);
            String resourceText = context.getString(resourceId);
            return resourceText;
        } catch (Exception e) {
            e.printStackTrace();
            return "No such resource";
        }
    }

    int getImgID(String photoCode){
        return context.getResources().getIdentifier("img_"+photoCode, "drawable", context.getPackageName());
    }

    String getImagePath(String photoCode){
        return Uri.parse(PathConstants.ROOT_PACKAGE_NAME + getImgID(photoCode)).toString();
    }


    //data operations
    public void add(Article article){
        data.add(article);
        notifyDataSetChanged();
    }

    public void clear(){
        data.clear();
        notifyDataSetChanged();
    }

    public void addData(List<Article> articles){
        this.data = articles;
        notifyDataSetChanged();
    }

    public List<Article> getData(){
        return data;
    }

    public void setNewArticle(Article art){
       for(Article a : data){
           if(a.equals(art)){
               data.remove(a);
               data.add(art);
           }
       }
       notifyDataSetChanged();
    }

}
