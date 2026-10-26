package com.nomio.app;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ScrollView;

public class MainActivity extends Activity {

    private final int black = Color.rgb(12, 12, 12);
    private final int gold = Color.rgb(222, 186, 112);
    private LinearLayout content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ScrollView scroll = new ScrollView(this);
        scroll.setBackgroundColor(black);

        content = new LinearLayout(this);
        content.setOrientation(LinearLayout.VERTICAL);
        content.setPadding(22, 30, 22, 30);

        scroll.addView(content);
        setContentView(scroll);

        addText("N O M I O", 32, gold, true);
        addText("TASTE BEYOND ORDINARY", 12, Color.WHITE, false);
        addText("Good food. Great moments.", 23, Color.WHITE, true);
        addText("Discover delicious food near you.", 15, gold, false);

        addSection("POPULAR NEAR YOU");

        addRestaurant("Royal Biryani House", "Biryani • Indian", "₹180");
        addRestaurant("Golden Crust Pizza", "Pizza • Italian", "₹249");
        addRestaurant("Spice Street", "North Indian • Meals", "₹150");
        addRestaurant("Burger District", "Burgers • Fast Food", "₹129");

        addSection("YOUR NOMIO PROMISE");
        addText("🎁 First order: FREE delivery offer", 16,
                Color.WHITE, false);
        addText("Delivery fee after offer: ₹30", 15, gold, false);
        addText("Restaurant platform fee: ₹15 per completed order",
                14, Color.WHITE, false);

        addText("Welcome to NOMIO ✨", 18, gold, true);
    }

    private void addText(String text, int size, int color, boolean bold) {
        TextView view = new TextView(this);
        view.setText(text);
        view.setTextSize(size);
        view.setTextColor(color);
        view.setPadding(0, 10, 0, 10);

        if (bold) {
            view.setTypeface(null, android.graphics.Typeface.BOLD);
        }

        content.addView(view);
    }

    private void addSection(String title) {
        TextView view = new TextView(this);
        view.setText(title);
        view.setTextSize(15);
        view.setTextColor(gold);
        view.setTypeface(null, android.graphics.Typeface.BOLD);
        view.setPadding(0, 26, 0, 12);
        content.addView(view);
    }

    private void addRestaurant(String name, String category, String price) {
        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setPadding(18, 15, 18, 15);
        card.setBackgroundColor(Color.rgb(30, 30, 30));

        TextView title = new TextView(this);
        title.setText(name);
        title.setTextColor(gold);
        title.setTextSize(19);
        title.setTypeface(null, android.graphics.Typeface.BOLD);

        TextView details = new TextView(this);
        details.setText(category + "   •   From " + price);
        details.setTextColor(Color.WHITE);
        details.setTextSize(14);
        details.setPadding(0, 8, 0, 0);

        card.addView(title);
        card.addView(details);

        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);

        params.setMargins(0, 0, 0, 14);
        content.addView(card, params);
    }
                      }
