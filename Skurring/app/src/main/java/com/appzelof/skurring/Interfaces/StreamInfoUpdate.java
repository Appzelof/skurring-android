package com.appzelof.skurring.Interfaces;

import com.appzelof.skurring.model.PotentialMetadataJSONObject;


public interface StreamInfoUpdate {
    void getInfo(String album, String artist, PotentialMetadataJSONObject potentialMetadataJSONObject);
}
