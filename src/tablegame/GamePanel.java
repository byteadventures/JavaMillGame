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

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.awt.event.MouseMotionAdapter;
//--------------------------------------------------------------------------------------------------
public class GamePanel extends JPanel implements Runnable{

    private static final int PWIDTH = 600;//...........................................size.of.panel
    private static final int PHEIGHT = 600; 

//........................................no.of.frames.that.can.be.skipped.in.any.one.animation.loop
//...................................................i.e.the.games.state.is.updated.but.not.rendered
    private static final int MAX_FRAME_SKIPS = 5;

    private Thread animator;//................................the.thread.that.performs.the.animation
    private volatile boolean running = false;//....................used.to.stop.the.animation.thread
    private volatile boolean isPaused = false;

    private final long period;//................................period.between.drawing.in._nanosecs_
    private long gameStartTime;//............................................. when.the.game.started

//.............................................................................off-screen.rendering
    private Graphics dbg; 
    private Image dbImage = null;

    private final GameController GC;
    private final HUD Display;
    private DragPlayer DraggablePoint;
    private SmartJointFactory generatedJoints;

    int xforreset=0;
    int yforreset=0;
//--------------------------------------------------------------------------------------------------
    public GamePanel(TableGame br, long period){
        this.period = period;
        setDoubleBuffered(false);
        setBackground(Color.black);
        setPreferredSize( new Dimension(PWIDTH, PHEIGHT));
        setFocusable(true);
        requestFocus();//...........................the.JPane.now.has.focus,.so.receives.key.events
        generatedJoints= new SmartJointFactory(PHEIGHT);
        DraggablePoint = new DragPlayer();
        GC=new GameController(generatedJoints.Joints, DraggablePoint);
        Display=new HUD(300,300);
        Images.init();//...........................................................preload.resources
        Imagesets.init();
//...................................................................................event.listeners
        addMouseListener( new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e){ 
                GC.mPressed(e.getX(),e.getY());}
            @Override
            public void mouseReleased(MouseEvent e){
                GC.mReleased(e.getX(),e.getY());}
            @Override
            public void mouseClicked(MouseEvent e){
                GC.mClicked(e.getX(),e.getY());
                xforreset=e.getX();
                yforreset=e.getY();}
        });

        addMouseMotionListener(new MouseMotionAdapter(){
            @Override
            public void mouseDragged(MouseEvent e){
                if (GC.millevent==0) GC.UpdateMouseLocation(e.getX(),e.getY());}
        });
  }
//....................................wait.for.the.JPanel.to.be.added.to.the.JFrame.before.starting
    @Override
    public void addNotify(){
        super.addNotify();//.......................................................creates.the.peer
        startGame();//.............................................................start.the.thread
    }
//...................................................................initialise.and.start.the.thread 
    private void startGame(){
       if (animator == null || !running) {
          animator = new Thread(this);
          animator.start();
       }
    }

// -------------------------------------------------------------------------game-life-cycle-methods
//....................................................called.by.the.JFrame's.window.listener.methods

    public void resumeGame(){//....................called.when.the.JFrame.is.activated./.deiconified
        isPaused = false;
    }

    public void pauseGame(){//.....................called.when.the.JFrame.is.deactivated./.iconified
        isPaused = true;
    } 

    public void stopGame(){//......................................called.when.the.JFrame.is.closing
        running = false;   
    }
//--------------------------------------------------------------------------------------------------
    @Override   
//.......................................The.frame.of.the.animation.are.drawn.inside.the.while.loop.
    public void run(){
        long beforeTime, afterTime, timeDiff, sleepTime;
        long overSleepTime = 0L;
        long excess = 0L;

        gameStartTime = System.currentTimeMillis();
        beforeTime = gameStartTime;

        running = true;

        while(running) {
        gameUpdate();
        gameRender();
        paintScreen();//....................................................draw.the.buffer.onscrean

        afterTime = System.currentTimeMillis();
        timeDiff = afterTime - beforeTime;
        sleepTime = (period - timeDiff) - overSleepTime;

        if (sleepTime > 0) {//..........................................some.time.left.in.this.cycle
            try {
                Thread.sleep(sleepTime/1000000L);//.......................................nano -> ms
            }
            catch(InterruptedException ex){
                System.out.println("Error during thread sleep "+ex);
            }
        }

        beforeTime = System.currentTimeMillis();

//.....................................If frame animation is taking too long, update the game state
//...........................................without rendering it, to get the updates/sec nearer to
//.................................................................................the required FPS.

        int skips = 0;
        while((excess > period) && (skips < MAX_FRAME_SKIPS)) {
            excess -= period;
            gameUpdate();    // update state but don't render
            skips++;
        }
        }
        System.exit(0);   // so window disappears
    }

    private void gameUpdate() {
        if (!isPaused && !GC.getGameOver()) {
        GC.Update();
        Display.Update(GC.getInfoForHUD());
        }
        if (GC.getGameOver()){
            Display.Update(GC.getInfoForHUD());
            if ((xforreset<280+40)&&(xforreset>280))
                if ((yforreset<335+40)&&(yforreset>335))
                    GC.setReset();
        }
    }

    private void gameRender(){
        if (dbImage == null){
            dbImage = createImage(PWIDTH, PHEIGHT);
            if (dbImage == null) {
                System.out.println("dbImage is null");
                return;
            }
            else dbg = dbImage.getGraphics();
        }
//...............................................draw.the.background:.use.the.image.or.a.black.color
        dbg.drawImage(Images.background.getImage(), 0, 0, this);
        for (int i=0;i<24;i++){//......................................................render.joints
            generatedJoints.Joints[i].drawSprite(dbg);
        }
        DraggablePoint.drawSprite(dbg);
        Display.drawSprite(dbg);
        if(GC.getGameOver()) dbg.drawImage(Images.resumeok.getImage(),300-20,335,this);
        else dbg.drawImage(Images.resumenop.getImage(),300-20,335,this);
    }

    private void paintScreen(){//...........use.active.rendering.to.put.the.buffered.image.on-screen
        Graphics g;
        try{
            g = this.getGraphics();
            if ((g != null) && (dbImage != null))
                g.drawImage(dbImage, 0, 0, null);
//.................................................................Sync.the.display.on.some.systems.
//.......................................................(on.Linux,.this.fixes.event.queue.problems)
                Toolkit.getDefaultToolkit().sync();
                g.dispose();
        }
        catch (Exception e){ 
            System.out.println("Graphics context error: " + e);  
        }
    }
}
//--------------------------------------------------------------------------------------------------