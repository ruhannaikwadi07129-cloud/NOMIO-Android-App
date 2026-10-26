package com.nomio.app;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.EditText;

public class MainActivity extends Activity {

    private final int bg = Color.rgb(9, 9, 11);
    private final int cardBg = Color.rgb(22, 22, 25);
    private final int gold = Color.rgb(232, 190, 105);
    private final int white = Color.rgb(247, 245, 240);
    private final int muted = Color.rgb(160, 157, 150);

    private LinearLayout root;
    private LinearLayout menu;
    private TextView cartCount;
    private int cartItems = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setStatusBarColor(bg);
        getWindow().setNavigationBarColor(bg);

        ScrollView scroll = new ScrollView(this);
        scroll.setFillViewport(true);
        scroll.setBackgroundColor(bg);

        root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(dp(20), dp(20), dp(20), dp(28));

        scroll.addView(root);
        setContentView(scroll);

        buildHeader();
        buildHero();
        buildSearch();
        buildCategories();
        buildMenu();
        buildFooter();
    }

    private int dp(float value) {
        return (int) (value * getResources()
                .getDisplayMetrics().density + 0.5f);
    }

    private GradientDrawable shape(
            int color, int strokeColor, int radius) {

        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(color);
        drawable.setCornerRadius(dp(radius));

        if (strokeColor != Color.TRANSPARENT) {
            drawable.setStroke(dp(1), strokeColor);
        }

        return drawable;
    }

    private TextView text(
            String value, int size, int color, boolean bold) {

        TextView t = new TextView(this);
        t.setText(value);
        t.setTextSize(size);
        t.setTextColor(color);
        t.setGravity(Gravity.CENTER_VERTICAL);

        if (bold) {
            t.setTypeface(Typeface.create(
                    "sans-serif", Typeface.BOLD));
        }

        return t;
    }

    private void gap(int height) {
        View v = new View(this);
        root.addView(v, new LinearLayout.LayoutParams(
                1, dp(height)));
    }

    private void buildHeader() {

        LinearLayout header = new LinearLayout(this);
        header.setGravity(Gravity.CENTER_VERTICAL);
        header.setOrientation(LinearLayout.HORIZONTAL);

        LinearLayout brand = new LinearLayout(this);
        brand.setOrientation(LinearLayout.VERTICAL);

        TextView logo = text("NOMIO", 27, gold, true);
        logo.setLetterSpacing(0.16f);

        TextView tagline = text(
                "TASTE BEYOND ORDINARY", 9, muted, true);

        brand.addView(logo);
        brand.addView(tagline);

        header.addView(brand, new LinearLayout.LayoutParams(
                0, -2, 1));

        TextView cart = text("CART  •  0", 12, gold, true);
        cart.setGravity(Gravity.CENTER);
        cart.setPadding(dp(13), dp(12), dp(13), dp(12));
        cart.setBackground(shape(cardBg, gold, 20));
        cartCount = cart;

        cart.setOnClickListener(v ->
                Toast.makeText(this,
                        "Cart feature is coming in Step B!",
                        Toast.LENGTH_SHORT).show());

        header.addView(cart);
        root.addView(header);

        gap(30);
    }

    private void buildHero() {

        LinearLayout hero = new LinearLayout(this);
        hero.setOrientation(LinearLayout.VERTICAL);
        hero.setPadding(dp(20), dp(25), dp(20), dp(24));
        hero.setBackground(shape(
                Color.rgb(25, 22, 17),
                Color.rgb(100, 77, 39), 24));

        TextView badge = text(
                "✦  THE ART OF GOOD FOOD", 10, gold, true);

        hero.addView(badge);

        TextView title = text(
                "Your cravings.\\nOur obsession.",
                31, white, true);

        title.setLineSpacing(dp(4), 1f);

        LinearLayout.LayoutParams titleParams =
                new LinearLayout.LayoutParams(-1, -2);
        titleParams.topMargin = dp(17);
        hero.addView(title, titleParams);

        TextView subtitle = text(
                "Discover exceptional flavours,\\n"
                + "delivered to your doorstep.",
                14, muted, false);

        subtitle.setLineSpacing(dp(4), 1f);

        LinearLayout.LayoutParams subParams =
                new LinearLayout.LayoutParams(-1, -2);
        subParams.topMargin = dp(12);
        hero.addView(subtitle, subParams);

        TextView explore = text(
                "EXPLORE THE MENU     →", 12, bg, true);

        explore.setGravity(Gravity.CENTER);
        explore.setPadding(dp(18), dp(15), dp(18), dp(15));
        explore.setBackground(shape(gold, Color.TRANSPARENT, 12));

        LinearLayout.LayoutParams btnParams =
                new LinearLayout.LayoutParams(-1, -2);
        btnParams.topMargin = dp(22);
        hero.addView(explore, btnParams);

        explore.setOnClickListener(v -> {
            if (menu != null) {
                menu.requestFocus();
                Toast.makeText(this,
                        "Discover our menu below",
                        Toast.LENGTH_SHORT).show();
            }
        });

        root.addView(hero);
        gap(28);
    }

    private void buildSearch() {

        TextView heading = text(
                "Find your next favourite", 21, white, true);

        root.addView(heading);
        gap(13);

        EditText search = new EditText(this);
        search.setSingleLine(true);
        search.setTextColor(white);
        search.setHintTextColor(muted);
        search.setHint("⌕   Search dishes or restaurants");
        search.setTextSize(14);
        search.setPadding(dp(16), dp(7), dp(16), dp(7));
        search.setBackground(shape(cardBg, Color.DKGRAY, 14));

        root.addView(search, new LinearLayout.LayoutParams(
                -1, dp(54)));

        gap(25);
    }

    private void buildCategories() {

        root.addView(text(
                "EXPLORE BY MOOD", 12, gold, true));

        gap(13);

        HorizontalScrollView hsv = new HorizontalScrollView(this);
        hsv.setHorizontalScrollBarEnabled(false);

        LinearLayout row = new LinearLayout(this);
        row.setOrientation(LinearLayout.HORIZONTAL);

        String[] categories = {
                "✦  All", "🍛  Indian", "🍕  Pizza",
                "🍔  Burgers", "🍰  Desserts"
        };

        for (int i = 0; i < categories.length; i++) {

            final String category = categories[i];

            TextView chip = text(category, 12,
                    i == 0 ? bg : white, true);

            chip.setGravity(Gravity.CENTER);
            chip.setPadding(dp(16), dp(12), dp(16), dp(12));

            chip.setBackground(shape(
                    i == 0 ? gold : cardBg,
                    i == 0 ? Color.TRANSPARENT
                            : Color.rgb(55, 55, 58),
                    24));

            LinearLayout.LayoutParams params =
                    new LinearLayout.LayoutParams(-2, -2);

            params.setMargins(0, 0, dp(9), 0);
            row.addView(chip, params);

            chip.setOnClickListener(v ->
                    Toast.makeText(this,
                            category + " selected",
                            Toast.LENGTH_SHORT).show());
        }

        hsv.addView(row);
        root.addView(hsv);

        gap(29);
    }

    private void buildMenu() {

        TextView title = text(
                "Signature selections", 22, white, true);

        root.addView(title);
        gap(5);

        root.addView(text(
                "A little extraordinary, every day.",
                13, muted, false));

        gap(17);

        menu = new LinearLayout(this);
        menu.setOrientation(LinearLayout.VERTICAL);
        root.addView(menu);

        addFood("ROYAL CLASSIC",
                "Royal Biryani",
                "Aromatic rice, rich spices",
                "₹180", "🍛");

        addFood("THE ITALIAN EDIT",
                "Golden Crust Pizza",
                "Melty cheese, signature herbs",
                "₹249", "🍕");

        addFood("THE COMFORT CLUB",
                "Smoky Classic Burger",
                "Crispy layers, bold flavour",
                "₹129", "🍔");

        addFood("SWEET FINISH",
                "Chocolate Dream",
                "A rich and creamy indulgence",
                "₹99", "🍰");
    }

    private void addFood(
            String label,
            String name,
            String description,
            String price,
            String emoji) {

        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setPadding(dp(17), dp(16), dp(17), dp(16));
        card.setBackground(shape(cardBg,
                Color.rgb(48, 45, 39), 19));

        LinearLayout.LayoutParams cardParams =
                new LinearLayout.LayoutParams(-1, -2);

        cardParams.bottomMargin = dp(13);
        menu.addView(card, cardParams);

        TextView tag = text(label, 9, gold, true);
        tag.setLetterSpacing(0.12f);
        card.addView(tag);

        LinearLayout row = new LinearLayout(this);
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.setGravity(Gravity.CENTER_VERTICAL);

        LinearLayout info = new LinearLayout(this);
        info.setOrientation(LinearLayout.VERTICAL);

        TextView foodName = text(name, 18, white, true);
        foodName.setPadding(0, dp(9), 0, dp(5));

        TextView details = text(
                description, 12, muted, false);

        TextView cost = text(price, 17, gold, true);
        cost.setPadding(0, dp(10), 0, 0);

        info.addView(foodName);
        info.addView(details);
        info.addView(cost);

        row.addView(info, new LinearLayout.LayoutParams(
                0, -2, 1));

        TextView icon = text(emoji, 36, white, false);
        icon.setGravity(Gravity.CENTER);
        icon.setBackground(shape(
                Color.rgb(39, 34, 26),
                Color.rgb(86, 68, 39), 16));

        row.addView(icon, new LinearLayout.LayoutParams(
                dp(76), dp(76)));

        LinearLayout.LayoutParams rowParams =
                new LinearLayout.LayoutParams(-1, -2);

        rowParams.topMargin = dp(5);
        card.addView(row, rowParams);

        TextView add = text("+  ADD TO CART", 11, bg, true);
        add.setGravity(Gravity.CENTER);
        add.setPadding(0, dp(13), 0, dp(13));
        add.setBackground(shape(gold, Color.TRANSPARENT, 11));

        LinearLayout.LayoutParams addParams =
                new LinearLayout.LayoutParams(-1, -2);

        addParams.topMargin = dp(16);
        card.addView(add, addParams);

        add.setOnClickListener(v -> {
            cartItems++;
            cartCount.setText("CART  •  " + cartItems);

            Toast.makeText(this,
                    name + " added to cart",
                    Toast.LENGTH_SHORT).show();
        });
    }

    private void buildFooter() {

        gap(15);

        TextView line = text(
                "━━━━━━━━━━━━━━━━━━━━", 12, gold, false);
        line.setGravity(Gravity.CENTER);
        root.addView(line);

        gap(12);

        TextView logo = text(
                "N O M I O", 20, gold, true);
        logo.setGravity(Gravity.CENTER);
        root.addView(logo);

        TextView tagline = text(
                "TASTE BEYOND ORDINARY", 10, muted, true);
        tagline.setGravity(Gravity.CENTER);

        root.addView(tagline);
        gap(10);

        TextView note = text(
                "Crafted for the love of great food.",
                12, muted, false);
        note.setGravity(Gravity.CENTER);

        root.addView(note);
    }
                                   }
