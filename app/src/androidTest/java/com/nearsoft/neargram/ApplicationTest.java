package com.nearsoft.neargram;

import android.test.ApplicationTestCase;

import com.nearsoft.neargram.di.components.ApplicationComponent;
import com.nearsoft.neargram.webservices.InstagramService;
import com.nearsoft.neargram.webservices.responses.InstagramSearchResponse;

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

        Response<InstagramSearchResponse> response = service.getPopularPhotos(29.0974411, -111.0220760, 1000).execute();
        assertTrue(response.isSuccess());
        InstagramSearchResponse instagramSearchResponse = response.body();
        assertNotNull(instagramSearchResponse);
    }
}