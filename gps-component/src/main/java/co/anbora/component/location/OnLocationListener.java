package co.anbora.component.location;


import androidx.annotation.NonNull;

/**
 * Created by dalgarins on 03/05/18.
 */

public interface OnLocationListener {

    OnLocationListener lastLocation(@NonNull CallbackLocation callback);

    OnLocationListener whenLocationChange(@NonNull CallbackLocation callback);

    OnObserveState attachState();

}
