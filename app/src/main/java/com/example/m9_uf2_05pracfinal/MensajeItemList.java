package com.example.m9_uf2_05pracfinal;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.m9_uf2_05pracfinal.Model.Mensaje;

import java.util.List;

public class MensajeItemList extends RecyclerView.Adapter<MensajeItemList.WordViewHolder> {

    private final List<Mensaje> mWordList;
    private final LayoutInflater mInflater;

    class WordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final TextView txtOrigen;
        public final TextView txtContingut;
        public final TextView txtLlegit;
        final MensajeItemList mAdapter;

        public WordViewHolder(View itemView, MensajeItemList adapter) {
            super(itemView);
            txtOrigen = itemView.findViewById(R.id.txt_origen);
            txtContingut = itemView.findViewById(R.id.txt_contingut);
            txtLlegit = itemView.findViewById(R.id.txt_leido);
            this.mAdapter = adapter;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int mPosition = getLayoutPosition();

            Mensaje element = mWordList.get(mPosition);

            element.isRead();

            if(element.getRead()){
                txtLlegit.setText("Leido");
                txtLlegit.setTextColor(Color.GRAY);
            }else{
                txtLlegit.setText("No Leido");
                txtLlegit.setTextColor(Color.BLACK);
            }

        }
    }

    public MensajeItemList(Context context, List<Mensaje> hitorialList) {

        mInflater = LayoutInflater.from(context);
        this.mWordList = hitorialList;
    }

    @Override
    public MensajeItemList.WordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate an item view.
        View mItemView = mInflater.inflate(
                R.layout.activity_mensaje_item_list, parent, false);
        return new WordViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(MensajeItemList.WordViewHolder holder, int position) {
        // Retrieve the data for that position.
        Mensaje mCurrent = mWordList.get(position);
        // Add the data to the view holder.

        holder.txtOrigen.setText(mCurrent.getOrigen_Des()+"");
        holder.txtContingut.setText(mCurrent.getContingut());

        if(mCurrent.getRead()){
            holder.txtLlegit.setText("Leido");
            holder.txtLlegit.setTextColor(Color.GRAY);
        }else{
            holder.txtLlegit.setText("No Leido");
            holder.txtLlegit.setTextColor(Color.BLACK);
        }

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
