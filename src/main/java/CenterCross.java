import processing.core.PApplet;
import processing.core.PShape;

import java.sql.PseudoColumnUsage;

class CenterCross{
    PApplet applet;
    float southX= EK.southX;
    float southY= EK.southY;
    float northX= EK.northX;
    float northY= EK.northY;
    float eastX= EK.eastX;
    float eastY= EK.eastY;
    float westX= EK.westX;
    float westY= EK.westY;
    private PShape centerCrossLines[] = new PShape[4];
    CenterCross(PApplet a){
        this.applet=a;
        drawCenterCross();
    }
    private PShape createShape(){
        return applet.createShape();
    }
    private void shape(PShape s){
        applet.shape(s);
    }
    public void drawCenterCross() {
        Rectangle[] sQuares=EK.sQuares;
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

