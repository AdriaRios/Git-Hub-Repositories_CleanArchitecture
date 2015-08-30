package githubrepos.adriarios.org.githubrepos.data.net;

import android.os.Handler;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.inject.Inject;

import githubrepos.adriarios.org.githubrepos.dispatcher.Dispatcher;
import githubrepos.adriarios.org.githubrepos.dispatcher.events.DataServiceEvent;
import githubrepos.adriarios.org.githubrepos.dispatcher.events.NetworkErrorEvent;
import githubrepos.adriarios.org.githubrepos.presentation.di.App;

/**
 * Created by Adrian on 13/08/2015.
 */
public class RestAPI {
    @Inject
    Dispatcher dispatcher;
    App application;
    public RestAPI (App application){
        this.application = application;
        application.getObjectGraph().inject(this);
    }

    public void getNewRepos(final int pageNum, final int pagePerCall) {
        final Handler mainHandler = new Handler(application.getBaseContext().getMainLooper());
        new Thread(new Runnable() {

            @Override
            public void run() {
                HttpURLConnection httpUrlConnection = null;

                try {
                    final String webserviceResponse = getServiceResponse("https://api.github.com/users/AdriaRios/repos?page="+pageNum+"&per_page="+pagePerCall+"");
                    mainHandler.post(new Runnable() {

                        @Override
                        public void run() {
                            dispatcher.dispatch(new DataServiceEvent(webserviceResponse, false));

                        }
                    });

                } catch (MalformedURLException e) {
                    launckNetworkError();
                    Log.e("WEBSERVICE", "MalformedURLException");

                } catch (IOException e) {
                    launckNetworkError();

                }  finally {
                    if (httpUrlConnection != null)
                        httpUrlConnection.disconnect();
                }
            }
        }).start(); // Executes the newly created thread

    }

    /**
     * Gets info from webservice
     *
     * @param url
     *            URL to do the Request
     *
     * @return Webservice response
     */

    private String getServiceResponse(String url) throws IOException {
        HttpURLConnection httpUrlConnection = null;

        try {
            httpUrlConnection = (HttpURLConnection) new URL(url)
                    .openConnection();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            launckNetworkError();
            e.printStackTrace();
        }

        // Get a reference to the web service's InputStream
        InputStream in = httpUrlConnection.getInputStream();

        // Read the raw data from the InputStream
        final String webserviceResponse = readFromInputStream(in);
        return webserviceResponse;
    }

    /**
     * Reads raw data from an InputStream.
     *
     * @param in
     *            The InputStream to read from.
     *
     * @return A String representation of the InputStream's data
     */
    private String readFromInputStream(InputStream in) {

        BufferedReader bufferedReader = null;
        StringBuilder responseBuilder = new StringBuilder();

        try {

            bufferedReader = new BufferedReader(new InputStreamReader(in));
            String line = "";

            while ((line = bufferedReader.readLine()) != null) {
                responseBuilder.append(line);
            }

        } catch (IOException e) {
            launckNetworkError();
            Log.e("WEBSERVICE", "IOException");
        } finally {

            if (bufferedReader != null) {

                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    launckNetworkError();
                    Log.e("WEBSERVICE", "IOException");
                }
            }
        }

        return responseBuilder.toString();
    }

    private void launckNetworkError() {
        final Handler mainHandler = new Handler(application.getBaseContext().getMainLooper());
        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                dispatcher.dispatch(new NetworkErrorEvent());

            }
        });
        Log.e("WEBSERVICE", "IOException");
    }

}

