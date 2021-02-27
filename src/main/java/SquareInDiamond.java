import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

class SquareInDiamond extends Rectangle {


    SquareInDiamond(int i, PApplet a) {
        super(i, a);
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


        int index= id;
        v = EK.sQuares[1].corners[index];
        x1 = v.x;
        y1 = v.y;
        index = id>2?-1:id;//  -1 for at komme tilbage til hjørne nummer nul
        v = EK.sQuares[1].corners[index+1];
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
        ArrayList<PVector> points= new ArrayList();
        /* Draw second diagonal line through each square*/
        for (int i = 0; i < points.size(); i=i+2) {
            applet.line(points.get(i).x, points.get(i).y, points.get(i+1).x, points.get(i+1).y);
        }
    }

}