package com.nomio.app;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.*;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends Activity {

    int bg = Color.rgb(9, 9, 11);
    int card = Color.rgb(24, 24, 27);
    int gold = Color.rgb(232, 190, 105);
    int white = Color.rgb(248, 246, 240);
    int muted = Color.rgb(160, 157, 150);

    LinearLayout root, foodList;
    TextView cartLabel;
    EditText searchBox;

    ArrayList<Food> foods = new ArrayList<>();
    ArrayList<Food> cart = new ArrayList<>();

    String selectedCategory = "All";

    class Food {
        String name, category, description, emoji;
        int price;

        Food(String n, String c, String d, String e, int p) {
            name = n;
            category = c;
            description = d;
            emoji = e;
            price = p;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setStatusBarColor(bg);
        getWindow().setNavigationBarColor(bg);

        addSampleFoods();

        ScrollView scroll = new ScrollView(this);
        scroll.setBackgroundColor(bg);

        root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(dp(20), dp(20), dp(20), dp(30));

        scroll.addView(root);
        setContentView(scroll);

        buildHeader();
        buildHero();
        buildSearch();
        buildCategories();
        buildMenu();
        buildFooter();
    }

    int dp(int n) {
        return (int)(n * getResources()
                .getDisplayMetrics().density + 0.5f);
    }

    GradientDrawable shape(int color, int stroke, int radius) {
        GradientDrawable d = new GradientDrawable();
        d.setColor(color);
        d.setCornerRadius(dp(radius));
        if (stroke != Color.TRANSPARENT) {
            d.setStroke(dp(1), stroke);
        }
        return d;
    }

    TextView label(String value, int size, int color, boolean bold) {
        TextView t = new TextView(this);
        t.setText(value);
        t.setTextSize(size);
        t.setTextColor(color);
        if (bold) {
            t.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        }
        return t;
    }

    void gap(int height) {
        root.addView(new TextView(this), new LinearLayout.LayoutParams(1, dp(height)));
    }

    void addSampleFoods() {
        foods.add(new Food("Royal Biryani", "Indian",
                "Aromatic rice with rich spices", "🍛", 180));
        foods.add(new Food("Golden Crust Pizza", "Pizza",
                "Cheese, herbs and signature sauce", "🍕", 249));
        foods.add(new Food("Smoky Classic Burger", "Burgers",
                "Crispy layers and bold flavour", "🍔", 129));
        foods.add(new Food("Chocolate Dream", "Desserts",
                "Rich and creamy chocolate dessert", "🍰", 99));
        foods.add(new Food("Paneer Masala", "Indian",
                "Paneer in a creamy spiced gravy", "🥘", 199));
        foods.add(new Food("Garden Pizza", "Pizza",
                "Fresh vegetables and melted cheese", "🍕", 219));
    }

    void buildHeader() {
        LinearLayout row = new LinearLayout(this);
        row.setGravity(Gravity.CENTER_VERTICAL);

        LinearLayout brand = new LinearLayout(this);
        brand.setOrientation(LinearLayout.VERTICAL);

        TextView logo = label("NOMIO", 28, gold, true);
        logo.setLetterSpacing(0.16f);
        brand.addView(logo);
        brand.addView(label("TASTE BEYOND ORDINARY", 9, muted, true));

        row.addView(brand, new LinearLayout.LayoutParams(0, -2, 1));

        cartLabel = label("CART  •  0", 12, gold, true);
        cartLabel.setGravity(Gravity.CENTER);
        cartLabel.setPadding(dp(13), dp(13), dp(13), dp(13));
        cartLabel.setBackground(shape(card, gold, 20));
        cartLabel.setOnClickListener(v -> showCart());

        row.addView(cartLabel);
        root.addView(row);
        gap(28);
    }

    void buildHero() {
        LinearLayout hero = new LinearLayout(this);
        hero.setOrientation(LinearLayout.VERTICAL);
        hero.setPadding(dp(20), dp(24), dp(20), dp(24));
        hero.setBackground(shape(Color.rgb(30, 25, 17),
                Color.rgb(100, 77, 39), 22));

        hero.addView(label("✦  THE ART OF GOOD FOOD", 10, gold, true));

        TextView title = label("Your cravings.\nOur obsession.",
                30, white, true);
        title.setPadding(0, dp(16), 0, dp(10));
        hero.addView(title);

        hero.addView(label("Discover exceptional flavours,\ndelivered to your doorstep.",
                14, muted, false));

        root.addView(hero);
        gap(25);
    }

    void buildSearch() {
        root.addView(label("Find your next favourite", 21, white, true));
        gap(12);

        searchBox = new EditText(this);
        searchBox.setSingleLine(true);
        searchBox.setTextColor(white);
        searchBox.setHintTextColor(muted);
        searchBox.setHint("Search dishes...");
        searchBox.setTextSize(14);
        searchBox.setPadding(dp(15), dp(8), dp(15), dp(8));
        searchBox.setBackground(shape(card, Color.DKGRAY, 14));

        root.addView(searchBox, new LinearLayout.LayoutParams(-1, dp(52)));

        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                renderFoods();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        gap(22);
    }

    void buildCategories() {
        root.addView(label("EXPLORE BY CATEGORY", 12, gold, true));
        gap(12);

        LinearLayout row = new LinearLayout(this);
        row.setOrientation(LinearLayout.HORIZONTAL);

        String[] cats = {"All", "Indian", "Pizza", "Burgers", "Desserts"};

        for (String category : cats) {
            TextView chip = label(category, 12,
                    category.equals(selectedCategory) ? bg : white, true);

            chip.setGravity(Gravity.CENTER);
            chip.setPadding(dp(13), dp(11), dp(13), dp(11));
            chip.setBackground(shape(
                    category.equals(selectedCategory) ? gold : card,
                    Color.TRANSPARENT, 22));

            LinearLayout.LayoutParams p =
                    new LinearLayout.LayoutParams(-2, -2);
            p.setMargins(0, 0, dp(7), 0);
            row.addView(chip, p);

            chip.setOnClickListener(v -> {
                selectedCategory = category;
                rebuildScreen();
            });
        }

        HorizontalScrollView horizontal = new HorizontalScrollView(this);
        horizontal.setHorizontalScrollBarEnabled(false);
        horizontal.addView(row);
        root.addView(horizontal);

        gap(25);
    }

    void buildMenu() {
        root.addView(label("Signature selections", 22, white, true));
        gap(5);
        root.addView(label("A little extraordinary, every day.", 13, muted, false));
        gap(15);

        foodList = new LinearLayout(this);
        foodList.setOrientation(LinearLayout.VERTICAL);
        root.addView(foodList);

        renderFoods();
    }

    void renderFoods() {
        if (foodList == null) return;

        foodList.removeAllViews();

        String query = searchBox == null ? "" :
                searchBox.getText().toString().toLowerCase(Locale.ROOT).trim();

        for (Food food : foods) {
            boolean categoryMatch = selectedCategory.equals("All")
                    || food.category.equals(selectedCategory);

            boolean searchMatch = food.name.toLowerCase(Locale.ROOT).contains(query)
                    || food.description.toLowerCase(Locale.ROOT).contains(query);

            if (categoryMatch && searchMatch) {
                addFoodCard(food);
            }
        }

        if (foodList.getChildCount() == 0) {
            TextView empty = label("No dishes found. Try another search.",
                    14, muted, false);
            empty.setPadding(0, dp(20), 0, dp(20));
            foodList.addView(empty);
        }
    }

    void addFoodCard(Food food) {
        LinearLayout box = new LinearLayout(this);
        box.setOrientation(LinearLayout.VERTICAL);
        box.setPadding(dp(16), dp(15), dp(16), dp(15));
        box.setBackground(shape(card, Color.rgb(48, 45, 39), 18));

        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(-1, -2);
        params.bottomMargin = dp(13);
        foodList.addView(box, params);

        box.addView(label(food.category.toUpperCase(Locale.ROOT),
                9, gold, true));

        LinearLayout row = new LinearLayout(this);
        row.setGravity(Gravity.CENTER_VERTICAL);

        LinearLayout info = new LinearLayout(this);
        info.setOrientation(LinearLayout.VERTICAL);

        TextView name = label(food.name, 17, white, true);
        name.setPadding(0, dp(8), 0, dp(5));

        info.addView(name);
        info.addView(label(food.description, 12, muted, false));
        info.addView(label("₹" + food.price, 17, gold, true));

        row.addView(info, new LinearLayout.LayoutParams(0, -2, 1));

        TextView emoji = label(food.emoji, 34, white, false);
        emoji.setGravity(Gravity.CENTER);
        emoji.setBackground(shape(Color.rgb(40, 34, 25),
                Color.rgb(86, 68, 39), 15));

        row.addView(emoji, new LinearLayout.LayoutParams(dp(70), dp(70)));
        box.addView(row);

        TextView add = label("+  ADD TO CART", 11, bg, true);
        add.setGravity(Gravity.CENTER);
        add.setPadding(0, dp(13), 0, dp(13));
        add.setBackground(shape(gold, Color.TRANSPARENT, 10));

        LinearLayout.LayoutParams ap =
                new LinearLayout.LayoutParams(-1, -2);
        ap.topMargin = dp(15);
        box.addView(add, ap);

        add.setOnClickListener(v -> {
            cart.add(food);
            updateCart();
            Toast.makeText(this, food.name + " added",
                    Toast.LENGTH_SHORT).show();
        });

        box.setOnClickListener(v -> showFoodDetails(food));
    }

    void showFoodDetails(Food food) {
        new android.app.AlertDialog.Builder(this)
                .setTitle(food.emoji + "  " + food.name)
                .setMessage(food.description + "\n\nPrice: ₹" + food.price)
                .setNegativeButton("Close", null)
                .setPositiveButton("Add to cart", (dialog, which) -> {
                    cart.add(food);
                    updateCart();
                })
                .show();
    }

    void updateCart() {
        int total = 0;
        for (Food f : cart) total += f.price;

        cartLabel.setText("CART • " + cart.size());
    }

    void showCart() {
        if (cart.isEmpty()) {
            new android.app.AlertDialog.Builder(this)
                    .setTitle("Your NOMIO Cart")
                    .setMessage("Your cart is empty. Add a dish to get started!")
                    .setPositiveButton("Explore food", null)
                    .show();
            return;
        }

        StringBuilder details = new StringBuilder();
        int total = 0;

        for (Food f : cart) {
            details.append(f.name)
                    .append("  —  ₹").append(f.price).append("\n");
            total += f.price;
        }

        details.append("\nTotal: ₹").append(total);

        new android.app.AlertDialog.Builder(this)
                .setTitle("Your NOMIO Cart")
                .setMessage(details.toString())
                .setNegativeButton("Keep browsing", null)
                .setPositiveButton("Clear cart", (dialog, which) -> {
                    cart.clear();
                    updateCart();
                })
                .show();
    }

    void rebuildScreen() {
        root.removeAllViews();
        buildHeader();
        buildHero();
        buildSearch();
        buildCategories();
        buildMenu();
        buildFooter();
    }

    void buildFooter() {
        gap(20);
        TextView logo = label("N O M I O", 19, gold, true);
        logo.setGravity(Gravity.CENTER);
        root.addView(logo);

        TextView tagline = label("TASTE BEYOND ORDINARY", 10, muted, true);
        tagline.setGravity(Gravity.CENTER);
        root.addView(tagline);
    }
    }
