package com.example.tetovalo_idopontfoglalo;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.app.DatePickerDialog;
import android.widget.DatePicker;
import android.os.Bundle;
import android.view.View.OnClickListener;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class AppointmentListAdapter extends RecyclerView.Adapter<AppointmentListAdapter.ViewHolder> {
    private static final String LOG_TAG = AppointmentListAdapter.class.getName();
    private ArrayList<AppointmentModel> appointmentsList;
    private ArrayList<AppointmentModel> appointmentsListAll;
    private Context context;
    private int lastPosition =-1;

    public AppointmentListAdapter(Context context, ArrayList<AppointmentModel> appointmentsList){
        this.appointmentsList = appointmentsList;
        this.appointmentsListAll = appointmentsList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_appointment, parent,false));
    }

    @Override
    public void onBindViewHolder(AppointmentListAdapter.ViewHolder holder, int position) {
        AppointmentModel currentItem = appointmentsList.get(position);
        holder.bindTo(currentItem);
    }

    @Override
    public int getItemCount() {
        return appointmentsList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        private TextView descriptionTextView;
        private TextView dateTextView;
        private Button dateChange;
        private Button delete;

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String txtDate;
        public ViewHolder( View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.listImage);
            descriptionTextView = itemView.findViewById(R.id.textViewDescription);
            dateTextView = itemView.findViewById(R.id.textViewAppointment);


        }

        public void bindTo(AppointmentModel currentItem) {
            descriptionTextView.setText(currentItem.getDescription());
            dateTextView.setText(currentItem.getAppointment());
            if (currentItem.getImagineResource() != 0){
                Glide.with(context).load(currentItem.getImagineResource()).into(imageView);
            }
            Log.d(LOG_TAG, currentItem.getDescription() +", "+ currentItem.getAppointment());
            itemView.findViewById(R.id.buttonChangeAppointment).setOnClickListener(view -> {
                //TODO Gomnyomásra meg lehessen változtani a dátumot



                DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                txtDate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                                try {
                                    ((MyAppointment)context).updateItem(currentItem, txtDate);
                                }catch (Exception e){
                                    e.toString();
                                }

                            }
                        }, this.year, this.month, this.day);
                datePickerDialog.show();


            });
            itemView.findViewById(R.id.buttonDeleteAppointment).setOnClickListener(view -> {
                ((MyAppointment)context).deleteItem(currentItem);
            });
            //TODO Adott idöpont törlése
        }
    }
}


