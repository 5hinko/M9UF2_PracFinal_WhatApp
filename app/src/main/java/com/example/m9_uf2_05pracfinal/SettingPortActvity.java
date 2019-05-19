package com.example.m9_uf2_05pracfinal;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.m9_uf2_05pracfinal.Model.Conexion;
import com.example.m9_uf2_05pracfinal.Model.ConexionConfig;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SettingPortActvity.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SettingPortActvity#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingPortActvity extends DialogFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private EditText edtIP;
    private EditText edtPuerto;
    private Button btnAcept;
    private Button btnCancel;

    private OnFragmentInteractionListener mListener;

    public SettingPortActvity() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SettingPortActvity.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingPortActvity newInstance() {
        SettingPortActvity fragment = new SettingPortActvity();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting_port_actvity, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        edtIP = view.findViewById(R.id.edt_ip);
        edtPuerto = view.findViewById(R.id.edt_port);
        btnAcept = view.findViewById(R.id.btn_acept);
        btnCancel = view.findViewById(R.id.btn_cancel);

        String conn[] = ConexionConfig.getPortIP().split(" 和");
        try{
            String direccion = conn[0];
            edtIP.setText(direccion);
            int puerto = Integer.parseInt(conn[1]);
            edtPuerto.setText(puerto+"");
        }catch (Exception ex){
            Log.d("MainActivity", "Error al parse numero del puerto");
        }

        btnAcept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConexionConfig.setPortIP(edtIP.getText().toString().replace(" ", ""),Integer.parseInt(edtPuerto.getText().toString()));
                closeFragment();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeFragment();
            }
        });
    }

    private void closeFragment(){
        dismiss();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {/*
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");*/
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
