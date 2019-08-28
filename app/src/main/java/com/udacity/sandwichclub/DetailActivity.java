package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private TextView mAlsoKnownAs;
    private TextView mPlaceOfOrigin;
    private TextView mDescription;
    private TextView mIngredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        mAlsoKnownAs = findViewById(R.id.also_known_tv);
        mPlaceOfOrigin = findViewById(R.id.origin_tv);
        mDescription = findViewById(R.id.description_tv);
        mIngredients = findViewById(R.id.ingredients_tv);
        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = null;
        try {
            sandwich = JsonUtils.parseSandwichJson(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage()).error(R.drawable.ic_error_image)
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        if (sandwich.getAlsoKnownAs() != null && sandwich.getAlsoKnownAs().size() != 0) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < sandwich.getAlsoKnownAs().size(); i++) {
                sb.append(sandwich.getAlsoKnownAs().get(i));
                if (i != sandwich.getAlsoKnownAs().size() - 1) {
                    sb.append(", ");
                }
            }
            mAlsoKnownAs.setText(sb.toString());
        } else {
            mAlsoKnownAs.setText(R.string.empty_also_known_as);
        }
        if (sandwich.getPlaceOfOrigin() != null && !sandwich.getPlaceOfOrigin().equals("")) {
            mPlaceOfOrigin.setText(sandwich.getPlaceOfOrigin());
        } else {
            mPlaceOfOrigin.setText(R.string.empty_place_of_origin);
        }
        if (sandwich.getDescription() != null && !sandwich.getDescription().equals("")) {
            mDescription.setText(sandwich.getDescription());
        } else {
            mDescription.setText(R.string.empty_description);
        }
        if (sandwich.getIngredients() != null && sandwich.getIngredients().size() != 0) {
            StringBuilder ingredientsSb = new StringBuilder();
            for (int i = 0; i < sandwich.getIngredients().size(); i++) {
                ingredientsSb.append(sandwich.getIngredients().get(i));
                if (i != sandwich.getIngredients().size() - 1) {
                    ingredientsSb.append(", ");
                }
            }
            mIngredients.setText(ingredientsSb.toString());
        } else {
            mIngredients.setText(R.string.empty_ingredients);
        }
    }
}
