package com.example.comsci;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class BookActivity extends AppCompatActivity {
    private TextView txtBookName, textAuthor, txtPages, text5, txtLink;
    private Button btnAddToWantRead, btn, btnAddToCurrentlyReading, btnAddToFavorites;
    private ImageView bookImage;
    public static final String Book_ID_KEY = "bookID";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        initViews();

        /*
Checks if there is a incoming book to pass to the Handle methods

 */


        Intent intent = getIntent();
        if (null != intent) {
            int bookId = intent.getIntExtra(Book_ID_KEY, -1);
            if (bookId != -1) {
                Book incomingBook = Utils.getInstance(this).getBookById(bookId);
                if ( null != incomingBook) {
                    setData(incomingBook);

                    handleAlreadyRead(incomingBook);
                    HandleCurrentlyReadingBooks(incomingBook);
                    HandleFavoriteBooks(incomingBook);

                }

            }
        }
    }

            /*
Logic to add books to other activitys.

 */


    private  void  HandleFavoriteBooks(final Book book) {

        ArrayList<Book> favoriteToRead = Utils.getInstance(this).getFavoriteBooks();
        boolean existFavoriteBooks = false;

        for (Book b : favoriteToRead) {
            if (b.getId() == book.getId()) {
                existFavoriteBooks = true;

            }
        }
        if (existFavoriteBooks) {
            btnAddToFavorites.setEnabled(false);
        } else {
            btnAddToFavorites.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (Utils.getInstance(BookActivity.this).addToFavorite(book)) {
                        Toast.makeText(BookActivity.this, "Book Added", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(BookActivity.this, FavoriteActivity.class);
                        startActivity(intent);


                    } else {
                        Toast.makeText(BookActivity.this, "Something wrong happened", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }






    private  void  HandleCurrentlyReadingBooks(final Book book) {

        ArrayList<Book> currentToRead = Utils.getInstance(this).getCurrentlyReadBooks();
        boolean existCurrentlyReadingBooks = false;

        for (Book b : currentToRead) {
            if (b.getId() == book.getId()) {
                existCurrentlyReadingBooks = true;

            }
        }
        if (existCurrentlyReadingBooks) {
            btnAddToCurrentlyReading.setEnabled(false);
        } else {
            btnAddToCurrentlyReading.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (Utils.getInstance(BookActivity.this).addToCurrent(book)) {
                        Toast.makeText(BookActivity.this, "Book Added", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(BookActivity.this, CurrentActivity.class);
                        startActivity(intent);


                    } else {
                        Toast.makeText(BookActivity.this, "Something wrong happened", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }










    private void handleAlreadyRead(Book book) {
        ArrayList<Book> alreadyReadBooks = Utils.getInstance(this).getAlreadyReadBooks();
        boolean existAlreadyReadBooks = false;

        for (Book b : alreadyReadBooks) {
            if (b.getId() == book.getId()) {
                existAlreadyReadBooks = true;

            }
        }
            if (existAlreadyReadBooks) {
                btn.setEnabled(false);
            } else {
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                            if (Utils.getInstance(BookActivity.this).addToAlreadyRead(book)) {
                                Toast.makeText(BookActivity.this, "Book Added", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(BookActivity.this, AlreadyReadBookActivity.class);
                                startActivity(intent);


                            } else {
                                Toast.makeText(BookActivity.this, "Something wrong happened", Toast.LENGTH_SHORT).show();
                            }
                    }
                });
            }
        }

                /*
Fetches date from the books into the book activity.
 */


    private void setData(Book book) {
        txtBookName.setText(book.getName());
        textAuthor.setText(book.getAuthor());
        txtPages.setText(String.valueOf(book.getPages()));
        text5.setText(book.getLongDesc());
        txtLink.setText(book.getLink());
        txtLink.setTextIsSelectable(true);


        Glide.with(this)
                .asBitmap().load(book.getImageUrl())
                .into(bookImage);
    }

    private void initViews() {
        textAuthor = findViewById(R.id.textAuthor);
        txtBookName = findViewById(R.id.textBookName);
        txtPages = findViewById(R.id.txtPages);
        text5 = findViewById(R.id.text5);
        txtLink = findViewById(R.id.bookLink);



        btn = findViewById(R.id.btnAddToAlreadyRead);
        btnAddToCurrentlyReading = findViewById(R.id.btnAddtoCurrentlyReading);
        btnAddToFavorites = findViewById(R.id.btnAddToFavorites );


        bookImage = findViewById(R.id.imageView);

    }
}