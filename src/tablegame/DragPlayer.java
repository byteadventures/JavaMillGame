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
//-------------------------------------------------------------------------------------------------
public class DragPlayer {

    private boolean isActive = false;

    private int posx;
    private int posy;
    private int origx;
    private int origy;
//--------------------------------------------------------------------------------------------------
    public void setActive(){isActive=true;}
 
    public void setNoActive(){isActive=false;}

    public void Update(int x, int y){
        posx=x; 
        posy=y;
    }
//--------------------------------------------------------------------------------------------------
    public void setOrig(int x, int y){
        origx=x;
        origy=y;
    }
//--------------------------------------------------------------------------------------------------
    public void drawSprite(Graphics g){
        if (isActive) { 
            try{
                g.setColor(Color.magenta);
                g.drawLine(origx, origy, posx, posy);
            }
            catch(Exception e){
            	System.out.println("Error during Draggable drawing! "+e);
            }
        }
    }
}
//--------------------------------------------------------------------------------------------------