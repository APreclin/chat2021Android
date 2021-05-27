package com.example.chat2021;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConvActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String CAT = "LE4-SI";
    ScrollView conversation;
    LinearLayout conversationLayout;
    APIInterface apiService;
    String hash;
    ListMessage lm;
    ImageButton galleryButton;
    private static int RESULT_LOAD_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_conversation);
        conversation = findViewById(R.id.conversation_svMessages);
        conversationLayout = (LinearLayout) findViewById(R.id.conversation_svLayoutMessages);
        galleryButton = findViewById(R.id.galleryButton);
        galleryButton.setOnClickListener(this);

        Bundle bdl = this.getIntent().getExtras();
        hash = bdl.getString("hash");
        apiService = APIClient.getClient().create(APIInterface.class);
        Call<ListMessage> call1 = apiService.doGetListMessage(hash, Integer.parseInt(bdl.getString("conv")));
        call1.enqueue(new Callback<ListMessage>() {
            @Override
            public void onResponse(Call<ListMessage> call, Response<ListMessage> response) {
                lm = response.body();
                for(Message m : lm.messages) {
                    TextView message = new TextView(ConvActivity.this);
                    message.setText(m.contenu);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.MATCH_PARENT
                    );
                    message.setLayoutParams(params);
                    conversationLayout.addView(message);
                }

                Log.i(CAT,lm.toString());
            }

            @Override
            public void onFailure(Call<ListMessage> call, Throwable t) {
                call.cancel();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null) {
            /*
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
             */
            ImageView image = new ImageView(ConvActivity.this);
            // image.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            Bitmap bm = data.getExtras().getParcelable("data");
            image.setImageBitmap(bm);
            // image.setImageURI(selectedImage);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
            );
            image.setLayoutParams(params);
            conversationLayout.addView(image);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.galleryButton:
                /*Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);*/
                Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
                getIntent.setType("image/*");

                Intent pickIntent = new Intent(Intent.ACTION_PICK);
                pickIntent.setType("image/*");

                Intent chooseIntent = Intent.createChooser(getIntent, "Select image");
                chooseIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] { pickIntent });

                startActivityForResult(chooseIntent, RESULT_LOAD_IMAGE);
                break;
        }
    }
}