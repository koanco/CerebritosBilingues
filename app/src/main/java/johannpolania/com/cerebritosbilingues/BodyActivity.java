package johannpolania.com.cerebritosbilingues;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class BodyActivity extends AppCompatActivity implements View.OnTouchListener, View.OnDragListener  {
    private String[] listaPartes = {"Hair", "Eyes", "Mouth", "Nose", "Hand", "Feet", "Ear", "Eyebrow"};
    private int[] listaAudios ={R.raw.hair, R.raw.eyes,R.raw.mouth,R.raw.nose,R.raw.hand,R.raw.feet,R.raw.ear,R.raw.eyebrow};
    private int opcion = 0;
    private int posicion=0;
    private int puntaje = 0;

    private Handler handler = new Handler();
    private final static long SPLASH_DELAY=5000;
    private ImageView opcion1,opcion2,opcion3;
    private ArrayList<ImageView> listaImagenes = new ArrayList<ImageView>();
    private LinearLayout layout_body,layout_partes;
    private ViewGroup linearBackup;
    private TextView tWord;
    private String nombre1;



    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_body);



        layout_body = (LinearLayout) findViewById(R.id.lBody);
        layout_partes = (LinearLayout) findViewById(R.id.lpartesCuerpoHumano);
         tWord = (TextView) findViewById(R.id.tCuerpoHumano);
        // fondo=(ImageView)findViewById(R.id.fondo);
        layout_body.setOnDragListener(this);
        findViewById(R.id.layoutTextoCuerpo).setOnDragListener(this);
        findViewById(R.id.lpartesCuerpoHumano).setOnDragListener(this);
        findViewById(R.id.lPadreCuerpo).setOnDragListener(this);


        tWord.setText(listaPartes[0]);
        cargarImagenes();
        cargarParte();
        MediaPlayer mp = MediaPlayer.create(this, listaAudios[0]);
        mp.start();

        SharedPreferences misPreferencias = getSharedPreferences("PreferenciasUsuario", Context.MODE_PRIVATE);
        nombre1= misPreferencias.getString("nombre","");


    }

    private void cargarParte()
    {
        layout_partes.addView(listaImagenes.get(posicion));



    }

    private void cargarImagenes()
    {
        for(int i=0;i<10;i++)
        {
            final ImageView imagen=new ImageView(this);
            imagen.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if(event.getAction() == MotionEvent.ACTION_DOWN){
                        View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
                        v.startDrag(null, shadowBuilder, v, 0);
                        v.setVisibility(View.INVISIBLE);
                        return true;
                    }

                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        imagen.setVisibility(View.VISIBLE);

                    }



                    return true;


                }
            });
            switch (i)
            {case 0:

                imagen.setImageResource(R.drawable.hair);
                break;
                case 1:
                    imagen.setImageResource(R.drawable.eyes);
                    break;
                case 2:
                    imagen.setImageResource(R.drawable.mouth);
                    break;
                case 3:
                    imagen.setImageResource(R.drawable.nose);
                    break;
                case 4:
                    imagen.setImageResource(R.drawable.hand_left);
                    break;
                case 5:
                    imagen.setImageResource(R.drawable.feet);
                    break;
                case 6:
                    imagen.setImageResource(R.drawable.ear_left);
                    break;
                case 7:
                    imagen.setImageResource(R.drawable.eyebrow);
                    break;


            }
            listaImagenes.add(i,imagen);




        }





    }

    public boolean onDrag(View v, DragEvent event) {
        // TODO Auto-generated method stub

        View view = (View) event.getLocalState();
//
        switch (event.getAction()) {
            case DragEvent.ACTION_DROP:

                if(listaImagenes.get(posicion)!=null) {
                    try{
                        if (v.getId() == R.id.lBody) {

                            ViewGroup source = (ViewGroup) view.getParent();
                            if (source != null) {
                                source.removeView(view);

                                view.setVisibility(View.VISIBLE);
                                final LinearLayout target = (LinearLayout) v;

                              //  Toast.makeText(this,String.valueOf(view.getX()),Toast.LENGTH_SHORT).show();


                                //target.removeAllViewsInLayout();


                                switch (posicion)
                                {
                                    case 0:
                                            view.setX(-10);
                                            view.setY(-15);
                                        break;
                                    case 1:
                                        view.setX(-10);
                                        view.setY(-70);
                                        break;
                                    case 2:
                                        view.setX(-10);
                                        view.setY(-65);
                                        break;
                                    case 3:
                                        view.setX(-10);
                                        view.setY(-140);
                                        break;
                                    case 4:
                                        view.setX(-110);
                                        view.setY(90);
                                        break;
                                    case 5:
                                        view.setX(-15);
                                        view.setY(175);
                                        break;
                                    case 6:
                                        view.setX(-70);
                                        view.setY(-450);
                                        break;
                                    case 7:
                                        view.setX(-10);
                                        view.setY(-585);
                                        break;
                                }
                                target.addView(view);
                                view.setEnabled(false);


                                 posicion += 1;

                                if (posicion <= 7) {
                                    tWord.setText(listaPartes[posicion]);
                                    cargarParte();

                                    MediaPlayer mp = MediaPlayer.create(this, listaAudios[posicion]);
                                    mp.start();



                                } else {
                                    actualizar();
                                    finish();

                                    Toast.makeText(this, "Muy bien", Toast.LENGTH_SHORT).show();


                                }

                            }

                        } else {



                            ViewGroup source = (ViewGroup) view.getParent();
                            source.removeView(view);
                            view.setVisibility(View.VISIBLE);
                            view.setEnabled(true);
                            source.addView(view);

                            // Toast.makeText(this, "se ha arrastrado a donde estaba", Toast.LENGTH_SHORT).show();
                            source.setVisibility(view.VISIBLE);
                            return false;

                        }

                    }
                    catch (Exception ex)
                    {
                        Toast.makeText(this, "???", Toast.LENGTH_SHORT).show();
//                           linearBackup.addView(view);



                    }

                }



                else
                {

                    ViewGroup source = (ViewGroup) view.getParent();
                    source.removeView(view);
                    view.setVisibility(View.VISIBLE);
                    view.setEnabled(true);
                    source.addView(view);

                    // Toast.makeText(this, "se ha arrastrado a donde estaba", Toast.LENGTH_SHORT).show();
                    source.setVisibility(view.VISIBLE);


                }



                break;


        }


        return true;
    }

    public boolean onTouch(View view, MotionEvent event) {
        // TODO Auto-generated method stub
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            Toast.makeText(this,"hola",Toast.LENGTH_SHORT).show();
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
            view.startDrag(null, shadowBuilder, view, 0);
            view.setVisibility(View.INVISIBLE);
            return true;
        }

        if (event.getAction() == MotionEvent.ACTION_UP) {
            view.setVisibility(View.VISIBLE);

        }

        return false;
    }

    public void actualizar()
    {


        Conexion puntaje=new Conexion(this,"bdScore",null,2);
        SQLiteDatabase bd=puntaje.getWritableDatabase();

        ContentValues registro=new ContentValues();
        registro.put("cuerpo", 5);


        int cant=bd.update("puntaje",registro,"usuario='"+this.nombre1+"'",null);

        bd.close();






    }




}
