package com.example.apple.myweatherapp.model_weather_for_five_days;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


    public class Clouds {

        @SerializedName("all")
        @Expose
        private Integer all;

        public Integer getAll() {
            return all;
        }

        public void setAll(Integer all) {
            this.all = all;
        }

    }

