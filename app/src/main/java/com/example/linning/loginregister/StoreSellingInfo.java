package com.example.linning.loginregister;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Raymond on 11/22/2015.
 */
public class StoreSellingInfo {

    private ProgressDialog progressDialog;
    private Context context;
    protected String baseURL;

    public StoreSellingInfo(Context theContext)
    {
        this.context = theContext;
        baseURL = this.context.getString(R.string.site_url) + "StoreSellingInfo.php";
        this.progressDialog = new ProgressDialog(theContext);
        this.progressDialog.setCancelable(false);
        this.progressDialog.setTitle("Processing...");
        this.progressDialog.setMessage("Please wait...");
    }

    public class StoreSellingInfoAsyncTask extends AsyncTask<String, Void, String>
    {
        @Override
        protected String doInBackground(String... params)
        {
            HttpClient hc = AndroidHttpClient.newInstance("Android");
            HttpPost hp = new HttpPost(baseURL);
            hp.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=UTF-8");
            List<NameValuePair> postParams = new ArrayList<NameValuePair>();
            postParams.add(new BasicNameValuePair("name", params[0]));
            postParams.add(new BasicNameValuePair("phone", params[1]));
            postParams.add(new BasicNameValuePair("rate", params[2]));
            postParams.add(new BasicNameValuePair("str_startDateTime", params[3]));
            postParams.add(new BasicNameValuePair("str_endDateTime", params[4]));
            postParams.add(new BasicNameValuePair("latitude", params[5]));
            postParams.add(new BasicNameValuePair("longitude", params[6]));
            postParams.add(new BasicNameValuePair("address", params[7]));
            String result = null;
            try
            {
                hp.setEntity(new UrlEncodedFormEntity(postParams, "UTF-8"));
                HttpResponse hr = hc.execute(hp);
                result = new BasicResponseHandler().handleResponse(hr);
            }
            catch(Exception e)
            {
                showToast(false);
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result)
        {
//            if(result == null)
//            {
//                showToast();
//            }
            showToast(true);
            progressDialog.dismiss();
        }
    }

    protected void showToast(boolean succeeded)
    {
        if(!succeeded) {
            Toast toast = Toast.makeText(this.context, "Error processing your request. Please try again.", Toast.LENGTH_SHORT);
            toast.show();
        }
        else
        {
            Toast toast = Toast.makeText(this.context, "Selling information successfully saved!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void sendSellingInfo(String... params)
    {
        progressDialog.show();
        new StoreSellingInfoAsyncTask().execute(params);
    }
}
