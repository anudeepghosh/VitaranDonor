package com.vitaran.donor;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class RegisterFragment extends Fragment implements View.OnClickListener{

    private AppCompatButton btn_register;
    private EditText et_email,et_password,et_re_password,et_name,et_contact,et_address;
    private TextView tv_login;
    private ProgressBar progress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_register,container,false);
        initViews(view);

        // Adding listener to first name EditText for verifying correct format
        et_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus == false)
                    isValidName(et_name);
                return;
            }
        });

        // Adding listener to email EditText for verifying correct format
        et_email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus == false)
                    isValidEmailID(et_email);
                return;
            }
        });

        // Adding listener to re_password EditText for verifying correct format
        /*et_re_password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP)
                    isValidRePassword(et_password, et_re_password);
                return true;
            }
        });*/

        // Adding listener to password EditText for verifying correct format
        et_password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus == false)
                    isValidPassword(et_password);
                return;

            }
        });

        // Adding listener to contact EditText for verifying correct format
        et_contact.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus == false)
                    isValidContact(et_contact);
                return;
            }
        });

        return view;
    }

    private void initViews(View view){

        btn_register = (AppCompatButton)view.findViewById(R.id.btn_register);
        tv_login = (TextView)view.findViewById(R.id.tv_login);
        et_name = (EditText)view.findViewById(R.id.et_name);
        et_email = (EditText)view.findViewById(R.id.et_email);
        et_password = (EditText)view.findViewById(R.id.et_password);
        et_re_password = (EditText)view.findViewById(R.id.et_repassword);
        et_contact = (EditText)view.findViewById(R.id.et_contact);
        et_address = (EditText) view.findViewById(R.id.et_address);

        progress = (ProgressBar)view.findViewById(R.id.progress);

        btn_register.setOnClickListener(this);
        tv_login.setOnClickListener(this);

        //et_contact.setRawInputType(Configuration.KEYBOARD_12KEY);

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.tv_login:
                goToLogin();
                break;

            case R.id.btn_register:
                attemptRegistration();
                break;
        }

    }

    private void attemptRegistration() {

        // Reset errors.
        et_name.setError(null);
        et_email.setError(null);
        et_address.setError(null);
        et_contact.setError(null);
        et_password.setError(null);
        et_re_password.setError(null);

        // Store values at the time of the registration attempt.
        String name = et_name.getText().toString();
        String email = et_email.getText().toString();
        String address = et_address.getText().toString();
        String contact = et_contact.getText().toString();
        String password = et_password.getText().toString();
        //String repassword = et_re_password.getText().toString();

        Boolean cancel = false;
        View focusView = null;

        if(!isValidName(et_name)) {
            focusView = et_name;
            cancel = true;
        }
        if(!isValidEmailID(et_email)) {
            focusView = (focusView==null)?et_email:focusView;
            cancel = true;
        }
        if(!isValidPassword(et_password)) {
            focusView = (focusView==null)?et_password:focusView;
            cancel = true;
        }
        if(!isValidRePassword(et_password, et_re_password)) {
            focusView = (focusView==null)?et_re_password:focusView;
            cancel = true;
        }
        if(!isValidContact(et_contact)) {
            focusView = (focusView==null)?et_contact:focusView;
            cancel = true;
        }
        if(!isValidAddress(et_address)) {
            focusView = (focusView==null)?et_address:focusView;
            cancel = true;
        }
        if (cancel) {
            // There was an error;
            // don't attempt registration and focus the first
            // form field with an error.
            focusView.requestFocus();
            Toast.makeText(getActivity(), "Recheck the fields!", Toast.LENGTH_LONG).show();
            //Snackbar.make(getView(), "Recheck the fields!", Snackbar.LENGTH_LONG).show();
        } else {
            progress.setVisibility(View.VISIBLE);
            registerProcess(name, email, address, contact, password);
        }
    }

    private void registerProcess(final String name, final String email, final String address, final String contact, final String password){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getActivity(), response, Toast.LENGTH_LONG).show();
                        if (response.equals("success")) {
                            progress.setVisibility(View.INVISIBLE);
                            goToLogin();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(),error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put(Constants.NAME, name);
                params.put(Constants.PASSWORD, password);
                params.put(Constants.EMAIL, email);
                params.put(Constants.ADDRESS, address);
                params.put(Constants.CONTACT, contact);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

        /*Gson gson = new GsonBuilder().setLenient().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        RequestInterface requestInterface = retrofit.create(RequestInterface.class);

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setContact(contact);
        user.setAddress(address);
        ServerRequest request = new ServerRequest();
        request.setOperation(Constants.REGISTER_OPERATION);
        request.setUser(user);
        Call<ServerResponse> response = requestInterface.operation(request);

        response.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {

                ServerResponse resp = response.body();
                Toast.makeText(getActivity(), "Response::"+resp.getMessage(), Toast.LENGTH_LONG).show();
                //Snackbar.make(getView(), resp.getMessage(), Snackbar.LENGTH_LONG).show();
                progress.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

                progress.setVisibility(View.INVISIBLE);
                Log.d(Constants.TAG, "failed");

                Toast.makeText(getActivity(), t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                //Snackbar.make(getView(), t.getLocalizedMessage(), Snackbar.LENGTH_LONG).show();

            }
        });
        */
    }

    private boolean isValidName(EditText et) {
        String name = et.getText().toString().trim();
        Pattern p = Pattern.compile("^[a-zA-Z\\s]+$");
        if (name.isEmpty()) {
            et.setError("This field is required");
            return false;
        } else if (!p.matcher(name).find()) { //name.matches("[a-zA-Z]+[\\sa-zA-Z]]");
            et.setError("Invalid name");
            return false;
        }
        return true;
    }

    private boolean isValidEmailID(EditText et) {
        String email = et.getText().toString().trim();
        if (email.isEmpty()) {
            et.setError("This field is required");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            et.setError("Invalid email");
            return false;
        }
        return true;
    }

    private boolean isValidPassword(EditText et) {
        // Code for password specifications.
        String email = et.getText().toString();
        if (email.isEmpty()) {
            et.setError("This field is required");
            return false;
        } //else if( #Match pattern for required password criteria) { #Handle password likewise }
        return true;
    }

    private boolean isValidRePassword(EditText et_pass, EditText et_repass) {
        String pass = et_pass.getText().toString(), repass = et_repass.getText().toString();
        if (repass.isEmpty()) {
            et_repass.setError("This field is required");
            return false;
        } else if (!repass.equals(pass)) {
            et_repass.setError("Passwords don't match");
            return false;
        }
        return true;
    }

    private boolean isValidContact(EditText et) {
        String contact = et.getText().toString();
        Pattern p1 = Pattern.compile("\\b\\d{3}[.-]\\d{3}[.-]\\d{4}\\b");
        Pattern p2 = Pattern.compile("\\b\\d{10}\\b");
        if (contact.isEmpty()) {
            et.setError("This field is required");
            return false;
        } else if (!((contact.length()==12&&p1.matcher(contact).find())|(p2.matcher(contact).find()&&contact.length()==10))) {
            et.setError("Invalid contact");
            return false;
        }
        return true;
    }

    private boolean isValidAddress(EditText et) {
        String address = et.getText().toString();
        if (address.isEmpty()) {
            et.setError("This field is required");
            return false;
        }
        return true;
    }

    private void goToLogin(){
        Fragment login = new LoginFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_frame,login);
        ft.commit();
    }
}
