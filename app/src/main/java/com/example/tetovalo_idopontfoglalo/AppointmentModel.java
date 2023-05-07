package com.example.tetovalo_idopontfoglalo;

public class AppointmentModel {
    private String description;
    private String appointment;
    private String email;
    private int imagineResource;
    private String id;


    public AppointmentModel(){

    }

    public AppointmentModel(String email, String appointment, String description){

        this.description = description;
        this.appointment = appointment;
        this.email = email;
        this.imagineResource = 0;
    }

    public AppointmentModel(String email, String appointment, String description, int imagineResource){
        this.description = description;
        this.appointment = appointment;
        this.email = email;
        this.imagineResource = imagineResource;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAppointment() {
        return appointment;
    }

    public void setAppointment(String appointment) {
        this.appointment = appointment;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getImagineResource() {
        return imagineResource;
    }

    public String _getId() {
        return id;
    }

    public void _setId(String id) {
        this.id = id;
    }
}
