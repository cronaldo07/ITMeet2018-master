package np.edu.ku.itmeet18;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class AboutFragment extends Fragment implements View.OnClickListener{
    Context context;
    Activity activity;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            View rootview= inflater.inflate(R.layout.about, container, false);

            context = rootview.getContext();
            activity = getActivity();
            Button SambadGP, SambadG, SambadT, AyushGP, AyushG, AyushT, DipeshGP,DipeshG, DipeshT, SuyogGP, SuyogG, SuyogT;
            SambadGP=(Button) rootview.findViewById(R.id.buttongp1);
            SambadGP.setOnClickListener(this);
            SambadG=(Button) rootview.findViewById(R.id.buttong1);
            SambadG.setOnClickListener(this);
            SambadT=(Button) rootview.findViewById(R.id.buttona1);
            SambadT.setOnClickListener(this);
            AyushGP=(Button) rootview.findViewById(R.id.buttongp2);
            AyushGP.setOnClickListener(this);
            AyushG=(Button) rootview.findViewById(R.id.buttong2);
            AyushG.setOnClickListener(this);
            AyushT=(Button) rootview.findViewById(R.id.buttona2);
            AyushT.setOnClickListener(this);
            DipeshGP=(Button) rootview.findViewById(R.id.buttongp3);
            DipeshGP.setOnClickListener(this);
            DipeshG=(Button) rootview.findViewById(R.id.buttong3);
            DipeshG.setOnClickListener(this);
            DipeshT=(Button) rootview.findViewById(R.id.buttona3);
            DipeshT.setOnClickListener(this);
            SuyogGP=(Button) rootview.findViewById(R.id.buttongp4);
            SuyogGP.setOnClickListener(this);
            SuyogG=(Button) rootview.findViewById(R.id.buttong4);
            SuyogG.setOnClickListener(this);
            SuyogT=(Button) rootview.findViewById(R.id.buttona4);
            SuyogT.setOnClickListener(this);
        return rootview;
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

                    ft.replace(R.id.frag_about, fragment);
                    ft.addToBackStack("tag");
                    ft.commit();
                    return true;

                }

                return false;
            }
        });
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onClick(View dev) {
        // default method for handling onClick Events..
        switch (dev.getId()) {
            case R.id.buttongp1:
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("fb://profile/100000956408193")));
                }
                catch(ActivityNotFoundException e)
                {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/bidarisambad")));
                }

                break;

            case R.id.buttong1:
                // do your code
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/sambadbidari")));
                //String gprofile ="https://github.com/sambadbidari";
                break;
            case R.id.buttona1:
                // do your code
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/i_bidari")));

                break;
            case R.id.buttongp2:
                // do your code
                try{
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("fb://profile/100001039167019")));
                }
                catch(ActivityNotFoundException e)
            {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/ayushkumarshah")));
            }

                break;

            case R.id.buttong2:
                // do your code
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/cronaldo07")));
                break;
            case R.id.buttona2:
                // do your code
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/Ayushkumarshah7")));

                break;
            case R.id.buttongp3:
                // do your code
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("fb://profile/100010725151010")));
                }
                catch(ActivityNotFoundException e)
                {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/deepesh.shrestha.5494")));
                }
                break;

            case R.id.buttong3:
                // do your code

                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/deepeshshrestha")));
                break;
            case R.id.buttona3:
                // do your code
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/haku_the_black")));
                break;
            case R.id.buttongp4:
                // do your code
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("fb://profile/100000155435271")));
                }
                catch(ActivityNotFoundException e)
                {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/lionelsuyog")));
                }
                break;

            case R.id.buttong4:
                // do your code
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/")));
                break;
            case R.id.buttona4:
                // do your code
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/leosuyog")));

                break;
        }
        }
    }

