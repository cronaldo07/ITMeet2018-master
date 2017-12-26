package np.edu.ku.itmeet;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class EventRegistration extends Fragment {

    List<TextView> tv = new ArrayList<TextView>();
    List<LinearLayout> lm = new ArrayList<LinearLayout>();
    List<EditText> et = new ArrayList<EditText>();
    List<Spinner> sp = new ArrayList<Spinner>();

    ArrayAdapter<CharSequence> spinner;
    ProgressDialog progressDialog;

    String[] fields={};
    String[] keys={};
    String[] types={};
    String url="";
    RequestQueue queue;

    String[] value={"","","","","","","","","","","","","","","",""};

    

    String name_x;
    String address_x;
    String phone_x;
    String email_x;
    String age_x;
    String gender_x;
    String inst_x;
    String lvl_x;
    String event_x;



    public EventRegistration() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root=inflater.inflate(R.layout.fragment_event_registration, container, false);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Sumbitting...");

        initializeUI(root);
        queue = Volley.newRequestQueue(getActivity());

        String event="";

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            event = bundle.getString("event", null);
        }

        switch(event){
            case "alumni_meet":
                fields=event_constants.alumni_meet_fields;
                keys=event_constants.alumni_meet_keys;
                types=event_constants.alumni_meet_types;
                url=event_constants.alumni_meet_url;
                break;
            case "yomari_code_camp":
                fields=event_constants.code_camp_fields;
                keys=event_constants.code_camp_keys;
                types=event_constants.code_camp_types;
                url=event_constants.code_camp_url;
                break;
            case "hackathon":
                fields=event_constants.hackathon_fields;
                keys=event_constants.hackathon_keys;
                types=event_constants.hackathon_types;
                url=event_constants.hackathon_url;
                break;
            case "career_fair":
                fields=event_constants.career_fair_fields;
                keys=event_constants.career_fair_keys;
                types=event_constants.career_fair_types;
                url=event_constants.career_fair_url;
                break;
            case "hardware_comp":
                fields=event_constants.hardware_comp_fields;
                keys=event_constants.hardware_comp_keys;
                types=event_constants.hardware_comp_types;
                url=event_constants.hardware_comp_url;
                break;
            case "software_comp":
                fields=event_constants.software_comp_fields;
                keys=event_constants.software_comp_keys;
                types=event_constants.software_comp_types;
                url=event_constants.software_comp_url;
                break;
            case "coding_comp":
                fields=event_constants.coding_comp_fields;
                keys=event_constants.coding_comp_keys;
                types=event_constants.coding_comp_types;
                url=event_constants.coding_comp_url;
                break;
            case "design_comp":
                fields=event_constants.design_comp_fields;
                keys=event_constants.design_comp_keys;
                types=event_constants.design_comp_types;
                url=event_constants.design_comp_url;
                break;
            case "nephack":
                fields=event_constants.nephack_fields;
                keys=event_constants.nephack_keys;
                types=event_constants.nephack_types;
                url=event_constants.nephack_url;
                break;                        
            case "data_local":
                fields=event_constants.data_local_fields;
                keys=event_constants.data_local_keys;
                types=event_constants.data_local_types;
                url=event_constants.data_local_url;
                break;
            case "googling":
                fields=event_constants.googling_fields;
                keys=event_constants.googling_keys;
                types=event_constants.googling_types;
                url=event_constants.googling_url;
                break;
            case "csgo":
                fields=event_constants.csgo_fields;
                keys=event_constants.csgo_keys;
                types=event_constants.csgo_types;
                url=event_constants.csgo_url;
                break;
            case "dota":
                fields=event_constants.dota_fields;
                keys=event_constants.dota_keys;
                types=event_constants.dota_types;
                url=event_constants.dota_url;
                break;
        }
        
        
        final SharedPreferences mSharedPreference= PreferenceManager.getDefaultSharedPreferences(getActivity());
        name_x=(mSharedPreference.getString("name",null ));
        address_x=(mSharedPreference.getString("address",null ));
        phone_x=(mSharedPreference.getString("phone",null ));
        age_x=(mSharedPreference.getString("age",null ));
        email_x=(mSharedPreference.getString("email",null ));

        gender_x=(mSharedPreference.getString("gender",null ));
        inst_x=(mSharedPreference.getString("institution",null ));
        lvl_x=(mSharedPreference.getString("level",null ));
        event_x=(mSharedPreference.getString("event_d",null ));

        
        generate();

           root.findViewById(R.id.button_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerValues();
                if(required()){
                    postData();}
            }
        });

        return root;
    }
    
        private boolean required(){
        boolean ck=true;
        for(int i=0;i<16;i++){
            if(fields[i].contains(" *")&&types[i]=="text"&&isEmpty(et.get(i))){
                et.get(i).setError("This is a required field");
//                        Toast.makeText(getActivity(),"This is a required field  " +value[0],Toast.LENGTH_LONG).show();
                ck=false;
            }
            if(fields[i].contains(" *")&&types[i]=="text"&&!isEmpty(et.get(i))){
                ck=true;
            }

        }
        return ck;
    }

    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }

    @Override
    public void onResume() {

        super.onResume();

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){

                    // handle back button
                   // getActivity().getSupportFragmentManager().popBackStack();

                    Fragment fragment = new HomeFragment();
                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();

                    ft.replace(R.id.frag_event_registration, fragment);
                    ft.addToBackStack("tag");
                    ft.commit();
                    return true;

                }

                return false;
            }
        });
    }
    private void registerValues() {
        for(int i=0;i<20;i++){
            switch (types[i]){
                case "text":
                    value[i]=et.get(i).getText().toString();
                    break;
                case "drop":
                    value[i]=sp.get(i).getSelectedItem().toString();
                    break;
            }

        }
    }

    public void postData() {
        progressDialog.show();
        StringRequest request = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.length() > 0) {
                            Toast.makeText(getContext(),"Submitted :)",Toast.LENGTH_LONG).show();
                                for(int i=0;i<16;i++){
                                    et.get(i).setText("");
                                }
                        } else {
                            Toast.makeText(getContext(),"Try again",Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getContext(),"Try again !!! ",Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                for(int i=0;i<16;i++){
                    params.put(keys[i], value[i]);
                }

                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);
    }


    private void generate() {

        for(int i=0;i<16;i++){
            if(fields[i]!=null) {
            tv.get(i).setText(fields[i]);
            lm.get(i).setVisibility(View.VISIBLE);
            switch (types[i]) {
                case "text":
                    et.get(i).setVisibility(View.VISIBLE);

                    break;
                case "drop":
                    sp.get(i).setVisibility(View.VISIBLE);
                    switch (fields[i]) {
                        case "Batch *":
                            spinnerInt(R.array.batch);
                            break;
                        case "Semester":
                            spinnerInt(R.array.semester);
                            break;
                        case "Group *":
                            spinnerInt(R.array.group);
                            break;
                        case "Are you in Nepal? *":
                            spinnerInt(R.array.yn);
                            break;
                        case "Are you attending the Alumni Meet? *":
                            spinnerInt(R.array.yn);
                            break;
                        case "Project Theme *":
                            spinnerInt(R.array.project_theme);
                            break;
                        case "Gender *":
                            spinnerInt(R.array.gender);
                            break;
                        case "Age *":
                            spinnerInt(R.array.age);
                            break;
                        case "Are you a ? *":
                            spinnerInt(R.array.level);
                            break;
                        case "Do you have any previous job experience? *":
                            spinnerInt(R.array.experience);
                            break;
                        case "What do you think is suitable for you to work on? *":
                            spinnerInt(R.array.work);
                            break;
                        case "How did you hear about this event?":
                            spinnerInt(R.array.hear);
                            break;
                        case "Education Background":
                            spinnerInt(R.array.ed);
                            break;
                        case "Platform(s) you are likely to use":
                            spinnerInt(R.array.design);
                            break;
                        case "How do you catagorize yourself ? *":
                            spinnerInt(R.array.cat);
                            break;
                        case "What is your preference? *":
                            spinnerInt(R.array.data_local);
                            break;
                        case "Does your team have steam account? *":
                            spinnerInt(R.array.yn);
                            break;
                        case "Payment Type *":
                            spinnerInt(R.array.payment_type);
                            break;
                        case "Location of payment":
                            spinnerInt(R.array.payment);
                            break;
                    }
                    spinner.setDropDownViewResource(android.R.layout.simple_spinner_item);
                    sp.get(i).setAdapter(spinner);
                    break;
            }
        }
    }
    
        getDefaults();
    
    }


    public void spinnerInt(int id){
        spinner = ArrayAdapter.createFromResource(getActivity(),id, android.R.layout.simple_spinner_item);

    }

    private void initializeUI(View root) {
        int[] tvid={R.id.t0,R.id.t1,R.id.t2,R.id.t3,R.id.t4,R.id.t5,R.id.t6,R.id.t7,R.id.t8,R.id.t9,R.id.t10,R.id.t11,R.id.t12,R.id.t13,R.id.t14,R.id.t15};
        int[] lmid={R.id.lm0,R.id.lm1,R.id.lm2,R.id.lm3,R.id.lm4,R.id.lm5,R.id.lm6,R.id.lm7,R.id.lm8,R.id.lm9,R.id.lm10,R.id.lm11,R.id.lm12,R.id.lm13,R.id.lm14,R.id.lm15};
        int[] etid={R.id.e0,R.id.e1,R.id.e2,R.id.e3,R.id.e4,R.id.e5,R.id.e6,R.id.e7,R.id.e8,R.id.e9,R.id.e10,R.id.e11,R.id.e12,R.id.e13,R.id.e14,R.id.e15};
        int[] spid={R.id.s0,R.id.s1,R.id.s2,R.id.s3,R.id.s4,R.id.s5,R.id.s6,R.id.s7,R.id.s8,R.id.s9,R.id.s10,R.id.s11,R.id.s12,R.id.s13,R.id.s14,R.id.s15};
        for(int i=0;i<16;i++){
            tv.add((TextView) root.findViewById(tvid[i]));
            lm.add((LinearLayout) root.findViewById(lmid[i]));
            et.add((EditText) root.findViewById(etid[i]));
            sp.add((Spinner) root.findViewById(spid[i]));
        }
    }
    
    
    private void getDefaults(){
        for(int i=0;i<16;i++){
            if(fields[i].contains("Your Name ")){
                et.get(i).setText(name_x);

            }
            if(fields[i].contains("Contact Number")){
                et.get(i).setText(phone_x);

            }
            if(fields[i].contains("Email ")){
                et.get(i).setText(email_x);

            }
            if(fields[i].contains("Address ")){
                et.get(i).setText(address_x);
            }
            if(fields[i].contains("College ")){
                et.get(i).setText(inst_x);
            }
            if(fields[i].contains("Age ")){
                selectSpinnerValue(sp.get(i),age_x);
            }
            if(fields[i].contains("Gender ")){
                selectSpinnerValue(sp.get(i),gender_x);
            }
            if(fields[i].contains("Level ")){
                selectSpinnerValue(sp.get(i),lvl_x);
            }
            if(fields[i].contains("How did you hear about this event?")){
                et.get(i).setText(event_x);
            }


        }
    }

    
private void selectSpinnerValue(Spinner spinner, String myString)
    {
        int index = 0;
        for(int i = 0; i < spinner.getCount(); i++){
            if(spinner.getItemAtPosition(i).toString().equals(myString)){
                spinner.setSelection(i);
                break;
            }
        }
    }

}
