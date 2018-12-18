package com.imam.pi.skripsi.siaterapps;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Register extends AppCompatActivity {
   private Spinner spinner,spinner2;
    /**  private String[] germanFeminine = {
     "S1-AKUNTANSI",
     "S1-MANAJEMEN",
     "S1-ILMU KOMUNIKASI",
     "S1-ADMINISTRASI BISNIS",
     "S1-ADMINISTRASI PUBLIK",
     "S1-HUKUM",
     "S1-ARSITEKTUR",
     "S1-TEKNIK SIPIL",
     "S1-TEKNIK MESIN",
     "S1-SISTEM INFORMASI",
     "S1-INFORMATIKA",
     "S2-HUKUM",
     "S2-TEKNIK SIPIL",
     "S2-ILMU ADMINISTRASI",
     "S2-MANAJEMEN",
     "S1-PENDIDIKAN BAHASA INGGRIS"
     };

     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        addItemsOnSpinner1();
        // Get reference of widgets from XML layout
         spinner2 = (Spinner) findViewById(R.id.sp_prodi);
    //    spinner.setPrompt(" Program Studi");
        // Initializing a String Array
        String[] plants = new String[]{
                "---",
                "S1-AKUNTANSI",
                "S1-MANAJEMEN",
                "S1-ILMU KOMUNIKASI",
                "S1-ADMINISTRASI BISNIS",
                "S1-ADMINISTRASI PUBLIK",
                "S1-HUKUM",
                "S1-ARSITEKTUR",
                "S1-TEKNIK SIPIL",
                "S1-TEKNIK MESIN",
                "S1-SISTEM INFORMASI",
                "S1-INFORMATIKA",
                "S2-HUKUM",
                "S2-TEKNIK SIPIL",
                "S2-ILMU ADMINISTRASI",
                "S2-MANAJEMEN",
                "S1-PENDIDIKAN BAHASA INGGRIS"
        };

        final List<String> plantsList = new ArrayList<>(Arrays.asList(plants));

        // Initializing an ArrayAdapter
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this,R.layout.spinner_item,plantsList){
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position%2 == 1) {
                    // Set the item background color
                    tv.setBackgroundColor(Color.parseColor("#47d3ec"));
                }
                else {
                    // Set the alternate item background color
                    tv.setBackgroundColor(Color.parseColor("#72e6fb"));
                }
                return view;
            }
        };
      //  spinner.setPrompt(" Program Studi");
        /*
            public void setPrompt (CharSequence prompt)
                Sets the prompt to display when the dialog is shown.
         */


        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinner2.setAdapter(spinnerArrayAdapter);

   /**     spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // memunculkan toast + value Spinner yang dipilih (diambil dari adapter)
                Toast.makeText(Register.this, "Selected "+ spinnerArrayAdapter.getItem(i), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
*/


    }


    public void addItemsOnSpinner1() {

        spinner = (Spinner) findViewById(R.id.sp_tahun);
        List<String> list = new ArrayList<String>();
        list.add("20181");
        list.add("20171");
        list.add("20161");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                R.layout.spinner_item, list){
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position%2 == 1) {
                    // Set the item background color
                    tv.setBackgroundColor(Color.parseColor("#47d3ec"));
                }
                else {
                    // Set the alternate item background color
                    tv.setBackgroundColor(Color.parseColor("#72e6fb"));
                }
                return view;
            }
        };
     //   spinner.setPrompt(" Tahun Aktif");
        /*
            public void setPrompt (CharSequence prompt)
                Sets the prompt to display when the dialog is shown.
         */


        dataAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(dataAdapter);




    }



}