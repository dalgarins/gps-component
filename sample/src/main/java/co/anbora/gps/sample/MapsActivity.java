package co.anbora.gps.sample;

import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import co.anbora.component.location.CallbackLocation;
import co.anbora.component.location.LocationAccuracy;
import co.anbora.component.location.LocationComponent;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LocationComponent gpsComponent;

    private final int FIVE_SECONDS = 5000;

    private CallbackLocation callbackLocation = new LastLocationCallback();
    private CallbackLocation updateLocation = new LocationUpdateCallback();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        gpsComponent = new LocationComponent.Builder()
                .locationRequest(FIVE_SECONDS, FIVE_SECONDS, LocationAccuracy.HIGH)
                .build(this);

        gpsComponent
                .lastLocation(callbackLocation)
                .whenLocationChange(updateLocation)
                .attachState()
                .observe(getLifecycle());
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    private class LastLocationCallback implements CallbackLocation {

        @Override
        public void onLocationResult(Location location) {
            showMarkerInMap(location);
        }

        @Override
        public void onLocationError() {

        }

        private void showMarkerInMap(Location location) {
            if (mMap != null) {
                // Add a marker in Sydney and move the camera
                LatLng sydney = new LatLng(location.getLatitude(), location.getLongitude());
                mMap.addMarker(new MarkerOptions().position(sydney).title("Last Location"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
            }
        }
    }

    public class LocationUpdateCallback implements CallbackLocation {

        @Override
        public void onLocationResult(Location location) {
            Toast.makeText(getApplicationContext(),
                    "Updated Location: " + location.getLatitude() + ", " + location.getLongitude()
                    , Toast.LENGTH_LONG).show();
        }

        @Override
        public void onLocationError() {

        }
    }

}
