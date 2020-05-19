package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.model.Neighbour;


public class NeighbourDetailsActivity extends AppCompatActivity {
    //-- PROPERTIES
    // UI
    ImageView userAvatarImageView;
    TextView userNameTextView;
    TextView userNameDetailsTextView;
    TextView userAddressTextView;
    TextView userPhoneNumberTextView;
    TextView userWebsiteTextView;
    TextView userAboutMeTextView;
    ImageButton favoriteImageButton;
    ImageButton backImageButton;

    // NEIGHBOUR
    Neighbour neighbour;
    Neighbour favoriteNeighbour;
    // BUNDLE EXTRA NEIGHBOUR
    public static final String BUNDLE_EXTRA_NEIGHBOUR = "BUNDLE_EXTRA_NEIGHBOUR";


    //-- VIEW LIFE CYCLE
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            // NEIGHBOUR
            neighbour = (Neighbour) extras.getSerializable(BUNDLE_EXTRA_NEIGHBOUR);

            // NEIGHBOUR PARAMETERS
            long id = neighbour.getId();
            String userName = neighbour.getName();
            final String avatarURL = neighbour.getAvatarUrl().replace("150", "400");
            String address = neighbour.getAddress();
            String phoneNumber = neighbour.getPhoneNumber();
            String website = getResources().getString(R.string.website_neighbour) + userName;
            String aboutMe = neighbour.getAboutMe();
            final Boolean[] favorite = {neighbour.getFavorite()};

            favoriteNeighbour = new Neighbour(id, userName, avatarURL, address, phoneNumber, aboutMe,favorite[0]);

            // INIT UI IN VIEW LIFE CYCLE
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_details_neighbour);

            // INIT ALL ELEMENTS OF UI
            userAvatarImageView = findViewById(R.id.user_avatar);
            Glide.with(this).load(avatarURL).into(userAvatarImageView);

            userNameTextView = findViewById(R.id.user_name);
            userNameTextView.setText(userName);

            userNameDetailsTextView = findViewById(R.id.user_name_details);
            userNameDetailsTextView.setText(userName);

            userAddressTextView = findViewById(R.id.user_address_details);
            userAddressTextView.setText(address);

            userPhoneNumberTextView = findViewById(R.id.user_phoneNumber_details);
            userPhoneNumberTextView.setText(phoneNumber);

            userWebsiteTextView = findViewById(R.id.user_website_details);
            userWebsiteTextView.setText(website);

            userAboutMeTextView = findViewById(R.id.user_about_me_details);
            userAboutMeTextView.setText(aboutMe);

            favoriteImageButton = findViewById(R.id.favorite_image_button);
            // SET ON CLICK LISTENER
            favoriteImageButton.setOnClickListener(v -> {
                neighbour.setFavorite(!neighbour.getFavorite());

                setupFavoriteImageButtonDrawable();
            });

            backImageButton = findViewById(R.id.back_button);
            // SET ON CLICK LISTENER
            backImageButton.setOnClickListener((View v) -> {
                onBackPressed();
            });

            setupFavoriteImageButtonDrawable();
        }
    }

    //-- METHODS
    // SETUP FAVORITE IMAGE BUTTON DRAWABLE
    private void setupFavoriteImageButtonDrawable() {
        if (neighbour.getFavorite()) {
            favoriteImageButton.setImageDrawable(getDrawable(R.drawable.favorite_added));
        } else {
            favoriteImageButton.setImageDrawable(getDrawable(R.drawable.favorite_not_added));
        }
    }

    @Override
    public void onBackPressed() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("favoriteNeighbour", neighbour);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

}
