import java.awt.*;

public class GeneralsFunctions {
    static int[] listDoubleToInt(double[] l){
        int[] newList= new int[l.length];
        for(var i=0;i<l.length;i++){
            newList[i]= (int) l[i];
        }
        return newList;
    }
    public static Color darkenColor(Color color, int num){
        int b= color.getBlue();
        int g= color.getGreen();
        int r= color.getRed();
        if(r<num)r=num;
        if(b<num)b=num;
        if(g<num)g=num;
        if(r>255+num)r=255+num;
        if(b>255+num)b=255+num;
        if(g>255+num)g=255+num;
        return new Color(r-num,g-num,b-num);
    }
}
