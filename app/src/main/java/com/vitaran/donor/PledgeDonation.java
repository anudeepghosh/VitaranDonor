package com.vitaran.donor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PledgeDonation extends AppCompatActivity implements AdapterView.OnItemSelectedListener,View.OnClickListener{
    Spinner sitem,scategory,sunit;
    ArrayAdapter adapter;
    EditText eaddress,edetails,equantity;
    CheckBox checkBox;
    TextView elist;
    //Button badd , bpledge;
    RequestQueue rq;//rq1,rq2;
    String[] arr,arrcat;
    String quantity,iname,cname,unit;
    ArrayList<String> arrayList;
    ImageButton bpledge,badd;
    String showURL = Constants.BASE_URL+"/showItem.php";
    String showURLC = Constants.BASE_URL+"/showCategory.php";
    String insertdonor = Constants.BASE_URL+"/insertdonorItem.php";
    String showURLdonor = Constants.BASE_URL+"/showdonorItem.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pledge_donation);
        sitem = (Spinner)findViewById(R.id.spinner);
        equantity = (EditText)findViewById(R.id.editText9);
        scategory = (Spinner)findViewById(R.id.spinner2);
        sunit = (Spinner)findViewById(R.id.spinner4);
        eaddress = (EditText)findViewById(R.id.editText2);
        edetails = (EditText)findViewById(R.id.editText);
        // badd = (Button)findViewById(R.id.button);
        // bpledge = (Button)findViewById(R.id.button2);
        badd = (ImageButton)findViewById(R.id.imageButton6);
        bpledge = (ImageButton)findViewById(R.id.imageButton5);
        elist = (TextView)findViewById(R.id.textView3);
        checkBox = (CheckBox)findViewById(R.id.checkBox);
        //ArrayAdapter<String> adapter;
        arr = new String[20];
        arrcat = new String[20];
        arrayList = new ArrayList<String>();
        elist.setText("hello");
        //equantity.setText("12");
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(PledgeDonation.this, R.array.item_ngo_unit, android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        sunit.setAdapter(adapter3);
        //rq2
        sitem.setOnItemSelectedListener(this);
        scategory.setOnItemSelectedListener(this);
        sunit.setOnItemSelectedListener(this);
        rq = Volley.newRequestQueue(this);

        /*
         badd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                elist.setText("blackcrab");
                quantity = equantity.getText().toString();
 Toast.makeText(getApplicationContext(),iname+" "+cname+" "+quantity+" "+unit,Toast.LENGTH_LONG).show();
                elist.append(iname+" "+cname+" "+quantity+" "+unit);
       /*        /// elist.setText("blackcrab");
                quantity = equantity.getText().toString();
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, showURLdonor, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {

                            JSONArray aitem = response.getJSONArray("Items");
                            for (int i = 0; i < aitem.length(); i++) {
                                JSONObject item = aitem.getJSONObject(i);
                                String name = item.getString("name");
                                String cat = item.getString("category");
                                String quan = item.getString("quantity");
                                String unit = item.getString("unit");


                                elist.append(name + " " + cat+" " +quan +" " + unit + "\n");

                            }
                            elist.append("===\n");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("VOLLEY", "ERROR");
                        error.printStackTrace();
                    }
                });
                rq.add(jsonObjectRequest);
                //rq2
            }
        });



       // rq2 = Volley.newRequestQueue(this);
      /*  badd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // elist.setText("blackcrab");
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, showURLdonor, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {

                            JSONArray aitem = response.getJSONArray("Items");
                            for (int i = 0; i < aitem.length(); i++) {
                                JSONObject item = aitem.getJSONObject(i);
                                String name = item.getString("Name");
                                String quan = item.getString("Quantity");
                                String unit = item.getString("Unit");

                                elist.append(name + " " + quan + " " + unit + "\n");

                            }
                            elist.append("===\n");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("VOLLEY", "ERROR");
                        error.printStackTrace();
                    }
                });
                rq.add(jsonObjectRequest);
                //rq2
            }
        });

*/




        // rq = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest jsonObjectRequest1 = new JsonObjectRequest(Request.Method.POST, showURLC, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    JSONArray items = response.getJSONArray("Category");
                    for(int i=0;i<items.length();i++)
                    {
                        JSONObject cat = items.getJSONObject(i);
                        String name = cat.getString("name");
                        //Toast.makeText(getApplicationContext(),name,Toast.LENGTH_SHORT).show();
                        arrcat[i]= name;
                    }
// Create an ArrayAdapter using the string array and a default spinner layoutu
                    adapter = new ArrayAdapter(PledgeDonation.this,android.R.layout.simple_spinner_item,arrcat);
// Specify the layout to use when the list of choices appears
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
                    scategory.setAdapter(adapter);
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        rq.add(jsonObjectRequest1);





        rq = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, showURL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    JSONArray items = response.getJSONArray("Items");
                    for(int i=0;i<items.length();i++)
                    {
                        JSONObject item = items.getJSONObject(i);
                        String name = item.getString("Name");
                        arr[i]= name;
                    }
// Create an ArrayAdapter using the string array and a default spinner layoutu
                    adapter = new ArrayAdapter(PledgeDonation.this,android.R.layout.simple_spinner_item,arr);
// Specify the layout to use when the list of choices appears
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
                    sitem.setAdapter(adapter);
                }
                catch(JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        rq.add(jsonObjectRequest);
        bpledge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PledgeDonation.this, Success.class);
                startActivity(i);
            }
        });


        badd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity = equantity.getText().toString();
                elist.append(iname+" "+cname+" "+quantity+" "+unit+"\n");
                equantity.setText(null);
            }
        });

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(parent.getId()==sitem.getId()){
            iname = parent.getItemAtPosition(position).toString();

            //if(iname.equals("Others")){
            //   other_item.setVisibility(View.VISIBLE);
            //  }
            Toast.makeText(parent.getContext(), "Item Selected: " + iname, Toast.LENGTH_LONG).show();}

        if(parent.getId()==scategory.getId()){
            cname = parent.getItemAtPosition(position).toString();
            //  if(icategory.equals("Others"))
            // other_category.setVisibility(View.VISIBLE);
            Toast.makeText(parent.getContext(), "Category Selected: " + cname, Toast.LENGTH_LONG).show();}
        if(parent.getId()==sunit.getId()){
            unit = parent.getItemAtPosition(position).toString();
            Toast.makeText(parent.getContext(), "Unit Selected: " + unit, Toast.LENGTH_LONG).show();}
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    @Override
    public void onClick(View v) {

    }
}
