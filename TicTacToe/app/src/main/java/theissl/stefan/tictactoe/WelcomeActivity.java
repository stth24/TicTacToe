package theissl.stefan.tictactoe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener{

    Boolean singlePlayer;
    Button btn_singlePlayer, btn_twoPlayers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        btn_singlePlayer = (Button)findViewById(R.id.btn_singlePlayer);
        btn_twoPlayers = (Button)findViewById(R.id.btn_twoPlayer);

        btn_singlePlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                intent.putExtra("singlePlayer", true);
                startActivity(intent);
                WelcomeActivity.this.finish();
            }
        });

        btn_twoPlayers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                intent.putExtra("singlePlayer", false);
                startActivity(intent);
                WelcomeActivity.this.finish();
            }
        });
    }

    @Override
    public void onClick(View view) {

    }
}
