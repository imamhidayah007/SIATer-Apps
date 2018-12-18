package com.imam.pi.skripsi.siaterapps;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.imam.pi.skripsi.siaterapps.server.AppController;
import com.imam.pi.skripsi.siaterapps.server.Config_URL;
import com.imam.pi.skripsi.siaterapps.session.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();

    private ProgressDialog pDialog;
    private SessionManager session;
    SharedPreferences prefs;

    int socketTimeout = 30000;
    RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);



    @BindView(R.id.l_npm)
    EditText edNPM;

    @BindView(R.id.l_password)
    EditText edPassword;


    String id;
   String tahunAktif;
    String programStudi;
    String npm;
    String email;
    String telepon;
    String angkatan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        ButterKnife.bind(this);
        prefs = getSharedPreferences("UserDetails",
                Context.MODE_PRIVATE);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // Session manager
        session = new SessionManager(getApplicationContext());

        id  = prefs.getString("id","");
        tahunAktif  = prefs.getString("tahunAktif","");
        programStudi  = prefs.getString("programStudi", "");
        npm  = prefs.getString("npm", "");
        email  = prefs.getString("email","");
        telepon  = prefs.getString("telepon", "");
        angkatan  = prefs.getString("angkatan", "");
        if(session.isLoggedIn()){
            Intent a = new Intent(getApplicationContext(), MenuUtama.class);
            a.putExtra("id", id);
            a.putExtra("tahunAktif", tahunAktif);
            a.putExtra("programStudi", programStudi);
            a.putExtra("npm", npm);
            a.putExtra("email", email);
            a.putExtra("telepon", telepon);
            a.putExtra("angkatan", angkatan);
            startActivity(a);
            finish();
        }

    }



    @OnClick(R.id.txt_register)
    void daftar(){

        Intent intent = new Intent(LoginActivity.this, Register.class);
        LoginActivity.this.startActivity(intent);
        // finish();
    }

    @OnClick(R.id.btn_login)
    void login(){
        String NPM = edNPM.getText().toString();
        String Pass  = edPassword.getText().toString();

        if (NPM.isEmpty() || Pass.isEmpty()){
            Toast.makeText(getApplicationContext(), "Data Harus Lengkap", Toast.LENGTH_SHORT).show();
        }else{
            checkLogin(NPM,Pass);
        }


    }

    @OnClick(R.id.txt_bantuan)
    void bantuan(){

        bantuan_login.showAbout(LoginActivity.this);
        // finish();
    }



    public void checkLogin(final String Npm, final String password){

        //Tag used to cancel the request
        String tag_string_req = "req_login";

        pDialog.setMessage("Login, Please Wait.....");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                Config_URL.loginUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    JSONObject result = jObj.getJSONObject("results");

                    boolean status = result.getBoolean("status");

                    if(status == true){

                        JSONObject user     = result.getJSONObject("user");
                        id           = user.getString("id");
                        tahunAktif        = user.getString("tahun_aktif");
                        programStudi    = user.getString("program_studi");
                        npm     = user.getString("npm");
                        email        = user.getString("email");
                        telepon    = user.getString("telepon");
                        angkatan     = user.getString("angkatan_s2");
                        String msg          = result.getString("msg");

                        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();

                        session.setLogin(true);
                        storeRegIdinSharedPref(getApplicationContext(),id, tahunAktif, programStudi, Npm, email, telepon, angkatan);
                        Intent a = new Intent(getApplicationContext(), MenuUtama.class);
                        a.putExtra("id", id);
                        a.putExtra("tahunAktif", tahunAktif);
                        a.putExtra("programStudi", programStudi);
                        a.putExtra("npm", Npm);
                        a.putExtra("email", email);
                        a.putExtra("telepon", telepon);
                        a.putExtra("angkatan", angkatan);
                        startActivity(a);
                        finish();
                        //Create login Session

                    }else {
                        String error_msg = result.getString("msg");
                        Toast.makeText(getApplicationContext(), error_msg, Toast.LENGTH_LONG).show();

                    }

                }catch (JSONException e){
                    //JSON error
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error){
                Log.e(TAG, "Login Error : " + error.getMessage());
                error.printStackTrace();
                hideDialog();
            }
        }){

            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
                params.put("npm", Npm);
                params.put("password", password);
                return params;
            }
        };

        strReq.setRetryPolicy(policy);
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    private void storeRegIdinSharedPref(Context context,String iduser,String tahun_aktif, String program_studi, String npm, String email, String telepon, String angkatan_s2) {

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("id", iduser);
        editor.putString("tahunAktif", tahun_aktif);
        editor.putString("programStudi", program_studi);
        editor.putString("npm", npm);
        editor.putString("email", email);
        editor.putString("telepon", telepon);
        editor.putString("angkatan", angkatan_s2);
        editor.commit();
    }


}
