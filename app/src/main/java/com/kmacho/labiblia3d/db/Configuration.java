package com.kmacho.labiblia3d.db;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by kmachoLaptop on 11/23/2017.
 */
@Entity
public class Configuration {
    public int getConfigurationId() {
        return configurationId;
    }

    public void setConfigurationId(int configurationId) {
        this.configurationId = configurationId;
    }

    @PrimaryKey
    public int configurationId;
    public Boolean getSpeech() {
        return speech;
    }

    public void setSpeech(Boolean speech) {
        this.speech = speech;
    }

    public Boolean speech;
}
