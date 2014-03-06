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

import java.awt.image.*;
import javax.imageio.ImageIO;
//--------------------------------------------------------------------------------------------------
public enum Imagesets{players("images/imageset.png");

    private BufferedImage img;//...............................................Image.for.every.enum

    Imagesets(String imagepath){//..................................................enum.constructor
        try{
            img =  ImageIO.read(TableGame.class.getResource(imagepath));
        }
        catch (Exception e) {
        	System.out.println("Error during imageset loading!");
        }
    }
//-------------------------------------------------------------------------------------------------
    static void init(){//...................................................................preload
        values();
    }
//----------------------------------------------------------------------Create-sub-images-from-sheet
    public BufferedImage[] getImageSet(int offsetx, int offsety, int cnt){
        int width=img.getWidth();
        int heigh=img.getHeight();
        int subImagecnt=0;
        BufferedImage[] Imageset= new BufferedImage[cnt];
        try{
            for (int n=0;n<=heigh/offsety-1;n++){
                for(int m =0; m<=width/offsetx-1;m++){
                    Imageset[subImagecnt]=img.getSubimage(m*offsetx,n*offsety,offsetx,offsety);
                    subImagecnt++;
                    if (subImagecnt==cnt) break;
                }
                if (subImagecnt==cnt) break;
            }
        }
        catch (Exception e){
            System.out.println("Error during subimage creation! "+e);
        }
        return Imageset;
    }
}
//--------------------------------------------------------------------------------------------------