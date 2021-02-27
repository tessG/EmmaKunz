import processing.core.*;



class Rectangle {
    PVector corners[]= new PVector[4];
    int id;
    float squareSize;
    PApplet applet;
    float southX= EK.southX;
    float southY= EK.southY;
    float northX= EK.northX;
    float northY= EK.northY;
    float eastX= EK.eastX;
    float eastY= EK.eastY;
    float westX= EK.westX;
    float westY= EK.westY;


    Rectangle(int id, PApplet a) {
        this.id=id;
        this.applet = a;
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
        for (int i = 0; i < EK.oktagon.length; i++) {
            if (i % 2 == 1) {
                v = EK.oktagon[i].getVertex(0);
                x1 = v.x;
                y1 = v.y;
                v = EK.oktagon[i].getVertex(1);
                x2 = v.x;
                y2 = v.y;
                medianX = (x2+x1)/2;
                medianY = (y2+y1)/2;
                corners[index]=new PVector(medianX, medianY);
                index++;
            }
        }
        drawOutline();
        EK.setGlobalCompas(this);
    }

    public void drawOutline() {

        PShape s= applet.createShape();
        s.beginShape();
        for (int i = 0; i < 4; i++) {
            s.vertex(corners[i].x, corners[i].y);
        }
        s.endShape(applet.CLOSE);
        applet.shape(s);
    }
    public void line(float x,float y,float w,float h){
        applet.line(x,y,w,h);
    }
}

