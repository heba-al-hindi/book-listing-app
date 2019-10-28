package com.example.booklistingretrofit.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.booklistingretrofit.R;
import com.google.android.material.appbar.CollapsingToolbarLayout;

public class BookDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);




        Bundle extras = getIntent().getExtras();
        String thumbnail = "";
        String title = "";
        String categories = "";
        String rating = "";
        String authors = "";
        String price = "";
        String buy = "";
        String preview = "";
        String description = "";

        if (extras != null) {
            thumbnail = extras.getString("book_thumbnail");
            title = extras.getString("book_title");
            categories = extras.getString("book_category");
            rating = extras.getString("book_rating");
            authors = extras.getString("book_author");
            price = extras.getString("book_price");
            buy = extras.getString("book_buy");
            preview = extras.getString("book_preview");
            description = extras.getString("book_description");


        }

        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitleEnabled(true);
        collapsingToolbarLayout.setTitle(title);


        ImageView ivThumbnail = findViewById(R.id.detail_book_image);
        TextView tvTitle = findViewById(R.id.detail_book_title);
        TextView tvCategory = findViewById(R.id.detail_book_categories);
        RatingBar Rating = findViewById(R.id.detail_book_rating);
        TextView tvAuthors = findViewById(R.id.detail_book_authors);
        TextView tvPrice = findViewById(R.id.book_price);
        final TextView tvBuy = findViewById(R.id.buy_book);
        TextView tvPreview = findViewById(R.id.preview_book);
        TextView tvDescription = findViewById(R.id.book_description);


        Glide.with(this).load(thumbnail).into(ivThumbnail);
        tvTitle.setText(title);
        tvAuthors.setText(authors);
        tvDescription.setText(description);
        tvCategory.setText(categories);
        tvPrice.setText(price);
        Rating.setRating(Float.parseFloat(rating));

        if (price.equals("Free")) {
            tvBuy.setVisibility(View.INVISIBLE);

        } else {
            tvBuy.setVisibility(View.VISIBLE);
        }

        final String finalBuy = buy;
        tvBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(finalBuy));
                startActivity(i);
            }

        });


        final String finalPreview = preview;
        tvPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!finalPreview.isEmpty() || !finalPreview.equals(" ")) {
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(finalPreview));
                    startActivity(i);
                } else {
                    Toast.makeText(BookDetails.this, "PreviewLink Not Available", Toast.LENGTH_LONG).show();
                }

            }
        });


    }

}
