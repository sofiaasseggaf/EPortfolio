package com.project.eportfolio.student.portfoliofragment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.project.eportfolio.R;
import com.project.eportfolio.model.portfolio.TrPortofolio;
import com.project.eportfolio.utility.Constants;

public class PortfolioDetail extends AppCompatActivity {

//    private ImageView mPhoto;
//    private TextView mName, mId, mCategory, mInstruction, mPrice;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.portfoliodetail);

        Intent intent = getIntent();

        TrPortofolio portofolio = (TrPortofolio) intent.getSerializableExtra(Constants.REFERENCE.PORTFOLIO);

        configViews();

        /*mId.setText(String.format("%d", portofolio.getProductId()));
        mName.setText(portofolio.getName());
        mCategory.setText(portofolio.getCategory());
        mInstruction.setText(portofolio.getInstructions());
        mPrice.setText(String.format("$%.2f", portofolio.getPrice()));

        if (flower.isFromDatabase()) {
            mPhoto.setImageBitmap(flower.getPicture());
        } else {
            Picasso.with(getApplicationContext()).load(Constants.HTTP.BASE_URL + "/photos/" + flower.getPhoto()).into(mPhoto);
        }*/
    }

    private void configViews() {
        /*mPhoto = (ImageView) findViewById(R.id.flowerPhoto);
        mName = (TextView) findViewById(R.id.flowerName);
        mId = (TextView) findViewById(R.id.flowerId);
        mCategory = (TextView) findViewById(R.id.flowerCategory);
        mInstruction = (TextView) findViewById(R.id.flowerInstruction);
        mPrice = (TextView) findViewById(R.id.flowerPrice);*/

    }

}
