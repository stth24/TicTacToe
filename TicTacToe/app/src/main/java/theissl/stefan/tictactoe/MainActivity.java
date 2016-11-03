package theissl.stefan.tictactoe;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button[] allFields, usedFields;
    Drawable defaultButtonBG;
    ListView lv;
    boolean playerOneIsPlaying, singlePlayer;
    int curZug;
    TextView txt_zug, txt_curPlayer;
    Button btn_singlePlayer, btn_twoPlayers, btn_1, btn_2, btn_3, btn_4, btn_5, btn_6, btn_7, btn_8, btn_9;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent i = getIntent();
        singlePlayer = i.getBooleanExtra("singlePlayer",true);

//        reference GUI Objects
        txt_zug = (TextView)findViewById(R.id.txt_zug);

        txt_curPlayer = (TextView)findViewById(R.id.txt_curPlayer);


//        //initialize usedFields Array
        usedFields = new Button[9];

        //reference the 9 TicTacToe Buttons
        btn_1 = (Button) findViewById(R.id.btn_1);
        defaultButtonBG = btn_1.getBackground(); //save the default color to get it back for a restart
        btn_2 = (Button) findViewById(R.id.btn_2);
        btn_3 = (Button) findViewById(R.id.btn_3);
        btn_4 = (Button) findViewById(R.id.btn_4);
        btn_5 = (Button) findViewById(R.id.btn_5);
        btn_6 = (Button) findViewById(R.id.btn_6);
        btn_7 = (Button) findViewById(R.id.btn_7);
        btn_8 = (Button) findViewById(R.id.btn_8);
        btn_9 = (Button) findViewById(R.id.btn_9);

        allFields = new Button[]{btn_1,btn_2,btn_3,btn_4,btn_5,btn_6,btn_7,btn_8,btn_9};

        //create OnClickListeners for all 9 Buttons
        for(Button btn : allFields)
        {
            btn.setOnClickListener(this);
        }

        newGame();
    }

    @Override
    public void onClick(View view) {
        Button curBtn = (Button) view;
        usedFields[curZug] = curBtn;
        mainGame(curBtn);
    }

    public void newGame() {


        //initialize all default values for a new Game
        usedFields = new Button[9];
        playerOneIsPlaying = true;
        txt_curPlayer.setText("Player 1 (X) am Zug");
        curZug = 0;
        txt_zug.setText("Zug: " + curZug);

//        btn_newGame.setEnabled(false);

        for (Button btn:allFields) {
            btn.setText("");
            btn.setEnabled(true);
            btn.setBackground(defaultButtonBG);
        }
    }



    public void mainGame(Button curBtn){
        curBtn.setText(playerOneIsPlaying ? "X" : "O"); //mark the used Field
        curBtn.setEnabled(false); //make sure the field can't be clicked again
        txt_zug.setText("Zug: " + (++curZug));

        if(!checkWinLoseStatus()) //check if someone won
        {
            if(playerOneIsPlaying){
                playerOneIsPlaying = false;
                txt_curPlayer.setText("Player 2 (O) am Zug");
                if(singlePlayer)
                    mainGame(AIenvironment());
            }
            else{
                playerOneIsPlaying = true;
                txt_curPlayer.setText("Player 1 (X) am Zug");
            }
//            if(!btn_newGame.isEnabled())
//                btn_newGame.setEnabled(true);

            if(curZug == 9){
                Intent intent = new Intent(getApplicationContext(), GameOverActivity.class);
                intent.putExtra("GameOverMessage", "Es gibt leider keinen Sieger - viel Glück beim nächsten mal.");
                intent.putExtra("singlePlayer", singlePlayer);
                startActivity(intent);
                MainActivity.this.finish();
            }
        }
    }

    public boolean checkWinLoseStatus(){
        boolean gameEnded = false;
        if(btn_1.getText() != ""  && btn_1.getText() == btn_2.getText() && btn_2.getText() ==  btn_3.getText())
        {
            btn_1.setBackgroundColor(Color.GREEN);
            btn_2.setBackgroundColor(Color.GREEN);
            btn_3.setBackgroundColor(Color.GREEN);
            gameEnded = true;
        }
        else if(btn_4.getText() != ""  && btn_4.getText() == btn_5.getText() && btn_5.getText() ==  btn_6.getText())
        {
            btn_4.setBackgroundColor(Color.GREEN);
            btn_5.setBackgroundColor(Color.GREEN);
            btn_6.setBackgroundColor(Color.GREEN);
            gameEnded = true;
        }
        else if(btn_7.getText() != ""  && btn_7.getText() == btn_8.getText() && btn_8.getText() ==  btn_9.getText())
        {
            btn_7.setBackgroundColor(Color.GREEN);
            btn_8.setBackgroundColor(Color.GREEN);
            btn_9.setBackgroundColor(Color.GREEN);
            gameEnded = true;
        }
        else if(btn_1.getText() != ""  && btn_1.getText() == btn_4.getText() && btn_4.getText() ==  btn_7.getText())
        {
            btn_1.setBackgroundColor(Color.GREEN);
            btn_4.setBackgroundColor(Color.GREEN);
            btn_7.setBackgroundColor(Color.GREEN);
            gameEnded = true;
        }
        else if(btn_2.getText() != ""  && btn_2.getText() == btn_5.getText() && btn_5.getText() ==  btn_8.getText())
        {
            btn_2.setBackgroundColor(Color.GREEN);
            btn_5.setBackgroundColor(Color.GREEN);
            btn_8.setBackgroundColor(Color.GREEN);
            gameEnded = true;
        }
        else if(btn_3.getText() != ""  && btn_3.getText() == btn_6.getText() && btn_6.getText() ==  btn_9.getText())
        {
            btn_3.setBackgroundColor(Color.GREEN);
            btn_6.setBackgroundColor(Color.GREEN);
            btn_9.setBackgroundColor(Color.GREEN);
            gameEnded = true;
        }
        else if(btn_1.getText() != ""  && btn_1.getText() == btn_5.getText() && btn_5.getText() ==  btn_9.getText())
        {
            btn_1.setBackgroundColor(Color.GREEN);
            btn_5.setBackgroundColor(Color.GREEN);
            btn_9.setBackgroundColor(Color.GREEN);
            gameEnded = true;
        }
        else if(btn_3.getText() != ""  && btn_3.getText() == btn_5.getText() && btn_5.getText() ==  btn_7.getText())
        {
            btn_3.setBackgroundColor(Color.GREEN);
            btn_5.setBackgroundColor(Color.GREEN);
            btn_7.setBackgroundColor(Color.GREEN);
            gameEnded = true;
        }

        if(gameEnded){
            for(Button btn:allFields)
                btn.setEnabled(false);
            Intent intent = new Intent(getApplicationContext(), GameOverActivity.class);
            intent.putExtra("GameOverMessage", playerOneIsPlaying ? "Spieler 1 hat gewonnen!" : "Spieler 2 hat gewonnen!");
            intent.putExtra("singlePlayer", singlePlayer);
            startActivity(intent);
            MainActivity.this.finish();
        }

        return gameEnded;
    }

    public Button AIenvironment() {
        //avoid user interruption while AI is working
        for(Button btn:allFields)
            btn.setEnabled(false);

        Button selectedButton = null;

        //call actual AI
        selectedButton = AI();
        //make sure selectedButton is marked as used
        usedFields[curZug] = selectedButton;

        //enable user interaction again
        for(Button btn:allFields)
            btn.setEnabled(true);
        //make sure already use Button can't be clicked again
        for(Button btn:usedFields)
            if(btn != null)
                btn.setEnabled(false);

        return selectedButton;
    }

    public Button AI(){
        Button selBtn = null;

        for(Button btn:allFields)
            if(btn.getText().length() == 0)
            {
                selBtn = btn;
                break;
            }

        return selBtn;
    }
}
