import processing.core.PApplet;
import processing.core.PVector;

class CenterSquare extends RotatedSquare {

    CenterSquare(int i, PApplet a) {
        super(i, a);
        drawOutline();
    }
    public void setCorners() {
        corners[0] = new PVector(EK.halfW+EK.centerSquareSize, EK.halfH);
        corners[1] = new PVector(EK.halfW, EK.halfH+EK.centerSquareSize);
        corners[2] = new PVector(EK.halfW-EK.centerSquareSize, EK.halfH);
        corners[3] = new PVector(EK.halfW, EK.halfH-EK.centerSquareSize);
    }
}
