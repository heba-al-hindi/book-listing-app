package com.example.booklistingretrofit.activities;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.booklistingretrofit.R;
import com.example.booklistingretrofit.adapters.RecyclerViewAdapter;
import com.example.booklistingretrofit.model.Book;
import com.example.booklistingretrofit.utilits.BookQuery;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Search extends AppCompatActivity {


    private final String REQUEST = "https://www.googleapis.com/books/v1/volumes?q=";
    private final String API_KEY = "&key=AIzaSyAPJrK1ooKnx15xJbjj6t-40BB-Xp_j_ks";
    String finalQuery;
    String finalRequest;
    MaterialSearchView searchView;
    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;
    TextView error_message;
    Uri baseUri;
    Uri finalUri;
    Uri.Builder builder;
    BookQuery service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);

        searchView = findViewById(R.id.search_view);
        recyclerView = findViewById(R.id.book_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        error_message = findViewById(R.id.tv_error_message);
        builder = new Uri.Builder();

        String baseUrl = "https://www.googleapis.com/books/v1/";
        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build();
        service = retrofit.create(BookQuery.class);


        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                search(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_item, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return true;

    }

    private boolean readNetworkState(Context context) {

        boolean is_connected;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        is_connected = info != null && info.isConnectedOrConnecting();

        return is_connected;

    }

    private void search(String query) {

        String search_query = query;

        boolean is_connected = readNetworkState(this);
        if (!is_connected) {
            error_message.setText(R.string.internet_connection);
            recyclerView.setVisibility(View.INVISIBLE);
            error_message.setVisibility(View.VISIBLE);
            return;
        }


        if (search_query=="  " || search_query.isEmpty()) {
            Toast.makeText(Search.this, "Please enter your book", Toast.LENGTH_LONG).show();

        }

        finalQuery = query.replace(" ", "+");
        finalQuery += API_KEY;
        baseUri = Uri.parse(REQUEST);
        finalUri = Uri.parse(baseUri + finalQuery);
        builder = finalUri.buildUpon();
        finalRequest = builder.toString();

        System.out.println("Final Request  " + finalRequest);
        getBooksQuery();

    }

    private void getBooksQuery() {


        Call<JsonObject> call = service.getBooks(finalRequest);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(Search.this, "code :" + response.code(), Toast.LENGTH_LONG).show();

                }

                JsonObject root = response.body();

                if (root.get("totalItems").getAsString() != "0") {
                    List<Book> books;
                    books = jsonParse(root);
                    if (books != null && !books.isEmpty()) {
                        recyclerView.setVisibility(View.VISIBLE);
                        recyclerViewAdapter = new RecyclerViewAdapter(books, Search.this);
                        recyclerView.setAdapter(recyclerViewAdapter);
                    } else {
                        recyclerView.setVisibility(View.INVISIBLE);
                        error_message.setText(R.string.matching_result);
                        error_message.setVisibility(View.VISIBLE);
                    }

                } else {
                    recyclerView.setVisibility(View.INVISIBLE);
                    error_message.setText(R.string.matching_result);
                    error_message.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                recyclerView.setVisibility(View.INVISIBLE);
                Toast.makeText(Search.this, t.getMessage(), Toast.LENGTH_LONG).show();


            }
        });
    }

    private List<Book> jsonParse(JsonObject root) {

        List<Book> bookData = new ArrayList<>();

        JsonObject item;
        JsonObject volumeInfo;
        JsonObject imageLinks;
        JsonObject saleInfo;
        JsonObject listPrice;

        JsonArray itemArray;
        JsonArray authorsArray;
        JsonArray categoryArray;

        String title;
        String authors;
        String category;
        String smallThumbnail;
        String avgRating;
        String price;
        String description;
        String buyLink;
        String previewLink;

        itemArray = root.getAsJsonArray("items");
        for (int i = 0; i < itemArray.size(); i++) {

            JsonElement element_item = itemArray.get(i);
            item = element_item.getAsJsonObject();

            volumeInfo = item.getAsJsonObject("volumeInfo");
            JsonElement element_title = volumeInfo.get("title");
            title = element_title.getAsString();


            authors = "";

            if (volumeInfo.has("authors") && !volumeInfo.get("authors").isJsonNull()) {
                authorsArray = volumeInfo.getAsJsonArray("authors");

                if (authorsArray.size() > 1) {
                    for (int j = 0; j < authorsArray.size(); j++) {

                        JsonElement element_author = authorsArray.get(j);
                        authors += element_author.getAsString() + "\n";
                    }
                } else {
                    JsonElement element_author = authorsArray.get(0);
                    authors += element_author.getAsString();
                }
            } else {
                authors = "Unknown";
            }

            category = "";

            if (volumeInfo.has("categories") && !volumeInfo.get("categories").isJsonNull()) {
                categoryArray = volumeInfo.getAsJsonArray("categories");

                if (categoryArray.size() > 1) {
                    for (int j = 0; j < categoryArray.size(); j++) {
                        JsonElement element_category = categoryArray.get(j);
                        category += element_category.getAsString() + "|";


                    }
                } else {
                    JsonElement element_category = categoryArray.get(0);
                    category += element_category.getAsString();
                }
            } else {
                category = "No Category";
            }

            if (volumeInfo.has("averageRating") && !volumeInfo.get("averageRating").isJsonNull()) {
                JsonElement element_rating = volumeInfo.get("averageRating");
                avgRating = element_rating.getAsString();

            } else {
                avgRating = "0";
            }

            if (volumeInfo.has("imageLinks") && !volumeInfo.get("imageLinks").isJsonNull()) {
                imageLinks = volumeInfo.getAsJsonObject("imageLinks");
                JsonElement element_smallThumbnail = imageLinks.get("smallThumbnail");
                smallThumbnail = element_smallThumbnail.getAsString();
            } else {
                smallThumbnail = "https://www.sylvansport.com/wp/wp-content/uploads/2018/11/image-placeholder-1200x800.jpg";
            }

            if (volumeInfo.has("description") && !volumeInfo.get("description").isJsonNull()) {
                JsonElement element_description = volumeInfo.get("description");
                description = element_description.getAsString();

            } else {
                description = "No description for this Book!";
            }

            if (volumeInfo.has("previewLink") && !volumeInfo.get("previewLink").isJsonNull()) {
                JsonElement element_preview = volumeInfo.get("previewLink");
                previewLink = element_preview.getAsString();

            } else {
                previewLink = " ";
            }

            saleInfo = item.getAsJsonObject("saleInfo");

            price = " ";
            buyLink = " ";
            if (saleInfo.has("saleability") && !saleInfo.get("saleability").isJsonNull()) {
                JsonElement element_saleability = saleInfo.get("saleability");
                if (element_saleability.getAsString().equals("FOR_SALE")) {
                    if (saleInfo.has("listPrice")) {
                        listPrice = saleInfo.getAsJsonObject("listPrice");
                        JsonElement element_price = listPrice.get("amount");
                        price = Double.toString(element_price.getAsDouble());
                    }
                    if (saleInfo.has("currencyCode")) {
                        JsonElement element_currencyCode = saleInfo.get("currencyCode");
                        price += element_currencyCode.getAsString();
                    }
                    if(saleInfo.has("buyLink")) {
                        JsonElement element_buyLink = saleInfo.get("buyLink");
                        buyLink = element_buyLink.getAsString();
                    }

                } else {

                    price = "Free";
                    buyLink = " ";
                }

            }

            bookData.add(new Book(smallThumbnail, title, category, avgRating, authors, price, description, buyLink, previewLink));

        }
        return bookData;
    }


}
