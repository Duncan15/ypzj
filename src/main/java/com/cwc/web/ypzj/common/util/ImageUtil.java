package com.cwc.web.ypzj.common.util;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class ImageUtil {
    /*
    if the value is true,this format is legal
     */
    private static HashMap<String,Boolean> legalFormat=new HashMap<>();
    static {
        for(String each:ImageIO.getReaderFormatNames()){
            legalFormat.put(each,false);
        }
        for(String each:ImageIO.getWriterFormatNames()){
            if(legalFormat.get(each)!=null){
                legalFormat.put(each,true);
            }
        }
    }

    /*
    @return thumnail success or failure
     */
    public static boolean thumnailImage(File input,File output,float ratio){
        if(input.exists()){
            if(input.getName().indexOf(".")>-1){
                String sufix=input.getName().substring(input.getName().lastIndexOf(".")+1);
                if(legalFormat.get(sufix)!=null&&legalFormat.get(sufix)==true){
                    try{
                        Image sourceImg=ImageIO.read(input);
                        int sourceWidth=sourceImg.getWidth(null);
                        int sourceHeight=sourceImg.getHeight(null);
                        int targetWidth=(int)((sourceWidth*1.0)/ratio);
                        int targetHeight=(int)((sourceHeight*1.0)/ratio);
                        BufferedImage bufferedImage=new BufferedImage(targetWidth,targetHeight,BufferedImage.TYPE_INT_RGB);
                        Graphics g= bufferedImage.getGraphics();
                        g.drawImage(sourceImg,0,0,targetWidth,targetHeight,Color.lightGray,null);
                        g.dispose();
                        ImageIO.write(bufferedImage,sufix,output);
                        return true;
                    }catch (IOException e){
                        e.printStackTrace();
                    }

                }
            }

        }
        return false;
    }
}
