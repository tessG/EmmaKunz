import processing.core.PApplet;
import processing.core.PVector;

class RotatedSquare extends Rectangle{

    RotatedSquare(int i, PApplet a){
        super(i,a);
        drawOutline();

    }
    public void setCorners(){

        corners[0]= new PVector(northX,  northY);
        corners[1]= new PVector(eastX, eastY);
        corners[2]= new PVector( southX ,  southY);
        corners[3]= new PVector( westX,  westY);
    }

}
