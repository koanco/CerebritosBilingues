package johannpolania.com.cerebritosbilingues;


import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener{

    private static final String TAG = "SignInActivity";
    private static final int RC_SIGN_IN = 9001;
    private Cursor c;
    private GoogleApiClient mGoogleApiClient;
    private TextView mStatusUser, mStatusEmail;
    private ProgressDialog mProgressDialog;
    String nombre1,pass2,nombre,pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Views
        mStatusUser = (TextView) findViewById(R.id.id_tvStatusUser);
        mStatusEmail = (TextView) findViewById(R.id.id_tvStatusEmail);

        //Button listeners.
        findViewById(R.id.sign_in_button).setOnClickListener(this);
        findViewById(R.id.id_sign_out_button).setOnClickListener(this);

        // [START configure_signin]
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        // [END configure_signin]

        // [START build_client]
        // Build a GoogleApiClient with access to the Google Sign-In API and the
        // options specified by gso.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        // [END build_client]


    }

//

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sign_in_button:    signIn();
                break;
            case R.id.id_sign_out_button:   signOut();
                break;
        }



    }
    public void refeshPrefs(){
        SharedPreferences misPreferencias = getSharedPreferences("PreferenciasUsuario", Context.MODE_PRIVATE);
        nombre1= misPreferencias.getString("nombre","");
        pass2= misPreferencias.getString("contraseña","");

    }
    public void registrarse (View v){
        Intent i = new Intent(MainActivity.this, Registrate.class);
        startActivity(i);
        finish();

    }

    public void ingresar (View v){
        refeshPrefs();
        EditText usuario=(EditText) findViewById(R.id.etUsuario);
        nombre= usuario.getText().toString();
        EditText passw=(EditText) findViewById(R.id.etPasswd);
        pass= passw.getText().toString();

        if(nombre.equals(nombre1) && pass.equals(pass2)){
            SharedPreferences mispreferencias = getSharedPreferences("PreferenciasUsuario", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor= mispreferencias.edit();
            editor.putString("nombre", nombre1);
            editor.putInt(" ", 1);
            editor.commit();
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
              guardar(nombre);
            inputMethodManager.hideSoftInputFromWindow(passw.getWindowToken(), 0);


            Toast.makeText(MainActivity.this, "Bienvenido(a) "+nombre1, Toast.LENGTH_LONG).show();
            Intent i=new Intent(this,Main2Activity.class);
            startActivity(i);
            finish();

        }else {

            Toast.makeText(MainActivity.this, "Los datos no coinciden o el usuario no está registrado", Toast.LENGTH_LONG).show();
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(passw.getWindowToken(), 0);
        }
    }

    private void guardar(String nombre)
    {
        Conexion score=new Conexion(this,"bdScore",null,2);
        SQLiteDatabase bd=score.getWritableDatabase();

        if(verificarDuplicado(nombre)!=true) {

            ContentValues registro = new ContentValues(); //Para guardar)

            registro.put("usuario", nombre);
            registro.put("animales", 0);
            registro.put("profesiones", 0);
            registro.put("frutas", 0);
            registro.put("casa", 0);
            registro.put("cuerpo", 0);
            bd.insert("puntaje", null, registro);
            bd.close();
            Toast.makeText(this, "Se guardó el usuario en la bd", Toast.LENGTH_SHORT).show();

        }
        else
        {

            Toast.makeText(this, "Ya existe este usuario en la base de datos", Toast.LENGTH_SHORT).show();

        }


    }


    public boolean verificarDuplicado(String nombre)

    {
        boolean bandera=false;

        Conexion puntaje=new Conexion(this,"bdScore",null,2);
        SQLiteDatabase  bd=puntaje.getWritableDatabase();


        c=bd.rawQuery("select *  from puntaje  where usuario='"+nombre+"'",null);
        if(c.moveToFirst()==true)
        {
            bandera=true;


        }




        bd.close();

        return bandera;

    }



    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }
    //
    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        // [START_EXCLUDE]
                        updateUI(false);
                        // [END_EXCLUDE]
                    }
                });

    }
    //
    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
        Intent intent = new Intent(MainActivity.this, Main2Activity.class);
        startActivity(intent);
        finish();
    }
    //
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from
        //   GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            mStatusUser.setText(getString(R.string.signed_in_fmt, acct.getDisplayName()));
            mStatusEmail.setText(getString(R.string.signed_in_fmt2, acct.getEmail()));
            updateUI(true);
        } else {
            // Signed out, show unauthenticated UI.
            updateUI(false);
        }
    }

    private void updateUI(boolean signedIn) {
        if (signedIn) {

            findViewById(R.id.sign_in_button).setVisibility(View.GONE);
            findViewById(R.id.id_sign_out_button).setVisibility(View.VISIBLE);
        } else {

            //Put into status view...

            mStatusUser.setText(R.string.signed_out);
            mStatusEmail.setText(R.string.signed_out2);
            findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
            findViewById(R.id.id_sign_out_button).setVisibility(View.GONE);
        }
    }
//
//
    //@Override
//    public void onStart() {
//        super.onStart();
//
//        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
//        if (opr.isDone()) {
//            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
//            // and the GoogleSignInResult will be available instantly.
//            Log.d(TAG, "Got cached sign-in");
//            GoogleSignInResult result = opr.get();
//            handleSignInResult(result);
//        } else {
//            // If the user has not previously signed in on this device or the sign-in has expired,
//            // this asynchronous branch will attempt to sign in the user silently.  Cross-device
//            // single sign-on will occur in this branch.
//            showProgressDialog();
//            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
//                @Override
//                public void onResult(GoogleSignInResult googleSignInResult) {
//                    hideProgressDialog();
//                    handleSignInResult(googleSignInResult);
//                }
//            });
//        }
//    }

//    private void showProgressDialog() {
//        if (mProgressDialog == null) {
//            mProgressDialog = new ProgressDialog(this);
//            mProgressDialog.setMessage(getString(R.string.loading));
//            mProgressDialog.setIndeterminate(true);
//        }
//
//        mProgressDialog.show();
//    }
//    private void hideProgressDialog() {
//        if (mProgressDialog != null && mProgressDialog.isShowing()) {
//            mProgressDialog.hide();
//        }
//    }
}