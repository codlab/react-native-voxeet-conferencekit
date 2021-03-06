package com.voxeet.rn.manifests;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.voxeet.sdk.VoxeetEnvironmentHolder;
import com.voxeet.sdk.manifests.AbstractManifestComponentProvider;
import com.voxeet.sdk.preferences.VoxeetPreferences;
import com.voxeet.specifics.RNRootViewProvider;
import com.voxeet.toolkit.controllers.VoxeetToolkit;
import com.voxeet.toolkit.implementation.overlays.OverlayState;

import org.greenrobot.eventbus.EventBus;

public final class RNVoxeetManifestComponent extends AbstractManifestComponentProvider {

    /**
     * Static instance of the root view provider to be used by the app's voxeet instance
     */
    public static RNRootViewProvider root_view_provider;

    private static final String TAG = RNVoxeetManifestComponent.class.getSimpleName();

    @Override
    protected void init(@NonNull Context context) {
        if(!(context instanceof Application)) {
            Log.d(TAG, "init: ISSUE CONTEXT IS NOT AN APPPPPPLLIIICCAAATTIIIOOONNNN");
            return;
        }

        Application application = (Application) context;
        VoxeetToolkit.initialize(application, EventBus.getDefault());

        RNVoxeetManifestComponent.root_view_provider = new RNRootViewProvider(application, VoxeetToolkit.getInstance());
        VoxeetToolkit.instance().setProvider(RNVoxeetManifestComponent.root_view_provider);

        VoxeetToolkit.instance().enableOverlay(true);

        //force a default voxeet preferences manager
        //in sdk mode, no issues
        VoxeetPreferences.init(application, new VoxeetEnvironmentHolder(application));

        //change the overlay used by default
        VoxeetToolkit.instance().getConferenceToolkit().setDefaultOverlayState(OverlayState.EXPANDED);
        VoxeetToolkit.instance().getReplayMessageToolkit().setDefaultOverlayState(OverlayState.EXPANDED);

    }

    @Override
    protected String getComponentName() {
        return RNVoxeetManifestComponent.class.getSimpleName();
    }

    @Override
    protected String getDefaultAuthority() {
        return "com.voxeet.rn.manifests.";
    }
}
