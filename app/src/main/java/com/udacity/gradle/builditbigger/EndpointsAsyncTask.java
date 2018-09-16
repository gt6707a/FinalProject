package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.os.AsyncTask;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

import java.io.IOException;

public class EndpointsAsyncTask extends AsyncTask<String, Void, String> {
  private static MyApi myApiService = null;
  private Context context;
  private JokeHandler jokeHandler;

  public interface JokeHandler {
    void onJokeReady(String joke);
  }

  public EndpointsAsyncTask(JokeHandler jokeHandler) {
    this.jokeHandler = jokeHandler;
  }

  @Override
  protected String doInBackground(String... params) {
    if (myApiService == null) { // Only do this once
      MyApi.Builder builder =
          new MyApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
              // options for running against local devappserver
              // - 10.0.2.2 is localhost's IP address in Android emulator
              // - turn off compression when running against local devappserver
              .setRootUrl("http://10.0.2.2:8080/_ah/api/")
              .setGoogleClientRequestInitializer(
                  new GoogleClientRequestInitializer() {
                    @Override
                    public void initialize(
                        AbstractGoogleClientRequest<?> abstractGoogleClientRequest)
                        throws IOException {
                      abstractGoogleClientRequest.setDisableGZipContent(true);
                    }
                  });
      // end options for devappserver

      myApiService = builder.build();
    }

    String name = params != null && params.length > 0 ? params[0] : "";

    try {
      return myApiService.sayHi(name).execute().getData();
    } catch (IOException e) {
      return e.getMessage();
    }
  }

  @Override
  protected void onPostExecute(String result) {
    jokeHandler.onJokeReady(result);
  }
}
