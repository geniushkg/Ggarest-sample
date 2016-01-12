package com.ultimateswan.ggarest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.greengrowapps.ggarest.GgaRest;
import com.greengrowapps.ggarest.Response;
import com.greengrowapps.ggarest.listeners.OnObjResponseListener;
import com.greengrowapps.ggarest.listeners.OnResponseListener;
public class MainActivity extends AppCompatActivity {
    TextView url,origin;
    Button btn_fetch_data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        url = (TextView)findViewById(R.id.tv_main_url);
        origin = (TextView)findViewById(R.id.tv_main_origin);
        btn_fetch_data = (Button) findViewById(R.id.btn_fetch_data);

        btn_fetch_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GgaRest.init(getApplicationContext());
                GgaRest.addDefaulteader("Accept", "application/json");
                GgaRest.ws().get("http://httpbin.org/get")
                        .onSuccess(OriginAndUrl.class, new OnObjResponseListener<OriginAndUrl>() {
                            @Override
                            public void onResponse(int code, OriginAndUrl object, Response fullResponse) {
                                url.setText(object.url);
                                origin.setText(object.origin);
                                Toast.makeText(MainActivity.this, "on response", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .onOther(new OnResponseListener() {
                            @Override
                            public void onResponse(int code, Response fullResponse) {
                                Toast.makeText(MainActivity.this, "Get failed", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .execute();
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
