package com.example.jonmid.tareasasincronas;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jonmid.tareasasincronas.Adapters.CountryAdapter;
import com.example.jonmid.tareasasincronas.Models.Country;
import com.example.jonmid.tareasasincronas.Models.Post;
import com.example.jonmid.tareasasincronas.Parser.Json;
import com.example.jonmid.tareasasincronas.Parser.JsonCountry;
import com.example.jonmid.tareasasincronas.URL.HttpManger;
//import com.sun.corba.se.impl.oa.toa.TOA;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ProgressBar progressBar;
    Button button;

    List<Post> postList = new ArrayList<>();
    List<Country> countryList = new ArrayList<>();

    RecyclerView recyclerView;

    CountryAdapter countryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = (ProgressBar) findViewById(R.id.id_pb_data);
        button = (Button) findViewById(R.id.id_btn_loaddata);

        recyclerView = (RecyclerView) findViewById(R.id.id_rcv_data);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
    }
    //
    public void showPost(View view){
        //(Desde donde sale, donde voy)
        Intent intent = new Intent(MainActivity.this, PostActivity.class);
        startActivity(intent);
    }

    // Metodo para validar la conexion a internet
    public Boolean isOnLine(){
        // Hacer llamado al servicio de conectividad utilizando el ConnectivityManager
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        // Obtener el estado de la conexion a internet en el dispositivo
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        // Validar el estado obtenido de la conexion
        if (networkInfo != null){
            return true;
        }else {
            return false;
        }
    }

    // Evento del boton
    public void loadData(View view){
        if (isOnLine()){
            // Hacer llamado a la tarea
            //MyTask task = new MyTask();
            //task.execute("https://jsonplaceholder.typicode.com/posts");

            TaskCountry taskCountry = new TaskCountry();
            taskCountry.execute("http://services.groupkt.com/country/get/all");

        }else {
            Toast.makeText(this, "Sin conexion", Toast.LENGTH_SHORT).show();
        }
    }

    // *************************************************************************************

    public void processData(){
        countryAdapter = new CountryAdapter(countryList, getApplicationContext());
        recyclerView.setAdapter(countryAdapter);

    }

    public class MyTask extends AsyncTask<String, String, String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... strings) {
            /*for (int i = 1; i <= 50; i++){
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                publishProgress(String.valueOf(i));
            }
            return "fin";*/
            String content = null;

            try {
                content = HttpManger.getData(strings[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return content;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            processData();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                postList = Json.getDataJson(s);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            processData();
            progressBar.setVisibility(View.GONE);
        }
    }

    // *************************************************************************************

    public class TaskCountry extends AsyncTask<String,String,String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... strings) {
            String content = null;
            try {
                content = HttpManger.getData(strings[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return content;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                countryList = JsonCountry.getData(s);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            processData();
            progressBar.setVisibility(View.GONE);
        }
    }
}
