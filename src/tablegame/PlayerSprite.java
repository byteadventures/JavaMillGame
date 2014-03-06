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

import java.awt.*;
import java.awt.image.*;
//--------------------------------------------------------------------------------------------------
public class PlayerSprite 
{
//.....................................................................................for.animation
    private long framestarttime=0L;
    private long framecurrenttime;
    private final long frametime=100L;
    private int AnimFrom=1;
    private int AnimTo=5;
    private int FadeFrom;
    private int FadeTo;
    private int fadein=0;
    private int fadeout=0;
//--------------------------------------------------------------------------------------------------
    private int CurrentImage= 0;
    private boolean isActive = true;
    int image;
  
    BufferedImage[] imageset;
  
    private final int posx;
    private final int posy;
    private final int diam; 

    private int player=0;
    private int lastplayer=0;

    private final int ID;
    private int mill=0;//......................................................joint.part.of.n.mills
    private final int[] mill1=new int[2];//..................................joint.part.of.this.mill
    private final int[] mill2=new int[2];
    private final int[] neighbours = new int[4];

    final private int circledia=60;
//--------------------------------------------------------------------------------------------------
    public PlayerSprite(int x, int y, int d, BufferedImage[] img, int id) { 
        posx=x;
        posy=y;
        diam=d;
        ID=id;
        imageset=img;
    }

    public int ItIsMe(int x, int y){
     if ((x>posx)&&(x<posx+diam)&&(y>posy)&&(y<posy+diam)) return ID;
     else return -1;
    }

    public void setActive(){
    	isActive=true;
    }

    public void setNoActive(){isActive=false;}

    public void setPlayer(int pl){ 
        lastplayer=player; 
        player=pl;
    }

    public int getPlayer(){return player;}

    public int[] getMill1(){return mill1;}

    public int[] getMill2(){return mill2;}

    public void setMill1(int m1, int m2){mill1[0]=m1;mill1[1]=m2;}

    public void setMill2(int m1, int m2){mill2[0]=m1;mill2[1]=m2;}

    public void setNeighbours(int n1, int n2, int n3, int n4){
        neighbours[0]=n1;
        neighbours[1]=n2;
        neighbours[2]=n3;
        neighbours[3]=n4;
    }
 
    public boolean checkNeighbour(int released){
       for (int i=0;i<4;i++){
           if (neighbours[i]==released) return true;
       }
       return false;
    }

    public boolean checkMillStatus(){
        if (mill>0) return true;
        else return false;
    }

    public void clearPlayer(){lastplayer=player; player=0;}

    public void setMill(){mill++;}

    public void clearMill(){mill--;}

    public void resetMill(){mill=0;};

    public void Update(){
        if (player==1) {//........................................................Set.player1.images
            image=0;
            CurrentImage=0;
            AnimFrom=1;
            AnimTo=4;
            FadeFrom=10;
            FadeTo=14;
        }
        if (player==-2){//........................................................Set.player2.images
            image=5;
            CurrentImage=5;
            AnimFrom=6;
            AnimTo=9;
            FadeFrom=15;
            FadeTo=19;
        }
        if ((lastplayer==0)&&(player!=0)) {//...........................................Catch.fadein
            fadein=1;
            CurrentImage=FadeTo;
        }
        if ((lastplayer!=0)&&(player==0)) {//..........................................Catch.fadeout
            fadeout=1;
            CurrentImage=FadeFrom;
        } 
    }
 
    public void drawSprite(Graphics g) {
        this.Animator();
        g.setColor(Color.WHITE);
        g.fillArc(posx,posy,circledia,circledia,0,360);
        if ((player!=0)||(fadeout==1)) {
            if (isActive){ 
                g.setColor(Color.WHITE);
                g.fillArc(posx,posy,circledia,circledia,0,360);
                g.drawImage(imageset[CurrentImage], posx+3, posy+3, null);
            }
        }

    }
  
    private void Animator(){
        if (framestarttime==0L) framestarttime=System.currentTimeMillis();
        framecurrenttime=System.currentTimeMillis();
        if (framecurrenttime>=framestarttime+frametime){
            framestarttime=framecurrenttime;
            if (mill>0){//..............................................................Animate.mill
                CurrentImage++;
                if (CurrentImage>AnimTo) CurrentImage=AnimFrom;
            }
            if (fadeout==1){//.......................................................Animate.fadout
                CurrentImage++;
                if (CurrentImage>FadeTo) {CurrentImage=image; fadeout=0;}
            }
            if (fadein==1){//........................................................Animate.fadein
                CurrentImage--;
                if (CurrentImage<FadeFrom) {CurrentImage=image; fadein=0;}
            }
        }
    }

}
//--------------------------------------------------------------------------------------------------