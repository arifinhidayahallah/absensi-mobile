package com.example.vsgo_juniormobileprogrammer.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vsgo_juniormobileprogrammer.R;
import com.example.vsgo_juniormobileprogrammer.database.DBHandler;
import com.example.vsgo_juniormobileprogrammer.models.PresenceModel;
import com.google.android.material.button.MaterialButton;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PresenceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PresenceFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private String nim;

    public PresenceFragment(String nim) {
        // Required empty public constructor
        this.nim = nim;
    }
    public PresenceFragment(){

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PresenceFragment.
     */
    // TODO: Rename and change types and number of parameters


    public static PresenceFragment newInstance(String param1, String param2) {
        PresenceFragment fragment = new PresenceFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.presence_frame, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        TextView timeInHour = view.findViewById(R.id.timeInHour);
        TextView fulltime = view.findViewById(R.id.fullTime);

        Date date = new Date();

        SimpleDateFormat dateFormatHour = new SimpleDateFormat("HH:mm", Locale.forLanguageTag("id"));
        SimpleDateFormat dateFormatDay = new SimpleDateFormat("dd-MM-yyyy", Locale.forLanguageTag("id"));
        SimpleDateFormat dateFormatFull = new SimpleDateFormat("EEEE, dd MMMM yyyy", Locale.forLanguageTag("id"));
        timeInHour.setText(dateFormatHour.format(date));
        fulltime.setText(dateFormatFull.format(date));

        MaterialButton button = view.findViewById(R.id.presenceButton);
        button.setOnClickListener(v -> {
//            Date date = new Date();
//            SimpleDateFormat dateFormatHour = new SimpleDateFormat("hh:mm", Locale.getDefault());
//            SimpleDateFormat dateFormatDay = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
            String timeDutyHour = dateFormatHour.format(date.getTime());
            String timeDutyDay = dateFormatFull
                    .format(date.getTime());
            PresenceModel presenceModel = new PresenceModel(nim, timeDutyDay, timeDutyHour, timeDutyHour);
            Boolean isPresenceSuccess = new DBHandler(view.getContext()).onPresence(presenceModel);
            if(isPresenceSuccess){
                Toast.makeText(view.getContext(), "Absensi berhasil !", Toast.LENGTH_LONG).show();

            }else{
                Toast.makeText(view.getContext(), "Kamu sudah melakukan absensi !", Toast.LENGTH_LONG).show();
            }
        });
        super.onViewCreated(view, savedInstanceState);
    }
}