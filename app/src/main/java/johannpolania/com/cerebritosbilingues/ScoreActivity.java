package johannpolania.com.cerebritosbilingues;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ScoreActivity extends AppCompatActivity {

    private String nombre1;
private Cursor c;
private String resultado="";
    private TextView tResultado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        SharedPreferences misPreferencias = getSharedPreferences("PreferenciasUsuario", Context.MODE_PRIVATE);
        nombre1= misPreferencias.getString("nombre","");
         tResultado=(TextView)findViewById(R.id.tResultado);
     buscarPuntaje();
    }

    public void buscarPuntaje()

    {
        Conexion puntaje=new Conexion(this,"bdScore",null,2);
        SQLiteDatabase bd=puntaje.getWritableDatabase();

         c=bd.rawQuery("select * from puntaje where usuario='"+this.nombre1+"'",null);
        if(c.moveToFirst()==true)
        {
            Toast.makeText(this,c.getString(3),Toast.LENGTH_SHORT).show();
            if(c.getInt(1)==5)
            {
                resultado="Animales, calificación: 5 " +"\n";

            }
            if(c.getInt(2)==5)
            {
                resultado+="Profesiones, calificación: 5 " +"\n";

            }

            if(c.getInt(3)==5)
            {
                resultado+="Fruta, calificación: 5 " +"\n";

            }
            if(c.getInt(4)==5)
            {
                resultado+="Casa, calificación: 5 " +"\n";

            }

            if(c.getInt(5)==5)
            {
                resultado+="Cuerpo, calificación: 5 " +"\n";

            }




        }
        else
        {

            Toast.makeText(this,"No existe en la BD :(",Toast.LENGTH_SHORT).show();

        }

        tResultado.setText(resultado);

        bd.close();




    }


}
