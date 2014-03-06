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
public enum Images{background("images/backgroundv2.png"),
                   hud("images/hud.png"),resumeok("images/resumeok.png"),
                   resumenop("images/resumenop.png");

    private BufferedImage img;//................................................Image.for.every.enum

    Images(String imagepath){//.....................................................enum.constructor
        try{
        img =  ImageIO.read(TableGame.class.getResource(imagepath));
        }
        catch (Exception e) {
            System.out.println("Error during image loading! +e");
        }
    }
//-------------------------------------------------------------------------------------------Preload
    static void init(){
        values();
    }
//--------------------------------------------------------------------------------------Return-image
    public BufferedImage getImage(){
        return img;
    }	

}
//--------------------------------------------------------------------------------------------------