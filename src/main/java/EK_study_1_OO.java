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

public class EK_study_1_OO extends PApplet {

    int total = 8;
    int lineSpacing = 8;
    int gridLineNum = 27; //change this to 40, and get unpredictable patterns
    float halfW;
    float halfH;
    float centerSquareSize = 16;

    /*   THE COLORS   */
    int blue = color(61, 109, 121);
    int black = color(0);
    int white = color(255);
    //color gray = color(145);
    int yellow = color(244, 215, 94, 200);
    int red = color(255, 0, 0, 200);
    int graphpaper = color(235, 221, 187);
    int gray =  color(80);


    int bgcolor = graphpaper;
    int linecolor = blue;
    int starcolor = red;
    int diamondcolor = yellow;
    int diamonddetailcolor = black;
    /* SHAPE CONTAINERS  */

    PShape oktagon[] = new PShape[8];
    PShape squares[] = new PShape[8];
    Rectangle sQuares[] = new Rectangle[8];
    PShape centerCrossLines[] = new PShape[4];
    //PShape diamonds[] = new PShape[4];
    Diamond diamonds[] = new Diamond[4];
    PShape diagonalCenter[] = new PShape[2];
//int i;

    /*    THE CROSS
     These are important globals and will only be initialized after the cross has been drawn.
     Other elements are dependent on this.

     */
    float southX;
    float southY;
    float northX;
    float northY;
    float eastX;
    float eastY;
    float westX;
    float westY;

    int gridCounter=0;
    int innerGridCounter=0;
    public void setup() {
        //size(800, 800);

        background(bgcolor);
        halfW = width/2;
        halfH = height/2;

        noFill();

        stroke(linecolor);
        strokeWeight(0.5f);
        strokeJoin(ROUND);

        drawOktagon();
    }
    public void draw() {
        if (frameCount==5) {
            drawSquare();
        } else if (frameCount>10 && gridCounter <gridLineNum) {
            Rectangle square = sQuares[0];
            drawOuterGrid(square);
            gridCounter++;
        } else if (frameCount==50) {
            drawRotatedSquare();
        } else if (frameCount==100) {
            drawCenterSquares();
        } else if (frameCount==150) {
            drawCenterCross();
        } else if (frameCount==200) {
            drawFourSquares();
        } else if (frameCount==250) {
            drawFourDiamonds();//includes the innergrids
            reDrawFourSquares();
        } else if (frameCount>300 && innerGridCounter < gridLineNum) {

            for (int i = 0; i < 4; i++ ) {
                float space =(innerGridCounter*lineSpacing);
                diamonds[i].drawGrids(space);
            }
            innerGridCounter++;
        } else if (frameCount==350) {
            drawCenterDiagonals();
            //
        } else if (frameCount==400) {
            drawDiamondDetails();
        } else if (frameCount==450) {

            // diamonds[frameCount-500].drawDiamondGrids();
            noLoop();
        }
    }
    public void drawDiamondDetails() {
        strokeWeight(0.3f);
        for (int i = 0; i < 4; i++ ) {
            diamonds[i].drawDiamondDetail();
        }
    }

    public void drawCenterSquares() {
        stroke(linecolor);
        sQuares[6] = new CenterSquare(0);
        rectMode(CENTER);
        fill(linecolor);
        rect(halfW, halfH, centerSquareSize, centerSquareSize);
    }
    public void drawOktagon() {
        PShape l;
        for (int i =0; i<total; i++) {
            // drawShapeAtAngle(i, 326, 20);
            l = createShape();
            l.beginShape();
            placeShapeAtAngle((float)i, 326, l );
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
        sQuares[0]=new Rectangle(0);
    }
    public void drawRotatedSquare() {
        sQuares[1]= new RotatedSquare(0);
    }
    public void drawFourSquares() {
        for (int i = 0; i < 4; i++) {
            sQuares[2+i]= new SquareInDiamond(i);
        }
    }
    public void reDrawFourSquares() {
        for (int i = 0; i < 4; i++) {
            sQuares[2+i].drawOutline();
        }
    }
    public void drawCenterCross() {
        new CenterCross();
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


    public void drawFourDiamonds() {
        stroke(linecolor);
        for (int i = 0; i < 4; i++ ) {
            diamonds[i] = new Diamond(i);
        }

        //drawRotatedSquare();//redraw because the color on the diamonds will cover it up
        //drawFourSquares();//redraw because the color on the diamonds will cover it up
    }
//void drawDiamondGrids() {
//  for (int i = 0; i < 4; i++ ) {

    //  }
//}
//float fromX, float fromY, float toX, float toY
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
    class CenterCross{

        CenterCross(){
            drawCenterCross();
        }
        public void drawCenterCross() {
            noFill();
            stroke(starcolor);
/*
  Rectangle s= (RotatedSquare) sQuares[1];//Bruger RotatedSquare til at mål
  northX = s.corners[0].x;
  northY = s.corners[0].y;
  //squares[1].getVertexY(0);
  southX = s.corners[2].x;
  southY = s.corners[2].y;
  eastX = s.corners[1].x;
  eastY =s.corners[1].y;
  westX = s.corners[3].x;
  westY = s.corners[3].y;*/

            /* The midle lines of the cross */
            centerCrossLines[0] = createShape();
            centerCrossLines[0].beginShape();
            centerCrossLines[0].vertex(southX, southY);
            centerCrossLines[0].vertex(northX, northY);
            centerCrossLines[0].endShape();
            shape(centerCrossLines[0]);
            /* The midle lines of the cross */
            centerCrossLines[1] = createShape();
            centerCrossLines[1].beginShape();
            centerCrossLines[1].vertex(eastX, eastY);
            centerCrossLines[1].vertex(westX, westY);
            centerCrossLines[1].endShape();
            shape(centerCrossLines[1]);
            /* north south*/
            centerCrossLines[2] = createShape();
            centerCrossLines[2].beginShape();
            centerCrossLines[2].vertex(southX, southY);
            centerCrossLines[2].vertex(sQuares[6].corners[2].x, sQuares[6].corners[2].y);
            centerCrossLines[2].vertex(northX, northY);
            centerCrossLines[2].vertex(sQuares[6].corners[0].x, sQuares[6].corners[0].y);
            centerCrossLines[2].vertex(southX, southY);
            centerCrossLines[2].endShape();
            shape( centerCrossLines[2] );

            centerCrossLines[3] = createShape();
            centerCrossLines[3].beginShape();
            centerCrossLines[3].vertex(eastX, eastY);
            centerCrossLines[3].vertex(sQuares[6].corners[2].x, sQuares[6].corners[1].y);
            centerCrossLines[3].vertex(westX, westY);
            centerCrossLines[3].vertex(sQuares[6].corners[0].x, sQuares[6].corners[3].y);
            centerCrossLines[3].vertex(eastX, eastY);
            centerCrossLines[3].endShape();
            shape( centerCrossLines[3] );
        }
    }
    class CenterSquare extends RotatedSquare {

        CenterSquare(int i) {
            super(i);
            drawOutline();
        }
        public void setCorners() {
            corners[0] = new PVector(halfW+centerSquareSize, halfH);
            corners[1] = new PVector(halfW, halfH+centerSquareSize);
            corners[2] = new PVector(halfW-centerSquareSize, halfH);
            corners[3] = new PVector(halfW, halfH-centerSquareSize);
        }
    }
    class Diamond extends Rectangle {

        Diamond(int i) {
            super(i);

            noFill();
        }

        public void setCorners() {
            fill(diamondcolor);
            switch(id) {

                case 0:  //BOTTOM RIGHT
                    corners[1] = new PVector(sQuares[2+id].corners[1].x, sQuares[2+id].corners[1].y);
                    corners[3] = new PVector(sQuares[2+id].corners[3].x, sQuares[2+id].corners[3].y);
                    corners[0] = new PVector(northX, northY);
                    corners[2] = new PVector(eastX, eastY);

                    break;
                case 1: //TOP RIGHT
                    corners[1]=new PVector(sQuares[2+id].corners[0].x, sQuares[2+id].corners[0].y);
                    corners[3]=new PVector(sQuares[2+id].corners[2].x, sQuares[2+id].corners[2].y);
                    corners[0]=new PVector(eastX, eastY);
                    corners[2]=new PVector(southX, southY);


                    break;
                case 2: //BOTTOM LEFT
                    corners[1]=new PVector(sQuares[2+id].corners[1].x, sQuares[2+id].corners[1].y);
                    corners[3]=new PVector(sQuares[2+id].corners[3].x, sQuares[2+id].corners[3].y);
                    corners[0]=new PVector(southX, southY);
                    corners[2]=new PVector(westX, westY);

                    break;
                case 3:
                    corners[1]=new PVector(sQuares[2+id].corners[0].x, sQuares[2+id].corners[0].y);
                    corners[3]=new PVector(sQuares[2+id].corners[2].x, sQuares[2+id].corners[2].y);
                    corners[0]=new PVector(westX, westY);
                    corners[2]=new PVector(northX, northY);

                    break;
            }
            drawOutline();
        }


        public void drawGrids(float space) {
            PVector v;

            //  for (int i = 0; i < gridLineNum; i++ ) {

            switch(id) {
                case 0:
                    //To the border
                    v = sQuares[2+id].corners[1];
                    line(eastX, eastY-space, v.x, v.y);
                    line(northX+space, northY, v.x, v.y);
                    //to the middle
                    v = sQuares[2+id].corners[3];
                    line(halfW+space, halfH, v.x, v.y);
                    line(halfW, halfH-space, v.x, v.y);
                    break;
                case 1:
                    v = sQuares[2+id].corners[0];
                    line(halfW+space, halfH, v.x, v.y);
                    line(halfW, halfH+space, v.x, v.y);

                    v = sQuares[2+id].corners[2];
                    line(eastX, eastY+space, v.x, v.y);
                    line(southX+space, southY, v.x, v.y);
                    break;
                case 2:
                    v = sQuares[2+id].corners[1];
                    line(halfW-space, halfH, v.x, v.y);
                    line(halfW, halfH+space, v.x, v.y);

                    v = sQuares[2+id].corners[3];
                    line(westX, halfH+space, v.x, v.y);
                    line(southX-space, southY, v.x, v.y);
                    break;
                case 3:

                    v = sQuares[2+id].corners[0];
                    line(westX, westY-space, v.x, v.y);
                    line(northX-space, northY, v.x, v.y);

                    v = sQuares[2+id].corners[2];
                    line(halfW-space, halfH, v.x, v.y);
                    line(halfW, halfH-space, v.x, v.y);
                    break;

            }
        }

        public void drawDiamondDetail() {
            PVector v = sQuares[1].corners[id];
            float x1 = v.x;
            float y1 = v.y;
            int index = id>2?-1:id;
            v = sQuares[1].corners[index+1];
            float x2 = v.x;
            float y2 = v.y;
            float medianX = (x2+x1)/2;
            float medianY = (y2+y1)/2;
            int spacing;
            stroke(diamonddetailcolor);
            switch(id) {
                case 0:
                    //BOTTOM RIGHT
                    for (int i = 0; i<gridLineNum; i++) {
                        spacing =(i*lineSpacing);
                        line(medianX, medianY, eastX, eastY-spacing);
                        line(medianX, medianY, halfW, halfH-spacing);
                    }
                    break;
                case 1:// TOP Right
                    for (int i = 0; i<gridLineNum; i++) {
                        spacing =(i*lineSpacing);
                        line(medianX, medianY, southX+spacing, southY);
                        line(medianX, medianY, halfW+spacing, halfH);
                    }
                    break;
                case 2:// TOP LEFT

                    for (int i = 0; i<gridLineNum; i++) {
                        spacing =(i*lineSpacing);

                        line(medianX, medianY, halfW-spacing, halfH);
                        line(medianX, medianY, southX-spacing, southY);
                        line(medianX, medianY, westX, westY+spacing);
                        line(medianX, medianY, halfW, halfH+spacing);
                    }

                    break;

                case 3://BOTTOM LEFT
                    Rectangle square = sQuares[0];
                    for (int i = 0; i<27; i++) {
                        line(square.corners[2].x,
                                square.corners[2].y+(8*i),
                                northX,
                                northY+(8*i));
                        line(square.corners[2].x+(8*i),
                                square.corners[2].y,
                                westX+(8*i),
                                westY);
                    }

                    square = sQuares[5];
                    fill(diamondcolor);
                    square.drawOutline();
                    for (int i = 0; i<13; i++) {
                        line(square.corners[0].x+(4*i), square.corners[0].y, square.corners[3].x+(4*i), square.corners[3].y);
                        line(square.corners[0].x, square.corners[0].y+(4*i), square.corners[1].x, square.corners[1].y+(4*i));
                    }


                    break;
            }
        }
    }
    class Rectangle {
        PVector corners[]= new PVector[4];
        int id;
        float squareSize;

        Rectangle(int i) {
            id=i;
            setCorners();
        }

        public void setCorners() {
            PVector v;
            float x1 = 0;
            float y1 = 0;
            float x2 = 0;
            float y2 = 0;
            float medianX;
            float medianY;
            int index=0;
            for (int i = 0; i < oktagon.length; i++) {
                if (i % 2 == 1) {
                    v = oktagon[i].getVertex(0);
                    x1 = v.x;
                    y1 = v.y;
                    v = oktagon[i].getVertex(1);
                    x2 = v.x;
                    y2 = v.y;
                    medianX = (x2+x1)/2;
                    medianY = (y2+y1)/2;
                    corners[index]=new PVector(medianX, medianY);
                    index++;
                }
            }
            drawOutline();
            setGlobalCompas();
        }
        public void setGlobalCompas() {
            northX = halfW;
            northY = this.corners[2].y;
            //squares[1].getVertexY(0);
            eastX = this.corners[0].x;
            eastY = halfH;

            southX = halfW;
            southY = this.corners[0].y;

            westX =this.corners[2].x;
            westY = halfH;
        }
        public void drawOutline() {

            PShape s= createShape();
            s.beginShape();
            for (int i = 0; i < 4; i++) {
                s.vertex(corners[i].x, corners[i].y);
            }
            s.endShape(CLOSE);
            shape(s);
        }
    }

    class RotatedSquare extends Rectangle{

        RotatedSquare(int i){
            super(i);
            drawOutline();

        }
        public void setCorners(){

            corners[0]= new PVector(northX,   northY);
            corners[1]= new PVector(eastX, eastY);
            corners[2]= new PVector( southX ,  southY);
            corners[3]= new PVector( westX,  westY);
        }

    }
    class SquareInDiamond extends Rectangle {


        SquareInDiamond(int i) {
            super(i);
            drawOutline();
        }

        public void setCorners() {
            PVector v;
            float x1 = 0;
            float y1 = 0;
            float x2 = 0;
            float y2 = 0;
            float medianX;
            float medianY;
            float squareSize=46;

            stroke(linecolor);
            int index= id;
            v = sQuares[1].corners[index];
            x1 = v.x;
            y1 = v.y;
            index = id>2?-1:id;//  -1 for at komme tilbage til hjørne nummer nul
            v = sQuares[1].corners[index+1];
            x2 = v.x;
            y2 = v.y;
            medianX = (x2+x1)/2;
            medianY = (y2+y1)/2;

            corners[0] = new PVector(medianX-(squareSize/2), medianY-(squareSize/2));
            corners[1] = new PVector(medianX+(squareSize/2), medianY-(squareSize/2));
            corners[2] = new PVector(medianX+(squareSize/2), medianY+(squareSize/2));
            corners[3] = new PVector(medianX-(squareSize/2), medianY+(squareSize/2));
            //line(medianX-(squareSize/2), medianY+(squareSize/2),medianX-(squareSize/2), medianY-(squareSize/2));

        }
        public void drawDiagonals(){
            ArrayList <PVector> points= new ArrayList();
            /* Draw second diagonal line through each square*/
            for (int i = 0; i < points.size(); i=i+2) {
                line(points.get(i).x, points.get(i).y, points.get(i+1).x, points.get(i+1).y);
            }
        }

    }
    public void settings() {  size(400,600); }
    static public void main(String[] passedArgs) {
        String[] appletArgs = new String[] { "EK_study_1_OO" };
        if (passedArgs != null) {
            PApplet.main(concat(appletArgs, passedArgs));
        } else {
            PApplet.main(appletArgs);
        }
    }
}
