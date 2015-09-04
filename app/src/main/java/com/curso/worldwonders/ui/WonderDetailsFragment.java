package com.curso.worldwonders.ui;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.curso.worldwonders.R;
import com.curso.worldwonders.entity.Wonder;
import com.curso.worldwonders.infrastructure.Constants;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

/**
 * Created by Junior on 03/09/2015.
 */
public class WonderDetailsFragment extends Fragment {

    private Activity hostActivity;
    private View fragmentView;

    private ImageView wonderImage;
    private TextView wonderName;
    private TextView wonderDescription;
    private TextView noneSelectedMessage;
    private View controlsGroup;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.fragment_wonder_details, container, false);
        loadUi();
        return fragmentView;
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        hostActivity = activity;
    }

    @Override
    public void onStart() {
        super.onStart();
        loadData();
    }

    @Override
    public void onStop() {
        super.onStop();
        getLoaderManager().destroyLoader(0);
    }

    private void loadUi()
    {
        controlsGroup = fragmentView.findViewById(R.id.wonder_details_layout_main);
        wonderImage = (ImageView) fragmentView.findViewById(R.id.wonder_details_image);
        wonderName = (TextView) fragmentView.findViewById(R.id.wonder_details_name);
        wonderDescription = (TextView) fragmentView.findViewById(R.id.wonder_details_description);
        noneSelectedMessage = (TextView) fragmentView.findViewById(R.id.wonder_details_none_selected_message);
    }

    public static WonderDetailsFragment newInstance(Wonder wonder) {
        WonderDetailsFragment wonderDetailsFragment = new WonderDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable(Constants.IntentConsts.EXTRA_WONDER, wonder);
        wonderDetailsFragment.setArguments(args);

        return wonderDetailsFragment;
    }

    private void loadData(){
        // primeiro, checar se o dado esta no Intent (estamos na
        // WonderDetailsActivity)
        Wonder selectedWonder = (Wonder) hostActivity.getIntent().getSerializableExtra(Constants.IntentConsts.EXTRA_WONDER);
        if (selectedWonder == null) {
            // Se nao encontramos no Intent, entao o dado deve estar nos
            // argumentos do Fragment (estamos na MainActivity)
            Bundle args = getArguments();
            if (args != null) {
                selectedWonder = (Wonder) args.getSerializable(Constants.IntentConsts.EXTRA_WONDER);
            }
        }

        if (selectedWonder == null){
            noneSelectedMessage.setVisibility(View.VISIBLE);
            controlsGroup.setVisibility(View.GONE);
        }
        else{
            final Wonder wonder = selectedWonder;
            Picasso.with(hostActivity).load(wonder.image_url).into(wonderImage, new Callback() {
                @Override
                public void onSuccess() {
                    //progress.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onError() {
                    Toast.makeText(hostActivity, "Erro ao buscar a imagem de " + wonder.name, Toast.LENGTH_SHORT).show();
                }
            });

            wonderName.setText(wonder.name);
            wonderDescription.setText(wonder.description);

            noneSelectedMessage.setVisibility(View.GONE);
            controlsGroup.setVisibility(View.VISIBLE);
        }

    }

}
