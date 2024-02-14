package com.aprendendo.wearos_audio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.AudioDeviceInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private AudioHelper audioHelper;
    private TexttoSpeech ttsManager; // Adicione esta linha

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = findViewById(R.id.textview1);
        Button btnBluetoothSettings = findViewById(R.id.btnBluetoothSettings);

        // Solicitar permissão para acessar as notificações
        //Intent intent = new Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS);
        //startActivity(intent);

        // Iniciar o serviço de escuta de notificações
        Intent serviceIntent = new Intent(this, NotificationService.class);
        startService(serviceIntent);

        audioHelper = new AudioHelper(this); // Inicialize o AudioHelper com o contexto da atividade
        ttsManager = new TexttoSpeech(this); // Inicialize o TTSManager com o contexto da atividade

        // Verifica se há saída de áudio disponível e atualiza o texto do TextView
        if (audioHelper.audioOutputAvailable(AudioDeviceInfo.TYPE_BLUETOOTH_A2DP)) {
            textView.setText("Conectado, estamos monitorando as notificações");
            btnBluetoothSettings.setVisibility(View.GONE); // Esconde o botão se um dispositivo estiver conectado
            // Falar o texto usando o TTSManager
            ttsManager.speak("Conectado, estamos monitorando as notificações");

        } else {
            textView.setText("Nenhum dispositivo de áudio Bluetooth conectado");
            btnBluetoothSettings.setVisibility(View.VISIBLE); // Mostra o botão se nenhum dispositivo estiver conectado
            // Adiciona um listener para o botão que abre as configurações Bluetooth
            btnBluetoothSettings.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    audioHelper.showBluetoothSettings();
                }
            });
        }

        // Falar o texto usando o TTSManager
        ttsManager.speak("Não há dispositivos de áudio Bluetooth conectados. Para conectar, basta pressionar o botão localizado no centro da tela, abaixo. Se precisar de ajuda, não hesite em pedir");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Desligar o TTSManager ao destruir a atividade
        ttsManager.shutdown();
    }
}


