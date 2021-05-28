package com.example.chat2021;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConvActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String CAT = "LE4-SI";
    RelativeLayout conversationLayout;
    APIInterface apiService;
    String hash;
    ListMessage lm;
    Message m;
    ImageButton galleryButton;
    ImageButton cameraButton;
    Button btnSend;
    EditText txtMsg;
    String photoPath;
    private static int RESULT_LOAD_IMAGE = 1;
    private static int RESULT_CAMERA_IMAGE = 2;
    private MessagesAdapter messagesAdapter;
    private RecyclerView conversation;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_conversation);

        mProgressBar=(ProgressBar)findViewById(R.id.progress_bar);
        mProgressBar.setVisibility(View.VISIBLE);

        conversation = findViewById(R.id.conversation_rvMessages);
        conversationLayout = (RelativeLayout) findViewById(R.id.relativeLayoutMessages);
        conversation=(RecyclerView)findViewById(R.id.conversation_rvMessages);
        conversation.setLayoutManager(new LinearLayoutManager(this));

        galleryButton = findViewById(R.id.galleryButton);
        cameraButton = findViewById(R.id.cameraButton);
        btnSend = findViewById(R.id.conversation_btnOK);
        txtMsg = findViewById(R.id.conversation_edtMessage);

        btnSend.setOnClickListener(this);
        galleryButton.setOnClickListener(this);
        cameraButton.setOnClickListener(this);

        Bundle bdl = this.getIntent().getExtras();
        hash = bdl.getString("hash");
        apiService = APIClient.getClient().create(APIInterface.class);
        Call<ListMessage> call1 = apiService.doGetListMessage(hash, Integer.parseInt(bdl.getString("conv")));
        call1.enqueue(new Callback<ListMessage>() {
            @Override
            public void onResponse(Call<ListMessage> call, Response<ListMessage> response) {
                lm = response.body();
                messagesAdapter = new MessagesAdapter(lm);
                conversation.setAdapter(messagesAdapter);
                Log.i(CAT,lm.toString());
            }

            @Override
            public void onFailure(Call<ListMessage> call, Throwable t) {
                Toast.makeText(ConvActivity.this,"Can't get messages from API",Toast.LENGTH_SHORT).show();
            }
        });
        mProgressBar.setVisibility(View.GONE);
    }

    /*
    private void addMessageTextView(Message m) {
        conversationLayout = (LinearLayout) findViewById(R.id.conversation_rvLayoutMessages);
        TextView message = new TextView(ConvActivity.this);
        message.setText(m.contenu);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        message.setLayoutParams(params);
        conversationLayout.addView(message);
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK) return;

        if (requestCode == RESULT_LOAD_IMAGE && data != null) {
            Uri selectedImage = data.getData();
            ImageView image = new ImageView(ConvActivity.this);
            image.setImageURI(selectedImage);
            image.setAdjustViewBounds(true);

            conversationLayout.addView(image);
        }

        if (requestCode == RESULT_CAMERA_IMAGE) {
            File file = new File(photoPath);
            Uri imageUri = Uri.fromFile(file);
            ImageView imageCamera = new ImageView(ConvActivity.this);
            imageCamera.setImageURI(imageUri);
            imageCamera.setAdjustViewBounds(true);

            conversationLayout.addView(imageCamera);
        }
    }

    @Override
    public void onClick(View v) {
        Log.i(CAT, "click sur " + v.getId());
        switch (v.getId()) {
            case R.id.galleryButton:
                Log.i(CAT, "galerie bouton");
                Intent intentGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intentGallery, RESULT_LOAD_IMAGE);
                break;

            case R.id.cameraButton:
                Log.i(CAT, "camera bouton");
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    Log.i(CAT, "camera bouton");
                    File photo = null;
                    try {
                        photo = createImageFile();
                    } catch (IOException ex) {
                        Log.i(CAT, "IOException");
                    }

                    if (photo != null) {
                        Uri photoUri = FileProvider.getUriForFile(this, "com.example.chat2021.fileprovider", photo);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                        startActivityForResult(intent, RESULT_CAMERA_IMAGE);
                    }
                }
                break;

            case R.id.conversation_btnOK:
                Log.i(CAT, "ok bouton");
                Bundle bdl = this.getIntent().getExtras();
                hash = bdl.getString("hash");
                Call<Message> call1 = apiService.doSendMessage(hash, Integer.parseInt(bdl.getString("conv")), txtMsg.getText().toString());
                call1.enqueue(new Callback<Message>() {
                    @Override
                    public void onResponse(Call<Message> call, Response<Message> response) {
                        m = response.body();
                        lm.add(m);
                        messagesAdapter = new MessagesAdapter(lm);
                        conversation.setAdapter(messagesAdapter);
                        Log.i(CAT, m.toString());
                    }

                    @Override
                    public void onFailure(Call<Message> call, Throwable t) {
                    }
                });
            break;
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFile = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFile, ".jpg", storageDir);

        photoPath = image.getAbsolutePath();

        return image;
    }
}