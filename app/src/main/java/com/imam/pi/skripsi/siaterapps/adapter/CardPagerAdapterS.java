package com.imam.pi.skripsi.siaterapps.adapter;

/**
 * Created by KottlandPro TET on 3/3/2018.
 */


import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.imam.pi.skripsi.siaterapps.LoginActivity;
import com.imam.pi.skripsi.siaterapps.MenuUtama;
import com.imam.pi.skripsi.siaterapps.R;
import com.imam.pi.skripsi.siaterapps.Register;
import com.imam.pi.skripsi.siaterapps.Webview;

import java.util.ArrayList;
import java.util.List;

// public class CardPagerAdapterS {


public class CardPagerAdapterS extends PagerAdapter implements CardAdapter {

    private List<CardView> mViews = new ArrayList<>();
    private List<CardItemString> mData;
    private float mBaseElevation;

    Context con;

    public CardPagerAdapterS(Context con) {
        mData = new ArrayList<>();
        mViews = new ArrayList<>();
        this.con = con;
    }

    public void addCardItemS(CardItemString item) {
        mViews.add(null);
        mData.add(item);
    }



    public float getBaseElevation() {
        return mBaseElevation;
    }

    @Override
    public CardView getCardViewAt(int position) {
        return mViews.get(position);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = LayoutInflater.from(container.getContext())
                .inflate(R.layout.adapter, container, false);
        container.addView(view);
        bind(mData.get(position), view);
        CardView cardView = (CardView) view.findViewById(R.id.cardView);
        LinearLayout linearClik = (LinearLayout) view.findViewById(R.id.linearClick);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (position){
                    case 0:
                        String url = "http://ubl.ac.id/kalender-akademik/";
                    String titlekalender = "Kalender Akademik";
                        Intent intent = new Intent(con, Webview.class);
                        intent.putExtra("linkkalender", url);
                      intent.putExtra("titlekalender", titlekalender);
                        con.startActivity(intent);
                        break;
                    case 1:
                        String url2 = "http://ubl.ac.id/artikel-opini/";
                        String titleartikel = "Artikel Dan Opini";
                        Intent intent2 = new Intent(con, Webview.class);
                        intent2.putExtra("linkartikel", url2);
                        intent2.putExtra("titleartikel", titleartikel);
                        con.startActivity(intent2);
                        break;
                    case 2:
                        String url3 = "http://ubl.ac.id/news-article/";
                        String titleberita = "Berita Terbaru";
                        Intent intent3 = new Intent(con, Webview.class);
                        intent3.putExtra("linkberita", url3);
                        intent3.putExtra("titleberita", titleberita);
                        con.startActivity(intent3);
                        break;
                    case 3:
                        String url4 = "https://docs.google.com/forms/d/e/1FAIpQLSfv4A8z6zMFJqGr-4OLKyenwvXiAp8dY9ehVzTObZXKSM6frw/viewform?c=0&w=1";
                        String titlebeasiswa = "Pembaruan Data Mahasiswa";
                        Intent intent4 = new Intent(con, Webview.class);
                        intent4.putExtra("linkbeasiswa", url4);
                        intent4.putExtra("titlebeasiswa", titlebeasiswa);
                        con.startActivity(intent4);
                        break;


                }
         /**       if(position == 0){
                    Intent intent = new Intent(con, Register.class);
                    con.startActivity(intent);
                }else {
                    Toast.makeText(con, "Salah", Toast.LENGTH_SHORT).show();
                }
          **/
            }
        });

        if (mBaseElevation == 0) {
            mBaseElevation = cardView.getCardElevation();
        }

        cardView.setMaxCardElevation(mBaseElevation * MAX_ELEVATION_FACTOR);
        mViews.set(position, cardView);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        mViews.set(position, null);
    }

    private void bind(CardItemString item, View view) {
        TextView titleTextView = (TextView) view.findViewById(R.id.titleTextView);
        TextView contentTextView = (TextView) view.findViewById(R.id.contentTextView);
        titleTextView.setText(item.getTitle());
        contentTextView.setText(item.getText());
    }

}

