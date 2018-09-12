package com.demo.app.assignment.views.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.demo.app.assignment.R;
import com.demo.app.assignment.model.News;
import java.util.List;


public class HomeAdapter extends BaseAdapter
{
    Context mContext;
    List<News.RowsItem> mList;
    private LayoutInflater mInflater;

    public HomeAdapter(Context mContext, List<News.RowsItem> mList) {
        this.mContext = mContext;
        this.mList = mList;
        this.mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View v = convertView;
        ViewHolder holder = null;

        if (v == null) {
            v = mInflater.inflate(R.layout.list_row_item_home_fragment, parent, false);
            holder = new ViewHolder();
            holder.txtTitle =(TextView)v.findViewById(R.id.title);
            holder.txtDescription =(TextView)v.findViewById(R.id.description);
            holder.mImage =(ImageView) v.findViewById(R.id.img);
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }

        holder.txtTitle.setText( mList.get(position).getTitle());
        holder.txtDescription.setText(mList.get(position).getDescription());
        Glide.with(mContext).load(mList.get(position).getImageHref())
                .thumbnail(0.5f).into(holder.mImage)
                .onLoadStarted(mContext.getResources().getDrawable(R.mipmap.ic_launcher));

//        ImageLoader imageLoader = ImageLoader.getInstance();
  //      String image = mList.get(position).getImageHref().toString();
    //    imageLoader.displayImage(image,holder.mImage);
        return v;
    }
    private class ViewHolder {
        TextView txtTitle,txtDescription;
        ImageView mImage;

    }

    }

