import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Bar {
    private final int MARGIN = 1;
    private int x,y,width,value;
    private Color color;

    public Bar(int x,int y,int width, int value, Color color){
        this.x = x;
        this.y = y;
        this.width = width;
        this.value = value;
        this.color = color;
    }

    public void draw(Graphics g){
        g.setColor(color);
        g.fillRect(x+MARGIN,y-value+35,width-MARGIN*2,value);
        g.setFont(new Font("Arial",Font.PLAIN,(int)(width/2.5)));
        g.drawString(String.format("%d",this.value),x+MARGIN,y+65);
    }

    public void clear(Graphics g){
        g.setColor(ColorManager.CANVAS_BACKGROUND);
        g.fillRect(x+MARGIN,y-value+35,width-MARGIN*2,value);
        g.setFont(new Font("Arial",Font.PLAIN,(int)(width/2.5)));
        g.drawString(String.format("%d",this.value),x+MARGIN,y+65);

    }

    /**
     * @param color the color to set
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * @return the color
     */
    public Color getColor() {
        return color;
    }

    /**
     * @param value the value to set
     */
    public void setValue(int value) {
        this.value = value;
    }

    /**
     * @return the value
     */
    public int getValue() {
        return value;
    }
}
