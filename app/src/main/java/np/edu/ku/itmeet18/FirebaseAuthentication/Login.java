package np.edu.ku.itmeet18.FirebaseAuthentication;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import np.edu.ku.itmeet18.HomeFragment;
import np.edu.ku.itmeet18.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class Login extends Fragment {
    //defining views
    private Button buttonSignIn,skip;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignup;
    //firebase auth object
    private FirebaseAuth firebaseAuth;

    //progress dialog
    private ProgressDialog progressDialog;

    public Login() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView=inflater.inflate(R.layout.fragment_login, container, false);

        //getting firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();

        //if the objects getcurrentuser method is not null
        //means user is already logged in

        if(firebaseAuth.getCurrentUser() != null){
            Fragment fragment=new Profile();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.main_fragment, fragment);
            ft.commit();
        }

        //initializing views
        editTextEmail = (EditText) rootView.findViewById(R.id.email_login);
        editTextPassword = (EditText) rootView.findViewById(R.id.password_login);
        buttonSignIn = (Button) rootView.findViewById(R.id.button_login);
        skip = (Button) rootView.findViewById(R.id.skip_login);
        textViewSignup  = (TextView) rootView.findViewById(R.id.no_acount);

        progressDialog = new ProgressDialog(getActivity());

        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin();
            }
        });

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new HomeFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();

                ft.replace(R.id.frag_login, fragment);
                ft.addToBackStack("tag");
                ft.commit();
            }
        });

        rootView.findViewById(R.id.no_acount).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment=new Register();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.main_fragment, fragment);
                ft.commit();
            }
        });

        rootView.findViewById(R.id.button_forgot).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment=new Reset();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.main_fragment, fragment);
                ft.commit();
            }
        });



        return rootView;
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
                    //getActivity().getSupportFragmentManager().popBackStack();
                    Fragment fragment = new HomeFragment();
                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();

                    ft.replace(R.id.frag_login, fragment);
                    ft.addToBackStack("tag");
                    ft.commit();
                    return true;

                }

                return false;
            }
        });
    }

    //method for user login
    private void userLogin(){
        String email = editTextEmail.getText().toString().trim();
        String password  = editTextPassword.getText().toString().trim();


        //checking if email and passwords are empty
        if(TextUtils.isEmpty(email)){
            editTextEmail.setError("Please enter email");
            return;
        }

        if(TextUtils.isEmpty(password)){
            editTextPassword.setError("Please enter password");            return;
        }

        //if the email and password are not empty
        //displaying a progress dialog

        progressDialog.setMessage("Logging In Please Wait...");
        progressDialog.show();

        //logging in the user
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        //if the task is successfull
                        if(task.isSuccessful()){
                            Fragment fragment=new Profile();
                            FragmentTransaction ft = getFragmentManager().beginTransaction();
                            ft.replace(R.id.main_fragment, fragment);
                            ft.commit();
                        }
                        else{
                            //display some message here
                            Toast.makeText(getActivity(),"Incorrect email or password",Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }

}
