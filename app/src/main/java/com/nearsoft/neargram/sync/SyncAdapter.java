package com.nearsoft.neargram.sync;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;

import com.nearsoft.neargram.NeargramApplication;
import com.nearsoft.neargram.model.PhotoModel;
import com.nearsoft.neargram.webservices.InstagramService;
import com.nearsoft.neargram.webservices.responses.InstagramSearchResponse;

import java.io.IOException;

import javax.inject.Inject;

import retrofit.Response;

/**
 * Sync adapter.
 * Created by epool on 11/27/15.
 */
public class SyncAdapter extends AbstractThreadedSyncAdapter {
    @Inject
    protected InstagramService instagramService;

    public SyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        instagramService = NeargramApplication.getApplicationComponent().provideInstagramService();
    }

    public SyncAdapter(Context context, boolean autoInitialize, boolean allowParallelSyncs) {
        super(context, autoInitialize, allowParallelSyncs);
        instagramService = NeargramApplication.getApplicationComponent().provideInstagramService();
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
        try {
            Response<InstagramSearchResponse> response = instagramService.getPopularPhotos(29.0974411, -111.0220760, 5000).execute();
            if (response.isSuccess()) {
                PhotoModel.clearPhotos();
                InstagramSearchResponse instagramSearchResponse = response.body();
                PhotoModel.savePhotos(instagramSearchResponse.getData());
            }
        } catch (IOException e) {
            e.printStackTrace();
            syncResult.stats.numIoExceptions += 1;
        }
    }

}
