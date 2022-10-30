package com.example.idnp_lab4_app1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Message;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static final String MESSAGE_ACTION = "com.app1.message";
    private TextView textResultado1;
    private TextView textResultado2;
    private MessageReceiver receiver1 = new MessageReceiver();
    private MessageReceiver receiver2 = new MessageReceiver();

    public class MessageReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals("com.app1.message")){
                textResultado1.setText(intent.getStringExtra("app"));
            } else if (intent.getAction().equals("com.app2.message")) {
                textResultado2.setText(intent.getStringExtra("app"));
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter filter1 = new IntentFilter("com.app2.message");
        IntentFilter filter2 = new IntentFilter("com.app1.message");
        registerReceiver(receiver1, filter1);
        registerReceiver(receiver2, filter2);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText editMensaje = findViewById(R.id.editMensaje);
        TextView textMensaje = findViewById(R.id.textMensaje);

        textResultado1 = findViewById(R.id.textResultado1);
        textResultado2 = findViewById(R.id.textResultado2);

        Button btnEnviar = (Button) findViewById(R.id.btnEnviar);

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mensaje1 = editMensaje.getText().toString();
                if(!(TextUtils.isEmpty(mensaje1))){
                    Intent intent = new Intent(MESSAGE_ACTION);
                    intent.putExtra("app", mensaje1);
                    sendBroadcast(intent);
                }
            }
        });
    }
}