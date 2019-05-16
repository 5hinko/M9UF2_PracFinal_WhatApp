package com.example.m9_uf2_05pracfinal;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.m9_uf2_05pracfinal.Model.Mensaje;

import java.util.List;

public class FicheroItemList extends RecyclerView.Adapter<FicheroItemList.WordViewHolder> {

    private final List<String> mWordList;
    private final LayoutInflater mInflater;

    class WordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final TextView txtNomFichero;
        final FicheroItemList mAdapter;

        public WordViewHolder(View itemView, FicheroItemList adapter) {
            super(itemView);
            txtNomFichero = itemView.findViewById(R.id.txtNomFichero);
            this.mAdapter = adapter;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int mPosition = getLayoutPosition();

            String element = mWordList.get(mPosition);

            Context context = view.getContext();

            Intent intent = new Intent(context, BajarFotoActivity.class);
            intent.putExtra("element", element);
            context.startActivity(intent);
            //new BajarFotoActivity(element);
        }
    }

    public FicheroItemList(Context context, List<String> hitorialList) {

        mInflater = LayoutInflater.from(context);
        this.mWordList = hitorialList;
    }

    @Override
    public FicheroItemList.WordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate an item view.
        View mItemView = mInflater.inflate(
                R.layout.activity_fichero_item_list, parent, false);
        return new WordViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(FicheroItemList.WordViewHolder holder, int position) {
        // Retrieve the data for that position.
        String mCurrent = mWordList.get(position);
        // Add the data to the view holder.

        holder.txtNomFichero.setText(mCurrent);

    }

    @Override
    public int getItemCount() {
        if (mWordList != null) {
            return mWordList.size();
        } else {
            return 0;
        }
    }
}
