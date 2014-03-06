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

import java.awt.Container;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JFrame;
//--------------------------------------------------------------------------------------------------
public class TableGame extends JFrame implements WindowListener
{
  final private static int DEFAULT_FPS = 40; 
  final private GamePanel bp;//..............................................where.the.game.is.drawn
//--------------------------------------------------------------------------------------------------
  public TableGame(long period){
    super("MillGame");
    Container c = getContentPane();//.....................................default.BorderLayout.used
    bp = new GamePanel(this, period);//...............................magic.for.proper.window.format
    c.add(bp, "Center");
    addWindowListener( this );
    pack();
    setResizable(false);
    setVisible(true);
  }
//--------------------------------------------------------------------------window-listener-methods
  @Override
  public void windowActivated(WindowEvent e) {
    bp.resumeGame();  }

  @Override
  public void windowDeactivated(WindowEvent e) {
    bp.pauseGame();  }

 @Override
  public void windowDeiconified(WindowEvent e) {
    bp.resumeGame();  }

  @Override
  public void windowIconified(WindowEvent e) {
    bp.pauseGame(); }

  @Override
  public void windowClosing(WindowEvent e) {
    bp.stopGame();  
  }

  @Override
  public void windowClosed(WindowEvent e) {}
  @Override
  public void windowOpened(WindowEvent e) {}

  // -------------------------------------------------------------------------------------------main

  public static void main(String args[])
  { 
    long period = (long) 1000.0/DEFAULT_FPS;
    new TableGame(period*1000000L);//................................................ms.-->.nanosecs
  }

}
//--------------------------------------------------------------------------------------------------