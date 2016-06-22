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
import java.util.Random;

public class AnimalsActivity extends AppCompatActivity implements View.OnTouchListener, View.OnDragListener {
    private String[] listaAnimales = {"bear", "pig", "cat", "fox", "dog", "monkey", "cow", "tiger","elephant","sheep"};
    private int[] listaAudios ={R.raw.bear,R.raw.pig,R.raw.cat,R.raw.fox,R.raw.dog,R.raw.monkey,R.raw.cow,R.raw.tiger,R.raw.elephant,R.raw.sheep};
    private int sonidoFallo=R.raw.fallo;
    private int opcion = 0;
    private int posicion=0;
    private int puntaje = 0;

    private Handler handler = new Handler();
    private final static long SPLASH_DELAY=5000;
    private ImageView opcion1,opcion2,opcion3;
    private ImageView fallo;
    private ArrayList<ImageView> listaImagenes = new ArrayList<ImageView>();
    private LinearLayout izquierda,centro,derecha,principal,padre;
    private ViewGroup linearBackup;
    private TextView tWord;
     private String nombre1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_animals);


        principal = (LinearLayout) findViewById(R.id.lprincipalAnimales);
        izquierda = (LinearLayout) findViewById(R.id.layout1Animales);
        centro = (LinearLayout) findViewById(R.id.layout2Animales);
        derecha = (LinearLayout) findViewById(R.id.layout3Animales);
        tWord = (TextView) findViewById(R.id.tWordAnimales);
        // fondo=(ImageView)findViewById(R.id.fondo);
        principal.setOnDragListener(this);
        findViewById(R.id.layoutSonidoAnimales).setOnDragListener(this);
        izquierda.setOnDragListener(this);
        centro.setOnDragListener(this);
        derecha.setOnDragListener(this);
        tWord.setText(listaAnimales[0]);
           fallo=(ImageView)findViewById(R.id.falloAnimals);
        padre=(LinearLayout)findViewById(R.id.lPadreAnimales);
        padre.setOnDragListener(this);
        cargarImagenes();
        cargarOpciones();
        MediaPlayer mp = MediaPlayer.create(this, listaAudios[0]);
        mp.start();

        SharedPreferences misPreferencias = getSharedPreferences("PreferenciasUsuario", Context.MODE_PRIVATE);
        nombre1= misPreferencias.getString("nombre","");



    }


    public boolean onDrag(View v, DragEvent event) {
        // TODO Auto-generated method stub

        View view = (View) event.getLocalState();
//        linearBackup=(ViewGroup)view.getParent();
        switch (event.getAction()) {
            case DragEvent.ACTION_DROP:
             /*  Toast.makeText(this,String.valueOf(izquierda.getChildCount()),Toast.LENGTH_SHORT).show();
                Toast.makeText(this,String.valueOf(centro.getChildCount()),Toast.LENGTH_SHORT).show();
                Toast.makeText(this,String.valueOf(derecha.getChildCount()),Toast.LENGTH_SHORT).show();
                Toast.makeText(this, String.valueOf(event.getX()), Toast.LENGTH_SHORT).show();
                Toast.makeText(this, String.valueOf(event.getY()), Toast.LENGTH_SHORT).show();
              */
                if(v.getId()!=R.id.lPadre && v.getId()!=R.id.lprincipalAnimales) {
                    //  Toast.makeText(this, "hola, soy tu padre", Toast.LENGTH_SHORT).show();

                }
                if(listaImagenes.get(posicion)!=null) {
                    try{
                        if (view.equals(listaImagenes.get(posicion)) && v.getId() == R.id.lprincipalAnimales) {
                            //Toast.makeText(this, String.valueOf(posicion), Toast.LENGTH_SHORT).show();
                            //    Toast.makeText(this, "Correcto!!", Toast.LENGTH_SHORT).show();
                            ViewGroup source = (ViewGroup) view.getParent();
                            if (source != null) {
                                source.removeView(view);
                                // view.setEnabled(false);
                                view.setVisibility(View.VISIBLE);
                                final LinearLayout target = (LinearLayout) v;
                                target.removeAllViewsInLayout();
                                target.addView(view);
                                // fondo.setVisibility(View.INVISIBLE);
                                posicion += 1;

                                //target.removeAllViewsInLayout();
                                //  fondo.setVisibility(View.VISIBLE);
                                 fallo.setVisibility(View.INVISIBLE);

                                if (posicion <=9) {
                                    tWord.setText(listaAnimales[posicion]);
                                    izquierda.clearDisappearingChildren();
                                    izquierda.removeAllViewsInLayout();
                                    derecha.removeAllViewsInLayout();
                                    centro.removeAllViewsInLayout();
                                    principal.removeAllViews();
                                    principal.removeAllViewsInLayout();
                                    cargarOpciones();

                                    MediaPlayer mp = MediaPlayer.create(this, listaAudios[posicion]);
                                    mp.start();
                                    //   principal.addView(fondo);


                                } else {
                                    actualizar();
                                    Toast.makeText(this, "Muy bien", Toast.LENGTH_SHORT).show();
                                    finish();

                                }

                            }

                        } else {

                            //Toast.makeText(this, "???", Toast.LENGTH_SHORT).show();

                            fallo.setVisibility(View.VISIBLE);
                            MediaPlayer mp = MediaPlayer.create(this, sonidoFallo);
                            mp.start();
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
                        Toast.makeText(this,String.valueOf("izquierda"+izquierda.getChildCount()),Toast.LENGTH_SHORT).show();
                        Toast.makeText(this,String.valueOf("centro"+centro.getChildCount()),Toast.LENGTH_SHORT).show();
                        Toast.makeText(this,String.valueOf("derecha"+derecha.getChildCount()),Toast.LENGTH_SHORT).show();


                        if(izquierda.getChildCount()==0)
                        {
                            Toast.makeText(this,"esta vacio izquierda",Toast.LENGTH_SHORT).show();
                            izquierda.addView(view);
                        }
                        else
                        if(derecha.getChildCount()==0)
                        {
                            Toast.makeText(this,"esta vacio izquierda",Toast.LENGTH_SHORT).show();
                            derecha.addView(view);

                        }
                        else if(centro.getChildCount()==0)
                        {
                            Toast.makeText(this,"esta vacio centro",Toast.LENGTH_SHORT).show();
                            centro.addView(view);


                        }


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
            case DragEvent.ACTION_DRAG_STARTED:

                break;
                       /*
                        case DragEvent.ACTION_DRAG_EXITED:
                Toast.makeText(this,"saliendo",Toast.LENGTH_SHORT).show();
                Toast.makeText(this,String.valueOf(event.getY()),Toast.LENGTH_SHORT).show();
                if(v.getId()!=R.id.lPadre && v.getId()!=R.id.lprincipal) {
                    if(event.getY()<=50)
                    {   Toast.makeText(this,"finalizando",Toast.LENGTH_SHORT).show();
                        ViewGroup source = (ViewGroup) view.getParent();
                        source.removeView(view);
                        view.setVisibility(View.VISIBLE);
                        view.setEnabled(true);
                        source.addView(view);

                        // Toast.makeText(this, "se ha arrastrado a donde estaba", Toast.LENGTH_SHORT).show();
                        source.setVisibility(view.VISIBLE);


                    }

                }
                break;
                */

            case DragEvent.ACTION_DRAG_ENDED:
                //Toast.makeText(this,"finalizando",Toast.LENGTH_SHORT).show();
             /*   if(v.getId()!=R.id.lPadre && v.getId()!=R.id.lprincipal) {
                    if(event.getY()<=50)
                    {   Toast.makeText(this,"finalizando",Toast.LENGTH_SHORT).show();
                        ViewGroup source = (ViewGroup) view.getParent();
                        source.removeView(view);
                        view.setVisibility(View.VISIBLE);
                        view.setEnabled(true);
                        source.addView(view);

                        // Toast.makeText(this, "se ha arrastrado a donde estaba", Toast.LENGTH_SHORT).show();
                        source.setVisibility(view.VISIBLE);


                    }

                }
                */
                break;


        }


        return true;
    }




    private void cargarOpciones()
    {
        Random r = new Random();
        int ubicacionCorrecta = r.nextInt(3);
        switch (ubicacionCorrecta){
            case 0:
                organizarLayout(izquierda,centro,derecha);
                break;
            case 1:
                organizarLayout(centro,izquierda,derecha);
                break;
            case 2:
                organizarLayout(derecha,izquierda,centro);
                break;


        }






    }


    private void organizarLayout(LinearLayout l1, LinearLayout l2, LinearLayout l3)
    {   int aux1=0,aux2=0;
        cargarLayout(l1,this.posicion);
        do{
            Random r=new Random();
            aux1=r.nextInt(10);


        }
        while(aux1==posicion);
        cargarLayout(l2,aux1);
        do{
            Random r=new Random();
            aux2=r.nextInt(10);


        }
        while(aux2==posicion || aux2==aux1);
        cargarLayout(l3,aux2);

    }

    private void cargarLayout(LinearLayout layout, int posicion)
    {
        // layout.removeAllViewsInLayout();
        layout.addView(listaImagenes.get(posicion));


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

                imagen.setImageResource(R.drawable.bear);
                break;
                case 1:
                    imagen.setImageResource(R.drawable.pig);
                    break;
                case 2:
                    imagen.setImageResource(R.drawable.cat);
                    break;
                case 3:
                    imagen.setImageResource(R.drawable.fox);
                    break;
                case 4:
                    imagen.setImageResource(R.drawable.dog);
                    break;
                case 5:
                    imagen.setImageResource(R.drawable.monkey);
                    break;
                case 6:
                    imagen.setImageResource(R.drawable.cow);
                    break;
                case 7:
                    imagen.setImageResource(R.drawable.tiger);
                    break;
                case 8:
                    imagen.setImageResource(R.drawable.elephant);
                    break;
                case 9:
                    imagen.setImageResource(R.drawable.sheep);
                    break;
            }
            listaImagenes.add(i,imagen);




        }

    }

    public boolean onTouch(View view, MotionEvent event) {
        // TODO Auto-generated method stub
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            linearBackup=(ViewGroup)view.getParent();
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


    public void reproducir(View view)
    {

        MediaPlayer mp = MediaPlayer.create(this, listaAudios[posicion]);
        mp.start();

    }

    public void actualizar()
    {


        Conexion puntaje=new Conexion(this,"bdScore",null,2);
        SQLiteDatabase bd=puntaje.getWritableDatabase();

        ContentValues registro=new ContentValues();
        registro.put("animales", 5);


        int cant=bd.update("puntaje", registro, "usuario='" + this.nombre1 + "'", null);

        bd.close();






    }



}
