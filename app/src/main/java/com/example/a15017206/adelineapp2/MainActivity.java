package com.example.a15017206.adelineapp2;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;

import org.json.simple.parser.JSONParser;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;


import android.widget.SeekBar;

import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    String TAG = "";

    EditText etSearch1;
    Button btnSearch1;
    ListView customlv1;
    ArrayList<SearchResult> searchResult = new ArrayList<>();
    ArrayAdapter aa;
    ImageView iv2;
    SeekBar seekBar;
    TextView tv_seekbar_helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etSearch1 = findViewById(R.id.et_Search1);
        btnSearch1 = findViewById(R.id.btnSearch);
        customlv1 = findViewById(R.id.customlv1);
        seekBar = findViewById(R.id.sbNoOfEntries);
        tv_seekbar_helper = findViewById(R.id.tv_seekbarhelper);

        seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
tv_seekbar_helper.setText("Change to: " + i + " products");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
btnSearch1.performClick();
            }
        });


        btnSearch1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchResult.clear();

                // These're fake data
//        searchResult.add(new SearchResult(R.drawable.star, "Title1", "Subtitle1", "$150.00", "Yes"));
//        searchResult.add(new SearchResult(R.drawable.nostar, "Title2", "Subtitle2", "$250.00", "No"));

                String appName = "?SECURITY-APPNAME=" + "GlennYeo-Software-PRD-35d705b3d-b15b0374";
                String operationName = "&OPERATION-NAME=" + "findItemsByKeywords";
                String serviceVersion = "&SERVICE-VERSION=" + "1.0.0";
                String responseDataFormat = "&RESPONSE-DATA-FORMAT=" + "JSON";
//        String callBack = "&callback=" + "_cb_findItemsByKeywords";
                String callBack = "";
                String keywords = "&keywords=" + etSearch1.getText().toString();
                String paginationInputentriesPerPage = "&paginationInput.entriesPerPage=" + "13";
                String paginationInputpageNumber = "&paginationInput.pageNumber=" + "1";
                String globalid = "&GLOBAL-ID=" + "EBAY-US";

                String totalParams = appName + operationName + serviceVersion + responseDataFormat + callBack + keywords + paginationInputentriesPerPage + paginationInputpageNumber + globalid;

                //Some url endpoint that you may have
                String myUrl = "https://svcs.ebay.com/services/search/FindingService/v1" + totalParams;

                //String to place our result in
                String result = "";

                //Instantiate new instance of our class
                HttpGetRequest getRequest = new HttpGetRequest();

                //Perform the doInBackground method, passing in our url
                try {
                    result = getRequest.execute(myUrl).get();
                    Log.i(TAG, "result is: " + result);

                    SearchResult searchResult2 = new SearchResult();

                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray array_findItemsByKeywordsResponse = (JSONArray) jsonObject.get("findItemsByKeywordsResponse");

                    for (int i = 0; i < array_findItemsByKeywordsResponse.length(); i++) {
                        JSONObject jsonObject1 = array_findItemsByKeywordsResponse.getJSONObject(i);
                        Log.i(TAG, "inside findItemsBy: " + jsonObject1);

                        JSONArray array_searchResult = (JSONArray) jsonObject1.get("searchResult");

                        for (int j = 0; j < array_searchResult.length(); j++) {
                            JSONObject jsonObject2 = array_searchResult.getJSONObject(j);
                            Log.i(TAG, "inside searchResult: " + jsonObject2);


                            JSONArray array_item = (JSONArray) jsonObject2.get("item");

                            for (int k = 0; k < array_item.length(); k++) {
                                JSONObject jsonObject3 = array_item.getJSONObject(k);
                                Log.i(TAG, "inside item: " + jsonObject3);

                                // Retrieve & Set text for Title
                                JSONArray array_title = (JSONArray) jsonObject3.get("title");

                                for (int l = 0; l < array_title.length(); l++) {
                                    String x = array_title.getString(i);
                                    searchResult2.setTvTitle(x);
                                    Log.i(TAG, "title is: " + x);
                                }

                                // SOME ENTRIES HAVE NO SUBTITLE- CHECK IF SUBTITLE KEY EXISTS!
                                if (jsonObject3.has("subtitle")) {
                                    // Retrieve & Set text for Subtitle
                                    JSONArray array_subtitle = (JSONArray) jsonObject3.get("subtitle");
                                    for (int l = 0; l < array_subtitle.length(); l++) {
                                        String x = array_subtitle.getString(i);
                                        searchResult2.setTvSubtitle(x);
                                    }
                                }


                                JSONArray array_sellingStatus = (JSONArray) jsonObject3.get("sellingStatus");
                                for (int l = 0; l < array_sellingStatus.length(); l++) {

                                    JSONObject jsonObject4 = array_sellingStatus.getJSONObject(l);
                                    JSONArray array_currentPrice = (JSONArray) jsonObject4.get("currentPrice");

                                    // Set text for currency + value
                                    for (int m = 0; m < array_currentPrice.length(); m++) {
                                        JSONObject jsonObject5 = array_currentPrice.getJSONObject(m);
                                        String currencyID = jsonObject5.getString("@currencyId");
                                        String __value__ = jsonObject5.getString("__value__");

                                        searchResult2.setTvPrice(currencyID + " " + __value__);
                                    }
                                }

                                JSONArray array_galleryURL = (JSONArray) jsonObject3.get("galleryURL");
                                for (int l = 0; l < array_galleryURL.length(); l++) {

                                    String galleryURL = array_galleryURL.getString(l);
                                    Log.i(TAG, "inside galleryURL: " + galleryURL);
                                    searchResult2.setImageView(galleryURL);
                                }


                                searchResult.add(searchResult2);

                            }
                        }

                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }

                aa = new SearchResultAdapter(MainActivity.this, R.layout.search_result_layout_row, searchResult);
                customlv1.setAdapter(aa);
            }
        });


    }

    public class HttpGetRequest extends AsyncTask<String, Void, String> {

        public static final String REQUEST_METHOD = "GET";
        public static final int READ_TIMEOUT = 15000;
        public static final int CONNECTION_TIMEOUT = 15000;

        @Override
        protected String doInBackground(String... params) {
            String stringUrl = params[0];
            String result;
            String inputLine;

            try {
                //Create a URL object holding our url
                URL myUrl = new URL(stringUrl);

                //Create a connection
                HttpURLConnection connection = (HttpURLConnection) myUrl.openConnection();

                //Set methods and timeouts
                connection.setRequestMethod(REQUEST_METHOD);
                connection.setReadTimeout(READ_TIMEOUT);
                connection.setConnectTimeout(CONNECTION_TIMEOUT);

                //Connect to our url
                connection.connect();

                //Create a new InputStreamReader
                InputStreamReader streamReader = new InputStreamReader(connection.getInputStream());

                //Create a new buffered reader and String Builder
                BufferedReader reader = new BufferedReader(streamReader);
                StringBuilder stringBuilder = new StringBuilder();

                //Check if the line we are reading is not null
                while ((inputLine = reader.readLine()) != null) {
                    stringBuilder.append(inputLine);
                }

                //Close our InputStream and Buffered reader
                reader.close();
                streamReader.close();

                //Set our result equal to our stringBuilder
                result = stringBuilder.toString();
            } catch (Exception e) {
                e.printStackTrace();
                result = "";
            }

            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
        }
    }
}