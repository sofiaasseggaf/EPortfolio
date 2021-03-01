package com.project.eportfolio.adapter.adapterArtikel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.project.eportfolio.R;
import com.project.eportfolio.model.blog.Blog;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterSliderArtikel extends PagerAdapter {

    private List<Blog> models;
    private LayoutInflater layoutInflater;
    private Context context;

    public AdapterSliderArtikel(List<Blog> models, Context context) {
        this.models = models;
        this.context = context;
    }

    @Override
    public int getCount() {
        return models.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.v_slider_artikel, container, false);
        ImageView imageView;
        TextView textView;
        imageView = view.findViewById(R.id.slider_artikel_img);
        textView = view.findViewById(R.id.slider_artikel_title);

        try{
            Picasso.get().load("https://eportofolio.id/uploads/blog/"+models.get(position).getImage()).into(imageView);
        } catch (Exception e){
            e.printStackTrace();
        }

        textView.setText(models.get(position).getTitle());

        container.addView(view, 0);
        return view;
    }

    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
