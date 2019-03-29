package it.chiarani.otlsmartcontroller.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import db.Entities.UserProfileEntity;
import it.chiarani.otlsmartcontroller.App;
import it.chiarani.otlsmartcontroller.R;
import it.chiarani.otlsmartcontroller.databinding.ActivitySignInBinding;
import it.chiarani.otlsmartcontroller.repositories.UserProfileRepository;
import it.chiarani.otlsmartcontroller.viewmodels.UserProfileViewModel;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class SignInActivity extends BaseActivity implements View.OnClickListener, UserProfileRepository.insertResponse {

    GoogleSignInClient mGoogleSignInClient;
    ActivitySignInBinding binding;
    UserProfileViewModel viewModel;
    GoogleSignInAccount account;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_sign_in;
    }

    @Override
    protected void setActivityBinding() {
        binding = DataBindingUtil.setContentView(this, getLayoutID());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        findViewById(R.id.sign_in_button).setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        account = GoogleSignIn.getLastSignedInAccount(this);
        if(account != null) {

            /*GoogleSignInResult result =
            Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            GoogleSignInAccount acct = result.getSignInAccount();
            String personName = acct.getDisplayName();
            String personGivenName = acct.getGivenName();
            String personFamilyName = acct.getFamilyName();
            String personEmail = acct.getEmail();
            String personId = acct.getId();
            Uri personPhoto = acct.getPhotoUrl();*/
            String personName = account.getDisplayName();
            gotoMain(account);
        }

        //  updateUI(account);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                signIn();
                break;
            // ...
        }
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, 010101);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == 010101) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            account = completedTask.getResult(ApiException.class);

            String personName = account.getDisplayName();
            // Signed in successfully, show authenticated UI.
            gotoMain(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("ok", "signInResult:failed code=" + e.getStatusCode());
            int x = 1;
           // updateUI(null);
        }
    }


    protected UserProfileViewModel getViewModel() {
        if(viewModel == null) {
            Application application = getApplication();
            UserProfileViewModel.Factory factory = new UserProfileViewModel.Factory(application, ((App)getApplication()).getRepository());
            viewModel = ViewModelProviders.of(this, factory).get(UserProfileViewModel.class);
        }
        return viewModel;
    }


    private void gotoMain(GoogleSignInAccount acc) {

        UserProfileEntity tmpUser = new UserProfileEntity();

        getViewModel().getUserData().observe(this, data -> {
            boolean isUserLoged = false;

            if(data == null) {
                tmpUser.userAccessToken = account.getEmail();
                getViewModel().insertData(tmpUser, this);
            }

            for(UserProfileEntity user : data) {
                if(user.userAccessToken.equals(account.getEmail())) {
                    this.onResponse();
                    isUserLoged = true;
                }
            }

            if(!isUserLoged) {
                tmpUser.userAccessToken = account.getEmail();
                getViewModel().insertData(tmpUser, this);
            }

        });


    }

    @Override
    public void onResponse() {
        Intent myIntent = new Intent(SignInActivity.this, MainActivity.class);
        myIntent.putExtra("G-NAME", account.getDisplayName());
        String x  = account.getPhotoUrl().toString();
         myIntent.putExtra("G-PIC", x);
        this.startActivity(myIntent);
    }
}
