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
public class HUD {

    private boolean isActive = true;
    
    BufferedImage image;
    BufferedImage[] imageset;

    BufferedImage hudimage = null;
    
    private int angleplaced1=0;
    private int angleplaced2=0;
    private int angleremoved1=0;
    private int angleremoved2=0;

    private int origx;
    private int origy;
    private int diam=175;
//--------------------------------------------------------------------------------------------------
    public HUD(int x, int y){
       origx=x;
       origy=y;
    }

    public void setActive(){isActive=true;}
 
    public void setNoActive(){isActive=false;}
  
    public void Update(int[] tmp){
        angleplaced1=20*tmp[0];
        angleplaced2=20*tmp[1];
        angleremoved1=20*tmp[2];
        angleremoved2=20*tmp[3];
    }

    public void Reset(){
        angleplaced1=0;
        angleplaced2=0;
        angleremoved1=0;
        angleremoved2=0;
    }

    public void drawSprite(Graphics g) {
        if (isActive) { 
            g.setColor(Color.gray);
            g.fillArc(origx-diam/2, origy-diam/2, diam, diam, 0, 360);
            g.setColor(Color.green);
            g.fillArc(origx-diam/2, origy-diam/2, diam, diam, 90+angleplaced1, 180-angleplaced1);
            g.fillArc(origx-diam/2, origy-diam/2, diam, diam, -90,angleremoved1 );
            g.setColor(Color.red);
            g.fillArc(origx-diam/2, origy-diam/2, diam, diam, -90+angleplaced2,180-angleplaced2 );
            g.fillArc(origx-diam/2, origy-diam/2, diam, diam, 90, angleremoved2);
            g.drawImage(Images.hud.getImage(),origx-90,origy-90,null);
        }
    }

}
//--------------------------------------------------------------------------------------------------