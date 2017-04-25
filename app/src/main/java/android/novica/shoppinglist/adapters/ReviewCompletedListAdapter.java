package android.novica.shoppinglist.adapters;

import android.content.Context;
import android.net.Uri;
import android.novica.shoppinglist.R;
import android.novica.shoppinglist.constants.PathConstants;
import android.novica.shoppinglist.model.Article;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Novica on 4/25/2017.
 */

public class ReviewCompletedListAdapter extends BaseAdapter {

    private Context context;
    private List<Article> data = new ArrayList<>();

    public ReviewCompletedListAdapter(Context context) {
        this.context = context;
    }

    public ReviewCompletedListAdapter(Context context, List<Article> data) {
        this.context = context;
        this.data = data;
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
        final ReviewCompletedListAdapter.ViewHolder viewHolder;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.row_purchased_article, parentView, false);
            viewHolder = new ReviewCompletedListAdapter.ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ReviewCompletedListAdapter.ViewHolder) convertView.getTag();
        }
        final Article currArticle = (Article) getItem(position);
        viewHolder.tvNameArticle.setText(getResourceForID(currArticle.getPhotoCode()));
        viewHolder.tvCostArticle.setText("Cost: $" + currArticle.getCost() + " M");

        Picasso.with(context)
                .load(getImagePath(currArticle.getPhotoCode()))
                .into(viewHolder.ivPhotoArticle);

        return convertView;
    }
    private class ViewHolder {
        TextView tvNameArticle;
        TextView tvCostArticle;
        ImageView ivPhotoArticle;

        public ViewHolder(View view) {
            tvNameArticle = (TextView) view.findViewById(R.id.tvNameArticle);
            tvCostArticle = (TextView) view.findViewById(R.id.tvCostOfArticle);
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
}
