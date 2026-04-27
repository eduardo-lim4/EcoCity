package com.ecocity;
//CÓDIGO QUE TOCA MÚSICA
//CRIA UMA INSTÂNCIA QUE CHAMA O ARQUIVO .WAV

import javax.sound.sampled.*;

public class AudioPlayer {

    private Clip clip;

    public void tocarLoop(String caminho) {
        // Fecha clip anterior se existir
        if (clip != null) {
            clip.stop();
            clip.close();
        }

        try (AudioInputStream audio = AudioSystem.getAudioInputStream(
                AudioPlayer.class.getResource("/" + caminho))) {

            clip = AudioSystem.getClip();
            clip.open(audio);

            // Volume mais baixo
            FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            volume.setValue(-20.0f);

            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void parar() {
        if (clip != null) {
            clip.stop();
            clip.close();
        }
    }
}
