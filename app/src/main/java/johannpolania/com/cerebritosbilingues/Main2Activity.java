package johannpolania.com.cerebritosbilingues;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
    }

    public void Casa(View view) {
        Intent i = new Intent(this, HouseActivity.class );
        startActivity(i);
    }

    public void Cuerpo(View view) {
        Intent i = new Intent(this, BodyActivity.class );
        startActivity(i);
    }

    public void Frutas(View view) {
        Intent i = new Intent(this, FrutasActivity.class );
        startActivity(i);
    }

    public void Animales(View view) {
        Intent i = new Intent(this,AnimalsActivity.class );
        startActivity(i);
    }

    public void Profesiones(View view) {
        Intent i = new Intent(this, ReadJobsActivity.class );
        startActivity(i);
    }

    public void consultarPuntaje(View view) {
        Intent i = new Intent(this, ScoreActivity.class );
        startActivity(i);
    }


}
