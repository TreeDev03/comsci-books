package com.example.comsci;

import android.content.Context;
import android.content.SharedPreferences;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Utils {

    private static final String ALL_BOOKS_KEY = "all_books";
    private static final String ALREADY_READ_BOOKS = "already_read_books";
    private static final String CURRENTLY_READING_BOOKS = "currently_reading_books";
    private static final String Favorite_BOOKS = "favorite_books";


    private static Utils instance;

    private SharedPreferences sharedPreferences;

    private ArrayList<Book> allBooks;

    private ArrayList<Book> alreadyReadBooks;

    private ArrayList<Book> currentlyReadBooks;

    private ArrayList<Book> favorite;

;


            /*
Fetching the data to other activitys when set on click listener or delete is activated.

 */


    private Utils(Context context) {

        sharedPreferences = context.getSharedPreferences("alternate_db", Context.MODE_PRIVATE);



        if (null == getAllBooks()) {


            initData();
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();

        if (null == getAlreadyReadBooks() ) {
            editor.putString(ALREADY_READ_BOOKS, gson.toJson(new ArrayList<Book>()));
            editor.commit();


        }

        if (null == getCurrentlyReadBooks()) {
            editor.putString(CURRENTLY_READING_BOOKS, gson.toJson(new ArrayList<Book>()));
            editor.commit();

        }

        if (null == getFavoriteBooks()) {
            editor.putString(Favorite_BOOKS, gson.toJson(new ArrayList<Book>()));
            editor.commit();



        }

    }





        private void initData () {

            ArrayList<Book> books = new ArrayList<>();


            books.add(new Book(1, "Introducing Python", "Bill Lubanovic", 630,
                    "https://images-na.ssl-images-amazon.com/images/I/51yPhKJee5L._SX379_BO1,204,203,200_.jpg",
                    "Master Python", "Introducing Python gives you an introduction to the core of Python and it’s features." +
                    " It teaches you how to create statements, numbers, lists, and dictionaries to build projects. Go into Python’s" +
                    " object oriented tools for code structure.\n", "https://www.oreilly.com/library/view/introducing-python-2nd/9781492051374/ "));

            books.add(new Book(2, "Think Java", " Allen B. Downey/ Chris Mayfield", 301,
                    "https://cdn11.bigcommerce.com/s-igquupw3/images/stencil/1280x1280/products/1129751/28746520/9781492072508__79463.1616223802.jpg?c=2",
                    "Master Java", "Think Java is a hands-on introduction to Java. The book\n starts with the most basic programming concepts " +
                    "like data types, for loops, arithmetic operators, strings and gradually works its way to advanced object-oriented techniques.", " https://www.oreilly.com/library/view/think-java-2nd/9781492072492/"));

            books.add(new Book(3, "The C Programming Language", "Brain W.Kernighan/Dennis M Ritchie", 261,
                    "https://images-na.ssl-images-amazon.com/images/I/411ejyE8obL._SX377_BO1,204,203,200_.jpg",
                    "Master C", "Written by the developers of the C Programming Language.\n Reviews Types, Operators and expressions · " +
                    "Control flow · Functions and Program\n Structure · Pointers and Arrays · Structures etc.", "https://www.oreilly.com/library/view/\nc-programming-language/9780133086249/ "));

            books.add(new Book(4, "Cracking The Coding Interview", " Gayle Laakmann McDowell", 687,
                    "https://images-na.ssl-images-amazon.com/images/I/41oYsXjLvZL._SX348_BO1,204,203,200_.jpg",

                    "Pass the Interview", "Cracking The Coding Interview gives you 189 programming\n interview questions, ranging from" +
                    " the basics to the trickiest algorithm problems and " +
                    "a walk-through of how to derive each solution, so that you can learn how to get there yourself.", "https://www.crackingthecodinginterview.com/" ));

            books.add(new Book(5, "Deep Learning", " Ian Goodfellow", 800,
                    "https://m.media-amazon.com/images/I/61qbj4KwauL._AC_SY780_.jpg",
                    "Machine Learning",
                    "An introduction to a broad range of topics in deep learning, covering mathematical and conceptual" +
                    " background,\n teaches deep learning techniques used in industry, and research. Recommended by Elon Musk Tesla CEO ", "https://www.deeplearningbook.org/"));

        SharedPreferences.Editor editor = sharedPreferences.edit();
            Gson gson= new Gson();
            editor.putString(ALL_BOOKS_KEY, gson.toJson(books));
            editor.commit();



        }


        public static Utils getInstance (Context context){
            if (null != instance) {
                return instance;


            } else {
                instance = new Utils(context);
                return instance;

            }
        }

                /*
Logic to add and remove the book data

 */


        public ArrayList<Book> getAllBooks () {
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<Book>>(){}.getType();
            ArrayList<Book> books = gson.fromJson(sharedPreferences.getString(ALL_BOOKS_KEY,null), type);
           return books;
        }

        public ArrayList<Book> getAlreadyReadBooks () {
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<Book>>(){}.getType();
            ArrayList<Book> books = gson.fromJson(sharedPreferences.getString(ALREADY_READ_BOOKS,null), type);
            return books;

        }


        public ArrayList<Book> getCurrentlyReadBooks () {
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<Book>>(){}.getType();
            ArrayList<Book> books = gson.fromJson(sharedPreferences.getString(CURRENTLY_READING_BOOKS,null), type);
            return books;
        }

        public ArrayList<Book> getFavoriteBooks () {
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<Book>>(){}.getType();
            ArrayList<Book> books = gson.fromJson(sharedPreferences.getString(Favorite_BOOKS,null), type);
            return books;
        }

        public Book getBookById ( int id){

            ArrayList<Book> books = getAllBooks();
            if (null != books) {
                for (Book b : books) {
                    if (b.getId() == id) {
                        return b;
                    }

                }

            }

            return null;
        }

        public boolean addToAlreadyRead (Book book){
            ArrayList<Book> books = getAlreadyReadBooks();
            if (null != books) {
                if (books.add(book)) {
                    Gson gson = new Gson();
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.remove(ALREADY_READ_BOOKS);
                    editor.putString(ALREADY_READ_BOOKS, gson.toJson(books));
                    editor.commit();
                    return true;
                }

            }
            return false;
        }


        public boolean addToCurrent (Book book){

            ArrayList<Book> books = getCurrentlyReadBooks();
            if (null != books) {
                if (books.add(book)) {
                    Gson gson = new Gson();
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.remove(CURRENTLY_READING_BOOKS);
                    editor.putString(CURRENTLY_READING_BOOKS, gson.toJson(books));
                    editor.commit();
                    return true;
                }

            }
            return false;
        }


        public boolean addToFavorite (Book book) {
            ArrayList<Book> books = getFavoriteBooks();
            if (null != books) {
                if (books.add(book)) {
                    Gson gson = new Gson();
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.remove(Favorite_BOOKS);
                    editor.putString(Favorite_BOOKS, gson.toJson(books));
                    editor.commit();
                    return true;
                }

            }
            return false;
        }


        public boolean removeFromAlreadyRead (Book book) {
            ArrayList<Book> books = getAlreadyReadBooks();
            if (null != books) {
                for (Book b : books) {
                    if (b.getId() == book.getId()) {
                        if (books.remove(b)) {
                            Gson gson = new Gson();
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.remove(ALREADY_READ_BOOKS);
                            editor.putString(ALREADY_READ_BOOKS, gson.toJson(books));
                            editor.commit();
                            return true;
                        }
                    }
                }
            }
            return false;
        }

        public boolean removeCurrentRead (Book book){
            ArrayList<Book> books = getCurrentlyReadBooks();
            if (null != books) {
                for (Book b : books) {
                    if (b.getId() == book.getId()) {
                        if (books.remove(b)) {
                            Gson gson = new Gson();
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.remove(CURRENTLY_READING_BOOKS);
                            editor.putString(CURRENTLY_READING_BOOKS, gson.toJson(books));
                            editor.commit();
                            return true;
                        }
                    }
                }
            }
            return false;
        }


        public boolean removeFavoriteBooks (Book book){
            ArrayList<Book> books = getFavoriteBooks();
            if (null != books) {
                for (Book b : books) {
                    if (b.getId() == book.getId()) {
                        if (books.remove(b)) {
                            Gson gson = new Gson();
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.remove(Favorite_BOOKS);
                            editor.putString(Favorite_BOOKS, gson.toJson(books));
                            editor.commit();
                            return true;
                        }
                    }
                }
            }
            return false;
        }
        }






