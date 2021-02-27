import processing.core.PApplet;
import processing.core.PVector;

class Diamond extends Rectangle {

    Diamond(int i, PApplet a) {
        super(i, a);


    }

    public void setCorners() {
        Rectangle[]sQuares = EK.sQuares;

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

    }


    public void drawGrids(float space) {
        PVector v;

        //  for (int i = 0; i < gridLineNum; i++ ) {

        switch(id) {
            case 0:
                //To the border
                v = EK.sQuares[2+id].corners[1];
                line(eastX, eastY-space, v.x, v.y);
                line(northX+space, northY, v.x, v.y);
                //to the middle
                v = EK.sQuares[2+id].corners[3];
                line(EK.halfW+space, EK.halfH, v.x, v.y);
                line(EK.halfW, EK.halfH-space, v.x, v.y);
                break;
            case 1:
                v = EK.sQuares[2+id].corners[0];
                line(EK.halfW+space, EK.halfH, v.x, v.y);
                line(EK.halfW, EK.halfH+space, v.x, v.y);

                v = EK.sQuares[2+id].corners[2];
                line(eastX, eastY+space, v.x, v.y);
                line(southX+space, southY, v.x, v.y);
                break;
            case 2:
                v = EK.sQuares[2+id].corners[1];
                line(EK.halfW-space, EK.halfH, v.x, v.y);
                line(EK.halfW, EK.halfH+space, v.x, v.y);

                v = EK.sQuares[2+id].corners[3];
                line(westX, EK.halfH+space, v.x, v.y);
                line(southX-space, southY, v.x, v.y);
                break;
            case 3:

                v = EK.sQuares[2+id].corners[0];
                line(westX, westY-space, v.x, v.y);
                line(northX-space, northY, v.x, v.y);

                v = EK.sQuares[2+id].corners[2];
                line(EK.halfW-space, EK.halfH, v.x, v.y);
                line(EK.halfW, EK.halfH-space, v.x, v.y);
                break;

        }
    }

    public void drawDiamondDetail(int i) {

        PVector v = EK.sQuares[1].corners[id];
        float x1 = v.x;
        float y1 = v.y;
        int index = id>2?-1:id;
        v = EK.sQuares[1].corners[index+1];
        float x2 = v.x;
        float y2 = v.y;
        float medianX = (x2+x1)/2;
        float medianY = (y2+y1)/2;
        int spacing;

        switch(id) {
            case 0:
                //BOTTOM RIGHT
             //   for (int i = 0; i<EK.gridLineNum; i++) {
                    spacing =(i*EK.lineSpacing);
                    line(medianX, medianY, eastX, eastY-spacing);
                    line(medianX, medianY, EK.halfW, EK.halfH-spacing);
            //    }
                break;
            case 1:// TOP Right
            //   for (int i = 0; i<EK.gridLineNum; i++) {
                    spacing =(i*EK.lineSpacing);
                    line(medianX, medianY, southX+spacing, southY);
                    line(medianX, medianY, EK.halfW+spacing, EK.halfH);
               // }
                break;
            case 2:// TOP LEFT

            //    for (int i = 0; i<EK.gridLineNum; i++) {
                    spacing =(i*EK.lineSpacing);

                    line(medianX, medianY, EK.halfW-spacing, EK.halfH);
                    line(medianX, medianY, southX-spacing, southY);
                    line(medianX, medianY, westX, westY+spacing);
                    line(medianX, medianY, EK.halfW, EK.halfH+spacing);
                //}

                break;

            case 3://BOTTOM LEFT
                Rectangle square = EK.sQuares[0];
             //   for (int i = 0; i<EK.gridLineNum; i++) {
                    line(square.corners[2].x,
                            square.corners[2].y+(8*i),
                            EK.northX,
                            EK.northY+(8*i));
                    line(square.corners[2].x+(8*i),
                            square.corners[2].y,
                            EK.westX+(8*i),
                            EK.westY);
                //}
                square = EK.sQuares[5];
                square.drawOutline();
                break;

        }
    }
    public void drawTinyDetails(int i){
        Rectangle square = EK.sQuares[5];
        line(square.corners[0].x+(4*i), square.corners[0].y, square.corners[3].x+(4*i), square.corners[3].y);
        line(square.corners[0].x, square.corners[0].y+(4*i), square.corners[1].x, square.corners[1].y+(4*i));
    }
}
