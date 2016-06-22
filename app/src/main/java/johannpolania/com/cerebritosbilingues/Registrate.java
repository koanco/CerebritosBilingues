package johannpolania.com.cerebritosbilingues;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

public class Registrate extends AppCompatActivity {

    String passw1,passw2,usuario;
    EditText nombre,pass1,pass2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_registrate);
        nombre= (EditText) findViewById(R.id.editText);
        pass1= (EditText) findViewById(R.id.editText2);
        pass2= (EditText) findViewById(R.id.editText3);


    }

    public void savePrefs(View view){
        usuario = nombre.getText().toString();
        SharedPreferences mispreferencias = getSharedPreferences("PreferenciasUsuario", Context.MODE_PRIVATE);
        //SharedPreferences mispreferencias= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor edit=mispreferencias.edit();
        //EditText campo= (EditText) findViewById(R.id.editText);
        edit.putString("nombre", usuario);
        edit.putString("contraseña", passw1);
        edit.commit();
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(pass2.getWindowToken(), 0);
    }

    public void listo (View v){

        passw1= pass1.getText().toString();
        passw2= pass2.getText().toString();
        if (passw1.equals(passw2)){
            savePrefs(v);
            Toast.makeText(this, "Registro exitoso!", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(Registrate.this, MainActivity.class);
            startActivity(i);
            finish();

        } else {

            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            //Abrir la actividad
            Intent i = new Intent(this, Registrate.class);
            startActivity(i);
        }

    }






}
