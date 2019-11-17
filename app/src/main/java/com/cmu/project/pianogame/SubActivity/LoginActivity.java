package com.cmu.project.pianogame.SubActivity;

import androidx.annotation.NonNull;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.cmu.project.pianogame.Game.Utils.Sound;
import com.cmu.project.pianogame.MainActivity.Service.Firebase;
import com.cmu.project.pianogame.MainActivity.Service.Set;
import com.cmu.project.pianogame.Options.Options;
import com.cmu.project.pianogame.MainActivity.MainActivity;
import com.cmu.project.pianogame.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookAuthorizationException;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    private DatabaseReference reference;

    private RelativeLayout mFacebookBtn;
    private CallbackManager mCallbackManager;

    private static final String TAG = "FACELOG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Options.SetFullScreen(LoginActivity.this);
        Options.HideNavBar(LoginActivity.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Sound.Audio(LoginActivity.this, Options.getResourceId(LoginActivity.this, "raw", "openapp"));
        Sound.Resume(Sound.getLength());

        // Initialize Facebook Login button
        mFacebookBtn = findViewById(R.id.rev1);
        mCallbackManager = CallbackManager.Factory.create();
        mFacebookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("email", "public_profile"));
                LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.d(TAG, "facebook:onSuccess:" + loginResult);
                        handleFacebookAccessToken(loginResult.getAccessToken());
                    }

                    @Override
                    public void onCancel() {
                        Log.d(TAG, "facebook:onCancel");
                        // ...
                    }

                    @Override
                    public void onError(FacebookException error) {
                        Log.d(TAG, "facebook:onError", error);
                        if (error instanceof FacebookAuthorizationException) {
                            if (AccessToken.getCurrentAccessToken() != null) {
                                LoginManager.getInstance().logOut();
                            }
                        }
                    }
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result back to the Facebook SDK
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private String facebookUserId = "";

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        Firebase.getmAuth().signInWithCredential(credential)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            String facebook_id = Firebase.getCurrent().getUid();
                            String f_name = Firebase.getCurrent().getDisplayName();
                            for(UserInfo profile : Firebase.getCurrent().getProviderData()) {
                                // check if the provider id matches "facebook.com"
                                if(FacebookAuthProvider.PROVIDER_ID.equals(profile.getProviderId())) {
                                    facebookUserId = profile.getUid();
                                }
                            }
                            String photoUrl = "https://graph.facebook.com/" + facebookUserId + "/picture?height=500";

                            mFacebookBtn.setEnabled(true);
                            updatefacebookUI(facebook_id, f_name, photoUrl);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                        }
                    }
                });
    }

    private void updatefacebookUI(final String facebook_id, final String f_name, final String photoUrl) {

        reference = FirebaseDatabase.getInstance().getReference("Users").child(facebook_id);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (!dataSnapshot.exists()) {
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("id", facebook_id);
                    hashMap.put("username", f_name);
                    hashMap.put("imageURL", photoUrl);
                    Set.CreateEnergy(facebook_id);
                    Set.CreateCoin(facebook_id);
                    Set.CreateTheme(facebook_id);
                    Set.CreateColor(facebook_id);
                    Set.CreateMusic(facebook_id);
                    Set.CreateScore(facebook_id);
                    Set.CreateLove(facebook_id);
                    reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) Options.showMessage(getApplicationContext(), "Successful create account with Facebook!", 2);
                        }
                    });
                } else {
                    reference.child("username").setValue(f_name);
                    reference.child("imageURL").setValue(photoUrl);
                    updateUI();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void updateUI() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Options.showMessage(getApplicationContext(), "Successful login with Facebook!", 2);
        Options.ChangePage(LoginActivity.this, intent);
    }

    @Override
    protected void onPause() {
        Sound.Pause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        Sound.Resume(Sound.getLength());
        Options.HideNavBar(LoginActivity.this);
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        Options.HideNavBar(LoginActivity.this);
        super.onDestroy();
    }
}
