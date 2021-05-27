package com.example.chat2021;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChoixConvActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String CAT = "LE4-SI";
    APIInterface apiService;
    String hash;
    Spinner listeConv;
    ListConversation lc;
    Button btnOK;
    int idItemSelected;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choix_conversation);
        Bundle bdl = this.getIntent().getExtras();
        hash = bdl.getString("hash");
        listeConv = findViewById(R.id.choixConversation_choixConv);
        btnOK = findViewById(R.id.choixConversation_btnOK);
        btnOK.setOnClickListener(this);


        apiService = APIClient.getClient().create(APIInterface.class);
        Call<ListConversation> call1 = apiService.doGetListConversation(hash);
        call1.enqueue(new Callback<ListConversation>() {
            @Override
            public void onResponse(Call<ListConversation> call, Response<ListConversation> response) {
                lc = response.body();
                List<String> spinnerArray =  new ArrayList<String>();
                List<Integer> idArray = new ArrayList<Integer>();
                for(Conversation c : lc.conversations) {
                    spinnerArray.add(c.theme);
                    idArray.add(Integer.parseInt(c.id));
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(ChoixConvActivity.this, android.R.layout.simple_spinner_item, spinnerArray);
                listeConv.setAdapter(adapter);

                listeConv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View arg1,
                                               int arg2, long arg3) {
                        idItemSelected = idArray.get(arg2);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                        // TODO Auto-generated method stub
                    }
                });

                Log.i(CAT,lc.toString());
            }

            @Override
            public void onFailure(Call<ListConversation> call, Throwable t) {
                call.cancel();
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onClick(View v) {
        Intent change2Conv = new Intent(this,ConvActivity.class);
        Bundle bdl = new Bundle();
        // Conversation conv = (Conversation) listeConv.getSelectedItem();
        bdl.putString("conv", Integer.toString(idItemSelected));
        bdl.putString("hash", hash);
        change2Conv.putExtras(bdl);
        startActivity(change2Conv);
    }

    private void alerter(String s) {
        Log.i(CAT,s);
        Toast t = Toast.makeText(this,s,Toast.LENGTH_SHORT);
        t.show();
    }
}
