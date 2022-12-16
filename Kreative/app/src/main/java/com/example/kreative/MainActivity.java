package com.example.kreative;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kreative.ui.pantalla_p;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;


import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;


public class MainActivity extends AppCompatActivity {
   // Declaracion de TextViews y Buttons
    private CallbackManager mCallbackManager;
    public TextView registro;
    private EditText email;
    private EditText password;
    private LoginButton LoginButton;
    private AccessTokenTracker accessTokenTracker;
    private FirebaseAuth.AuthStateListener authStateListener;
    private static final String T = "FacebookAuthentication";
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    int RC_SIGN_IN = 1;
    private TextView btnSignIn;
    String TAG = "GoogleSignIn";
    //---------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        FirebaseAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();
        setTheme(R.style.Theme_Kreative_NoActionBar);
        super.onCreate(savedInstanceState);
        FacebookSdk.fullyInitialize();
        AppEventsLogger.activateApp(getApplication());
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        registro = findViewById(R.id.registro);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        btnSignIn = findViewById(R.id.btnSignIn);
        mAuth = FirebaseAuth.getInstance();


        //----------------Inicio de sesion con Correo y Email------------------------
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                signIn();
            }
        });
        //---------------------------------------------------


// Inicio de Sesion con Google
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


// Aqui empieza el inicio de sesion con Facebook
        mCallbackManager = CallbackManager.Factory.create();
        LoginButton = findViewById(R.id.login_button);
        LoginButton.setPermissions("email", "public_profile");
        LoginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                handleFacebookToken(loginResult.getAccessToken());

            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);
            }
            
        });


        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = mAuth.getCurrentUser();
                if (user != null) {
                    Pantallaprincipal();
                } else {
                    // updateUI(null);
                }
            }

        };



        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                if (currentAccessToken == null) {
                    mAuth.signOut();
                }
            }
        };
    }
    //---------------------------------------------------
    //                 Metodos(Funciones)
    //---------------------------------------------------



    // Metodos para inicio de sesion de Facebook
    private void handleFacebookToken(AccessToken token) {
        Log.d(T, "handleFacebookToken" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d(T, "Inicio sastifactorio");
                    FirebaseUser user = mAuth.getCurrentUser();
                } else {

                    Log.d(T,"Inicio fallido:",task.getException());
                    Toast.makeText(MainActivity.this, "Fallo al iniciar.",
                            Toast.LENGTH_SHORT).show();
                }



            }
        });
    }
    //------------------Boton de Retroceder---------------------------------
    @Override
    public void onBackPressed(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("¿Deseas salir?")
                .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                        finish(); System.exit(0);
                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.show();
    }
    //---------------------------------------------------


// Metodo Activity
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());

            } catch (ApiException e) {
                Log.w(TAG, "Google sign in failed", e);
            }
        }

        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }
    //---------------------------------------------------

    // Inicio de sesión con Google
    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            //updateUI(null);
                        }
                    }
                });
    }


    //-------------------------
private void Pantallaprincipal() {


    Intent i= new Intent(getApplicationContext(), Principal.class);
    getIntent().addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
    startActivity(i);
    overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
}
    //---------------------------------------------------

    private void signIn(){
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    //---------------------------------------------------
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
mAuth.addAuthStateListener(authStateListener);
    }
    //---------------------------------------------------


// Inicio de sesion (Normal)
    public void registrarse(View view){

        Intent i= new Intent(getApplicationContext(),Registrousuario.class);
        startActivity(i);
    }

    public void iniciarsesion(View view){
        if (email.getText().toString().equals("") && password.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Campo Vacio", Toast.LENGTH_SHORT).show();


        } else {
        mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(getApplicationContext(), "Inicio Exitoso", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent i= new Intent(getApplicationContext(), Principal.class);
                            startActivity(i);
                            overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                           // updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getApplicationContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }
                    }
                });
        }
    }
    //---------------------------------------------------
    protected void onStop() {
        super.onStop();
        if (authStateListener != null) {
            mAuth.removeAuthStateListener(authStateListener);

        }
    }

    }
