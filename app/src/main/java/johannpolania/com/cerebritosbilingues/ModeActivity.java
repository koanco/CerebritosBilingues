package johannpolania.com.cerebritosbilingues;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ModeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode);
    }

    public void ControlParental(View view) {
        Intent i = new Intent(this, MainActivity.class );
        startActivity(i);
    }

    public void Jugar(View view) {
        Intent i = new Intent(this, Main2Activity.class );
        startActivity(i);
    }
}
