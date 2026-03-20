package com.ecocity;
//CÓDIGO QUE TOCA MÚSICA
//CRIA UMA INSTÂNCIA QUE CHAMA O ARQUIVO .WAV

import javax.sound.sampled.*;
import java.io.File;

public class AudioPlayer {

    private Clip clip;

    public void tocarLoop(String caminho) {
        try {
            File arquivo = new File(caminho);
            AudioInputStream audio = AudioSystem.getAudioInputStream(arquivo);

            clip = AudioSystem.getClip();
            clip.open(audio);

            // 🔊 volume mais baixo
            FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            volume.setValue(-20.0f); // diminui volume (ajuste se quiser)

            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void parar() {
        if (clip != null) {
            clip.stop();
        }
    }
}