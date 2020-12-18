package com.project.eportfolio.adapter.adapterPortfolio;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.project.eportfolio.R;
import com.project.eportfolio.model.portfolio.TrPortofolio;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterSliderPortfolio extends PagerAdapter {

    private List<TrPortofolio> models;
    private LayoutInflater layoutInflater;
    private Context context;

    public AdapterSliderPortfolio(List<TrPortofolio> models, Context context) {
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
        View view = layoutInflater.inflate(R.layout.v_slider_portfolio, container, false);
        ImageView imageView;
        TextView textView;
        imageView = view.findViewById(R.id.slider_oase_img);
        textView = view.findViewById(R.id.slider_oase_title);

        try{
            Picasso.get().load(models.get(position).getFoto().toString()).into(imageView);
        } catch (Exception e){
            e.printStackTrace();
        }

        textView.setText(models.get(position).getJudulKd());

        container.addView(view, 0);
        return view;
    }

    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
