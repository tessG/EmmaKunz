import processing.core.*;
import processing.data.*;
import processing.event.*;
import processing.opengl.*;

import java.util.HashMap;
import java.util.ArrayList;
import java.io.File;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;

public class EK extends PApplet {

    public static int total = 8;
    public static int lineSpacing = 8;
    public static int gridLineNum = 28; //change this to 40, and get unpredictable patterns
    public static float halfW;
    public static float halfH;
    public static float centerSquareSize = 16;
    private static int oktagonSize = 330;
    /*   THE COLORS   */
    int blue;
    int black;
    int white;
    //color gray = color(145);
    int yellow;
    int red;
    int graphpaper;
    int gray;
/* int blue = color(61, 109, 121);
    int black = color(0);
    int white = color(255);
    //color gray = color(145);
    int yellow = color(244, 215, 94, 200);
    int red = color(255, 0, 0, 200);
    int graphpaper = color(235, 221, 187);
    int gray =  color(80);*/

    int bgcolor;
    int linecolor;
    int starcolor;
    int diamondcolor;
    int diamonddetailcolor;
    /* SHAPE CONTAINERS  */

    static PShape oktagon[] = new PShape[8];
    static PShape squares[] = new PShape[8];
    static Rectangle sQuares[] = new Rectangle[8];

    //PShape diamonds[] = new PShape[4];
    static Diamond diamonds[] = new Diamond[4];
    static PShape diagonalCenter[] = new PShape[2];



    /*    THE CROSS
     These are important globals and will only be initialized after the cross has been drawn.
     Other elements are dependent on this.

     */
    static float southX;
    static float southY;
    static float northX;
    static float northY;
    static float eastX;
    static float eastY;
    static float westX;
    static float westY;

    int gridCounter;
    int innerGridCounter;
    int detailCounter;
    int tinyDetailCounter;

    boolean drawstate =true;

    public void setup() {
        //size(800, 800);

        setOriginalColors();
        background(bgcolor);
        stroke(linecolor);
        halfW = width/2;
        halfH = height/2;
        //  noFill();
        strokeWeight(0.5f);
        strokeJoin(ROUND);

    }
    int startCounter = 0;
    public void startOver() {
        // if(drawstate == false){

        // drawstate = true;
        gridCounter = 0;
        innerGridCounter = 0;
        detailCounter = 0;
        tinyDetailCounter = 0;
        frameCount = 0;
        System.out.println(startCounter+" : modulus 3 = "+startCounter % 3);
        switch(startCounter % 3){
            case 0: setRandomGrayscaleColors();break;
            case 1: setRandomColors();break;
            case 2: setOriginalColors();break;
        }
        stroke(linecolor);
        background(bgcolor);

        startCounter++;

        //      loop();
        //  }else{
        //      drawstate = false;
        //      noLoop();
        //  }
    }
    public void mouseClicked() {
        drawstate= !drawstate;
        if(drawstate== false){
            noLoop();
        }else{
            loop();
        }
    }

    //
    public void draw() {

        // if(drawstate==true) {
        if (frameCount == 5) {
            drawOktagon();
        }else if(frameCount == 10){
            drawSquare();

        } else if (frameCount > 20 && gridCounter < gridLineNum) {
            Rectangle square = sQuares[0];
            drawOuterGrid(square);
            gridCounter++;
        } else if (frameCount == 50) {
            drawRotatedSquare();
        } else if (frameCount == 75) {
            drawCenterSquares();
        } else if (frameCount == 100) {
            drawCenterCross();
        } else if (frameCount == 125) {
            drawFourSquares();
            // change colors for the diamonds coming up next
            stroke(linecolor);
            fill(diamondcolor);
        } else if (frameCount == 150) {
            diamonds[0] = new Diamond(0, this);
            diamonds[0].drawOutline();

        } else if (frameCount == 170) {
            diamonds[1] = new Diamond(1, this);
            diamonds[1].drawOutline();

        } else if (frameCount == 180) {
            diamonds[2] = new Diamond(2, this);
            diamonds[2].drawOutline();

        } else if (frameCount == 199) {
            diamonds[3] = new Diamond(3, this);
            diamonds[3].drawOutline();

        } else if (frameCount == 200) {
            noFill();
            reDrawFourSquares();
        } else if (frameCount > 200 && innerGridCounter < gridLineNum) {
            for (int i = 0; i < 4; i++) {
                float space = (innerGridCounter * lineSpacing);
                diamonds[i].drawGrids(space);
            }
            innerGridCounter++;
        } else if (frameCount == 250) {
            drawCenterDiagonals();
        } else if (frameCount > 300 && detailCounter < gridLineNum) {
            fill(diamondcolor);
            drawDiamondDetails(0, detailCounter);
            drawDiamondDetails(1, detailCounter);
            drawDiamondDetails(2, detailCounter);
            drawDiamondDetails(3, detailCounter);
            detailCounter++;
        } else if (frameCount > 350 && tinyDetailCounter < 12) {
            drawTinyDetails(3, tinyDetailCounter);
            tinyDetailCounter++;
        } else if (frameCount == 600) {
            // noLoop();
            startOver();
        }
        // }

    }
    public void setOriginalColors(){
        /*   THE COLORS   */

        blue = color(61, 109, 121);
        black = color(0);
        white = color(255);
        //color gray = color(145);
        yellow = color(244, 215, 94, 200);
        red = color(255, 0, 0, 200);
        graphpaper = color(235, 221, 187);
        gray =  color(80);
        bgcolor = graphpaper;
        linecolor = blue;
        starcolor = red;
        diamondcolor = yellow;
        diamonddetailcolor = black;
        background(bgcolor);
        noFill();
    }
    public void setRandomGrayscaleColors(){
        /*   THE COLORS   */
        bgcolor = color(random(255));
        linecolor = color(random(255));
        starcolor = color(random(255));
        diamondcolor = color(random(255));
        diamonddetailcolor = color(random(255));
        background(bgcolor);
        noFill();

    }
    public void setRandomColors(){
        /*   THE COLORS   */
        bgcolor =  color(random(155),random(255),random(55));
        linecolor =  color(random(55),random(155),random(255));
        starcolor = color(random(255),random(55),random(155));
        diamondcolor  = color(random(255),random(255),random(255));
        //color gray = color(145);
        diamonddetailcolor =color(random(255),random(255),random(255));
        background(bgcolor);
        noFill();


    }
    public void drawDiamondDetails(int i, int j) {
        strokeWeight(0.3f);
        stroke(diamonddetailcolor);
        diamonds[i].drawDiamondDetail(j);

    }
    public void drawTinyDetails(int i, int j) {
        strokeWeight(0.3f);
        stroke(diamonddetailcolor);
        diamonds[i].drawTinyDetails(j);

    }
    public void drawCenterSquares() {
        stroke(linecolor);
        sQuares[6] = new CenterSquare(0, this);
        rectMode(CENTER);
        fill(linecolor);
        rect(halfW, halfH, centerSquareSize, centerSquareSize);
    }
    public void drawOktagon() {
        PShape l;
        for (int i =0; i<total; i++) {
            l = createShape();
            l.beginShape();
            placeShapeAtAngle((float)i, oktagonSize, l );
            l.endShape(CLOSE);
            oktagon[(int)i]=l;
            shape(l);
        }
    }
    public void placeShapeAtAngle(float i, int centerProx, PShape shape) {
        float angle = TWO_PI / (float) total;
        i = i+0.5f;
        float x1 = halfW + centerProx * sin(angle * i);
        float y1 = halfH + centerProx * cos(angle * i);
        float x2 = halfW + centerProx * sin(angle * (i-1));
        float y2 = halfH + centerProx * cos(angle * (i-1));
        shape.vertex(x1, y1);
        shape.vertex(x2, y2);
    }
    public void drawSquare() {
        sQuares[0]=new Rectangle(0, this);
    }
    public void drawRotatedSquare() {
        sQuares[1]= new RotatedSquare(0, this);
    }
    public void drawFourSquares() {
        stroke(linecolor);
        for (int i = 0; i < 4; i++) {
            sQuares[2+i]= new SquareInDiamond(i,this);
        }
    }
    public void reDrawFourSquares() {
        for (int i = 0; i < 4; i++) {
            sQuares[2+i].drawOutline();
        }
    }
    public void drawCenterCross() {
        noFill();
        stroke(starcolor);
        new CenterCross(this);
    }
    public void drawCenterDiagonals() {
        stroke(starcolor);
        /* Diagonal west - east */
        Rectangle square = sQuares[5];
        float topLeftX= square.corners[1].x;
        float topLeftY= square.corners[3].y;

        square = sQuares[6];
        float northX= square.corners[3].x;
        float northY= square.corners[3].y;
        float eastX = square.corners[0].x;
        float eastY = square.corners[0].y;
        float southX = square.corners[1].x;
        float southY = square.corners[1].y;
        float westX= square.corners[2].x;
        float westY= square.corners[2].y;

        square = sQuares[3];
        float bottomRightX= square.corners[0].x;
        float bottomRightY= square.corners[0].y;

        diagonalCenter[0] = createShape();
        diagonalCenter[0].beginShape();
        diagonalCenter[0].vertex(topLeftX, topLeftY);
        diagonalCenter[0].vertex(northX, northY);
        diagonalCenter[0].vertex(eastX, eastY);
        diagonalCenter[0].vertex(bottomRightX, bottomRightY);
        diagonalCenter[0].vertex(southX, southY);
        diagonalCenter[0].vertex(westX, westY);
        diagonalCenter[0].vertex(topLeftX, topLeftY);
        shape( diagonalCenter[0] );

        square = sQuares[4];
        float bottomLeftX= square.corners[1].x;
        float bottomLeftY= square.corners[1].y;

        square = sQuares[2];
        float topRightX= square.corners[3].x;
        float topRightY= square.corners[3].y;
        diagonalCenter[1] = createShape();
        diagonalCenter[1].beginShape();
        diagonalCenter[1].vertex(bottomLeftX, bottomLeftY);
        diagonalCenter[1].vertex(westX, westY);
        diagonalCenter[1].vertex(northX, northY);
        diagonalCenter[1].vertex(topRightX, topRightY);
        diagonalCenter[1].vertex(eastX, eastY);
        diagonalCenter[1].vertex(southX, southY);
        diagonalCenter[1].vertex(bottomLeftX, bottomLeftY);
        shape( diagonalCenter[1]);
    }

    public void drawOuterGrid(Rectangle square) {
        line(oktagon[0].getVertex(0).x, oktagon[0].getVertex(0).y, square.corners[0].x- gridCounter*lineSpacing, square.corners[0].y);
        line(oktagon[1].getVertex(0).x, oktagon[1].getVertex(0).y, square.corners[0].x, square.corners[0].y-gridCounter*lineSpacing);
        line(oktagon[2].getVertex(0).x, oktagon[2].getVertex(0).y, square.corners[1].x, square.corners[1].y+gridCounter*lineSpacing);
        line(oktagon[3].getVertex(0).x, oktagon[3].getVertex(0).y, square.corners[1].x-gridCounter*lineSpacing, square.corners[1].y);
        line(oktagon[4].getVertex(0).x, oktagon[4].getVertex(0).y, square.corners[2].x+gridCounter*lineSpacing, square.corners[2].y);
        line(oktagon[5].getVertex(0).x, oktagon[5].getVertex(0).y, square.corners[2].x, square.corners[2].y+gridCounter*lineSpacing);
        line(oktagon[6].getVertex(0).x, oktagon[6].getVertex(0).y, square.corners[3].x, square.corners[3].y-gridCounter*lineSpacing);
        line(oktagon[7].getVertex(0).x, oktagon[7].getVertex(0).y, square.corners[3].x+gridCounter*lineSpacing, square.corners[3].y);

    }
    public static void setGlobalCompas(Rectangle r) {
        northX = halfW;
        northY = r.corners[2].y;
        eastX = r.corners[0].x;
        eastY = halfH;
        southX = halfW;
        southY = r.corners[0].y;
        westX = r.corners[2].x;
        westY = halfH;
    }

    public void settings() {  size(800,800); }
    static public void main(String[] passedArgs) {
        String[] appletArgs = new String[] { "EK" };
        if (passedArgs != null) {
            PApplet.main(concat(appletArgs, passedArgs));
        } else {
            PApplet.main(appletArgs);
        }
    }
}
