package com.nearsoft.neargram;

import android.test.ApplicationTestCase;

import com.nearsoft.neargram.di.components.ApplicationComponent;
import com.nearsoft.neargram.webservices.InstagramService;
import com.nearsoft.neargram.webservices.responses.InstagramPopularResponse;

import java.io.IOException;

import retrofit.Response;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<NeargramApplication> {
    public ApplicationTest() {
        super(NeargramApplication.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        createApplication();
    }

    public void testInstagramRequest() throws IOException {
        NeargramApplication neargramApplication = getApplication();
        ApplicationComponent applicationComponent = neargramApplication.getApplicationComponent();
        InstagramService service = applicationComponent.provideInstagramService();

        Response<InstagramPopularResponse> response = service.getPopularPhotos("f7373613c193424ba4be7f85ec6e6b2c").execute();
        InstagramPopularResponse instagramPopularResponse = response.body();
        assertNotNull(instagramPopularResponse);
    }
}