package com.example.booklistingretrofit.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.booklistingretrofit.R;
import com.example.booklistingretrofit.activities.BookDetails;
import com.example.booklistingretrofit.model.Book;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    List<Book> books;
    Context context;

    public RecyclerViewAdapter(List<Book> books, Context context) {
        this.books = books;
        this.context = context;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        view = inflater.inflate(R.layout.book_item, parent, false);
        final MyViewHolder viewHolder = new MyViewHolder(view);

        viewHolder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, BookDetails.class);
                int index = viewHolder.getAdapterPosition();
                i.putExtra("book_thumbnail", books.get(index).getmThumbnail());
                i.putExtra("book_title", books.get(index).getmTitle());
                i.putExtra("book_category", books.get(index).getmCategory());
                i.putExtra("book_rating", books.get(index).getmRating());
                i.putExtra("book_author", books.get(index).getmAuthors());
                i.putExtra("book_price", books.get(index).getmPrice());
                i.putExtra("book_buy", books.get(index).getmBuyLink());
                i.putExtra("book_preview", books.get(index).getmPreviewLink());
                i.putExtra("book_description", books.get(index).getmDescription());
                context.startActivity(i);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Book book = books.get(position);

        holder.tvTitle.setText(book.getmTitle());
        holder.tvCategories.setText(book.getmCategory());
        try {
            holder.ratingBar.setRating(Float.parseFloat(book.getmRating()));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        holder.tvAuthors.setText(book.getmAuthors());

        String imgProtocol = book.getmThumbnail().replace("http", "https");
        Glide.with(context)
                .load(imgProtocol)
                .placeholder(R.drawable.placeholder_image)
                .into(holder.imgThumbnail);

    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle, tvCategories, tvAuthors;
        ImageView imgThumbnail;
        RatingBar ratingBar;
        LinearLayout container;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.book_title);
            tvCategories = itemView.findViewById(R.id.book_categories);
            tvAuthors = itemView.findViewById(R.id.book_authors);
            imgThumbnail = itemView.findViewById(R.id.book_image);
            ratingBar = itemView.findViewById(R.id.book_rating);
            container = itemView.findViewById(R.id.container);


        }
    }
}
