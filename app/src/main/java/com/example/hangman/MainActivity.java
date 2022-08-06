package com.example.hangman;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    TextView display,user_lives,display2;
    char[] chosen_word,dash;
    String guess="";
    int word_length;
    int lives;
    int rnd;
    boolean game_over = false;
    String[] wordlist = {"abruptly", "absurd", "abyss","askew","avenue","awkward","azure","bagpipes","bandwagon",
            "banjo","beekeeper","blitz","blizzard","boggle","bookworm","boxcar","boxful","buckaroo","buffalo","buffoon",
            "buzzard","buzzing","buzzwords","caliph","cobweb","croquet","crypt","curacao","cycle","daiquiri",
            "dirndl","disavow","dizzying","duplex","dwarves","embezzle","equip","espionage","exodus","faking","fishhook","fixable",
            "fjord","flapjack","flopping","fluffiness","flyby","foxglove","frazzled","frizzled","fuchsia","funny","gabby","galaxy",
            "galvanize","gazebo","gizmo","glowworm","glyph","gnarly","gnostic","gossip","grogginess","haiku","haphazard",
            "hyphen","iatrogenic","icebox","injury","ivory","ivy","jackpot","jaundice","jawbreaker","jaywalk","jazziest","jazzy",
            "jelly","jigsaw","jinx","jockey","jogging","joking","jovial","joyful","juicy","jukebox","jumbo","kayak",
            "kazoo","keyhole","khaki","kilobyte","kiosk","kitsch","kiwifruit","klutz","knapsack","larynx","lengths","lucky","luxury",
            "lymph","marquis","matrix","megahertz","microwave","mnemonic","mystify","nightclub","nowadays",
            "nymph","onyx","ovary","oxidize","oxygen","pajama","peekaboo","phlegm","pixel","pizazz","pneumonia","phenomenon",
            "psyche","puppy","puzzling","quartz","queue","quips","quixotic","quiz","quizzes","quorum","razzmatazz","rhubarb","rhythm",
            "rickshaw","schnapps","scratch","shiv","snazzy","sphinx","spritz","squawk","staff","strength","strengths","stretch","stronghold",
            "subway","swivel","syndrome","thriftless","thumbscrew","topaz","transcript","transgress","transplant","twelfth",
            "unknown","unworthy","unzip","uptown","vaporize","vixen","vodka","voodoo","vortex","walkway","waltz","wave","wavy","waxy",
            "wellspring","wheezy","whiskey","whizzing","whomever","wimpy","witchcraft","wizard","woozy","wristwatch","xylophone","yachtsman","yippee",
            "yoked","youthful","yummy","zigzag","zilch","zipper","zodiac","zombie"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        display = findViewById(R.id.display);
        user_lives = findViewById(R.id.user_lives);
        display2 = findViewById(R.id.display2);
        rnd = new Random().nextInt(wordlist.length);
        chosen_word =  wordlist[rnd].toCharArray();
        dash = new char[chosen_word.length];
        lives = 6;
//        display.setText(chosen_word);
        for(int i = 0;i<chosen_word.length;i++){
            if(chosen_word[i] == 'a' || chosen_word[i] == 'e' || chosen_word[i] == 'i' || chosen_word[i] == 'o' ||chosen_word[i] == 'u'){
                dash[i] = chosen_word[i];
            }
            else {
                dash[i] = '_';
            }
        }
        display.setText(String.valueOf(dash));
        user_lives.setText(String.valueOf("Lives = " + lives));
    }
    public void reset(View v){
        lives = 6;
        guess = "";
        game_over = false;
        rnd = new Random().nextInt(wordlist.length);
        chosen_word =  wordlist[rnd].toCharArray();
        dash = new char[chosen_word.length];
        for(int i = 0;i<chosen_word.length;i++){
            if(chosen_word[i] == 'a' || chosen_word[i] == 'e' || chosen_word[i] == 'i' || chosen_word[i] == 'o' ||chosen_word[i] == 'u'){
                dash[i] = chosen_word[i];
            }
            else {
                dash[i] = '_';
            }
        }
        display.setText(String.valueOf(dash));
        user_lives.setText(String.valueOf("Lives = " + lives));
        display2.setText(String.valueOf("Welcome"));
    }
    public void onButtonClick(View v) {
        Button button = (Button) v;
        String btnText = button.getText().toString();
//        display.setText(dash.toString());
        if(btnText.contains("A") || btnText.contains("E") || btnText.contains("I") || btnText.contains("O") || btnText.contains("U")){
            guess += btnText.toLowerCase();
            display2.setText(guess);
            Toast.makeText(MainActivity.this,"You don't have to guess a vowel",Toast.LENGTH_SHORT).show();
            return;
        }
        if(guess.contains(btnText.toLowerCase())){
            Toast.makeText(MainActivity.this,"You have already guessed " + btnText,Toast.LENGTH_LONG).show();
            return;
        }
        guess += btnText.toLowerCase();
        display2.setText(guess);

        char[] text = btnText.toCharArray();
        text[0] = Character.toLowerCase(text[0]);
        int flag = 0;
        int flag2 = 0;
        word_length = chosen_word.length;
        if(!game_over){
            for(int i = 0; i < word_length; i++){
                if(chosen_word[i] == text[0]){
                    flag = 1;
                    dash[i] = chosen_word[i];
                }
            }
            if(flag == 0){
                lives -= 1;
            }
            for (char c : dash) {
                if (c == '_') {
                    flag2 = 1;
                }
            }
            if(lives == 0)
                game_over = true;
            display.setText(String.valueOf(dash));
            user_lives.setText(String.valueOf("Lives = " + lives));
            if(flag2 == 0){
                Toast.makeText(MainActivity.this,"You have guessed it right, You win",Toast.LENGTH_LONG).show();
                display.setText(String.valueOf(dash));
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        game_over = true;
                        guess = "";
                        reset(v);
                    }
                }, 3000);
            }
        }
        else {
            Toast.makeText(MainActivity.this,"You lose, The word was - " + String.valueOf(chosen_word),Toast.LENGTH_LONG).show();
            display.setText(String.valueOf(chosen_word));
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    game_over = true;
                    guess = "";
                    reset(v);
                }
            }, 3000);
        }
    }

}