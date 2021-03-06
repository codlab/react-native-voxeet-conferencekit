package com.voxeet.models;

import android.support.annotation.NonNull;

import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeArray;
import com.facebook.react.bridge.WritableNativeMap;
import com.voxeet.sdk.json.UserInfo;
import com.voxeet.sdk.models.User;
import com.voxeet.sdk.models.v1.SdkParticipant;

public final class ConferenceUserUtil {
    private ConferenceUserUtil() {

    }

    @NonNull
    public static WritableMap toMap(@NonNull User user) {
        UserInfo userInfo = user.getUserInfo();

        WritableMap map = new WritableNativeMap();
        map.putString("userId", user.getId());
        map.putString("conferenceStatus", user.getStatus().name());

        if (null != user.getUserInfo()) {
            map.putString("name", userInfo.getName());
            map.putString("externalId", userInfo.getExternalId());
            map.putString("avatarUrl", userInfo.getAvatarUrl());
        }

        return map;
    }

    public static WritableArray toMap(Iterable<User> conferenceUsers) {
        WritableNativeArray array = new WritableNativeArray();
        if(null != conferenceUsers) {
            for (User user: conferenceUsers) {
                array.pushMap(toMap(user));
            }
        }
        return array;
    }

    public static WritableMap toMap(SdkParticipant user) {
        WritableMap map = new WritableNativeMap();
        map.putString("userId", user.getUserId());
        map.putString("conferenceStatus", user.getStatus());

        if (null != user.getMetadata()) {
            map.putString("name", user.getMetadata().getExternalName());
            map.putString("externalId", user.getMetadata().getExternalId());
            map.putString("avatarUrl", user.getMetadata().getExternalPhotoUrl());
        }

        return map;
    }
}
