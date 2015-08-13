package com.curso.worldwonders.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.curso.worldwonders.R;
import com.curso.worldwonders.entity.Wonder;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

/**
 * Created by Junior on 13/08/2015.
 */
public class FeedCursorAdapter extends CursorAdapter {

    public FeedCursorAdapter(Context context, Cursor c) {
        super(context, c, false);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return inflater.inflate(R.layout.wonders_list_item, null);
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        final Wonder wonder = new Wonder(cursor);
        TextView lblName = (TextView)view.findViewById(R.id.wonder_item_name);
        TextView lblCountry = (TextView)view.findViewById(R.id.wonder_item_country);
        ImageView picWonder =(ImageView) view.findViewById(R.id.wonder_item_pic);
        ImageView picLike =(ImageView) view.findViewById(R.id.wonder_item_like);
        final ProgressBar progress = (ProgressBar) view.findViewById(R.id.wonder_item_progress);

        lblName.setText(wonder.name);
        lblCountry.setText(wonder.country);
        Picasso.with(context).load(wonder.image_url).into(picWonder, new Callback() {
            @Override
            public void onSuccess() {
                progress.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onError() {
                Toast.makeText(context,"Erro ao buscar a imagem de " + wonder.name ,Toast.LENGTH_SHORT).show();
            }
        });



        picLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"Curtiu " + wonder.name,Toast.LENGTH_SHORT).show();
            }
        });

    }
}
