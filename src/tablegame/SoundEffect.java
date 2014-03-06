/*
*Copyright (C) 2014  Zoltán Bíró

*This program is free software: you can redistribute it and/or modify
*it under the terms of the GNU General Public License as published by
*the Free Software Foundation, either version 3 of the License, or
*(at your option) any later version.
*
*This program is distributed in the hope that it will be useful,
*but WITHOUT ANY WARRANTY; without even the implied warranty of
*MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
*GNU General Public License for more details.

*You should have received a copy of the GNU General Public License
*along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

//--------------------------------------------------------------------------------------------------
package tablegame;

import javax.sound.sampled.*;

/*
* This enum encapsulates all the sound effects of a game, so as to separate the sound playing
* codes from the game codes.
* 1. Define all your sound effect names and the associated wave file.
* 2. To play a specific sound, simply invoke SoundEffect.SOUND_NAME.play().
* 3. You might optionally invoke the static method SoundEffect.init() to pre-load all the
*    sound files, so that the play is not paused while loading the file for the first time.
*/
//--------------------------------------------------------------------------------------------------
public enum SoundEffect{felrakas("sounds/felrakas.wav"),jump("sounds/Jump.wav"),
                            levet("sounds/levet.wav"),
                            mill("sounds/mill.wav"),move("sounds/move.wav");

    private Clip clip;//....................................................every.enum.have.own.clip

    SoundEffect(String wavpath){//..................................................enum.constructor
        try {
            AudioInputStream stream;
            AudioFormat format;
            DataLine.Info info;
            stream = AudioSystem.getAudioInputStream(TableGame.class.getResource(wavpath));
            format = stream.getFormat();
            info = new DataLine.Info(Clip.class, format);
            clip = (Clip) AudioSystem.getLine(info);
            clip.open(stream);
        }
        catch (Exception e) {
            System.out.println("Error during sound loading! "+e);
        }
    }
//--------------------------------Play-or-Re-play-the-sound-effect-from-the-beginning,-by-rewinding.
       public void play() {
           try{
               if (clip.isRunning()) clip.stop();//..............Stop.the.player.if.it.is.still.running
               clip.setFramePosition(0);//......................................rewind.to.the.beginning
               clip.start();//............................................................Start.playing
           }
           catch(Exception e){
               System.out.println("Error during sound play! "+e);
           }
       }
//-------------------------------------------------------------------------------------------Preload
       static void init(){
           values();
       }

}
//--------------------------------------------------------------------------------------------------