package game;
public class Obstacle {
    private int x,y;
    private int width,height;
    public Obstacle(int x, int y, int width, int height){
       this.x=x;
        this.y=y;
       this.width=width;
        this.height=height;
    }
    
    public boolean isInside(int x, int y){
        if(Math.abs(this.x-x)<width/2){
            if(Math.abs(this.y-y)<height/2){
                return true;
            }
        }
        return false;   
    }
}
