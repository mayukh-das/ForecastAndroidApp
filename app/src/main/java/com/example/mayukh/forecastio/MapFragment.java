package com.example.mayukh.forecastio;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.model.LatLng;
import com.hamweather.aeris.communication.AerisEngine;
import com.hamweather.aeris.maps.AerisMapView;
import com.hamweather.aeris.maps.AerisMapView.AerisMapType;
import com.hamweather.aeris.maps.MapViewFragment;
import com.hamweather.aeris.tiles.AerisTile;

/**
 * Created by Mayukh on 05-12-2015.
 */
public class MapFragment extends MapViewFragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Bundle args = getArguments();
        float latitude = args.getFloat("latitude");
        float longitude = args.getFloat("longitude");
        View view = inflater.inflate(R.layout.fragment_interactive_maps, container, false);
        AerisEngine.initWithKeys("aeris_key_1","aeris_key_2","com.example.mayukh.forecastio");
        mapView = (AerisMapView)view.findViewById(R.id.aerisfragment_map);
        mapView.init(savedInstanceState, AerisMapType.GOOGLE);
        mapView.moveToLocation(new LatLng(latitude, longitude), 9);
        mapView.addLayer(AerisTile.RADSAT);
        return view;
    }
}