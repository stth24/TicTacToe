package theissl.stefan.tictactoe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameOverActivity extends AppCompatActivity implements View.OnClickListener{

    Button btn_newGame, btn_restart;
    TextView txt_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameover);

        btn_restart = (Button) findViewById(R.id.btn_restart);
        btn_newGame = (Button)findViewById(R.id.btn_newGame);
        txt_info = (TextView)findViewById(R.id.txt_Info);

        Intent i = getIntent();
        final boolean singlePlayer = i.getBooleanExtra("singlePlayer",true);

        //create OnClickListener for the newGame Button
        btn_newGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GameOverActivity.this, WelcomeActivity.class);
                startActivity(intent);
                GameOverActivity.this.finish();
            }
        });

        btn_restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GameOverActivity.this, MainActivity.class);
                intent.putExtra("singlePlayer", singlePlayer);
                startActivity(intent);
                GameOverActivity.this.finish();
            }
        });


        txt_info.setText(i.getStringExtra("GameOverMessage"));
    }

    @Override
    public void onClick(View view) {

    }
}
