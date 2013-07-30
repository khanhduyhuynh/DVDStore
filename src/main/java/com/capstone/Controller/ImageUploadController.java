/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.capstone.Controller;

import java.awt.AlphaComposite;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;

/**
 *
 * @author duy
 */
public class ImageUploadController 
{
    private static int IMG_LOGO_WIDTH = 100;
    private static int IMG_LOGO_HEIGHT = 100;
    
    private static int IMG_PRODUCT_WIDTH = 200;
    private static int IMG_PRODUCT_HEIGHT = 200;
    
    public boolean checkExtension(String fileName)
    {
        boolean check = false;

        if(fileName != null && !fileName.equals(""))
        {
            String extValidate = fileName.substring(fileName.indexOf(".") + 1).toLowerCase();
            if(extValidate.equals("png") || extValidate.equals("jpg") || extValidate.equals("jpeg") || extValidate.equals("gif"))
            {
                check = true;
            }
        }
        return check;
    }
    
    public String copyImage(String fileName, InputStream in, String imageCategory) 
    {
        String newFileName = "";
        try
        {
            newFileName = getNewFileName(fileName);
            FacesContext facesContext = FacesContext.getCurrentInstance();
            ExternalContext externalContext = facesContext.getExternalContext();
            String destination = externalContext.getRealPath("/resources/product_images");
            File source = new File(destination);
            if(!source.exists())
            {
                source.mkdir();
            }
           
            boolean check = true;
            while(check == true)
            {
                File file = new File(destination + "/" + newFileName);
                if(file.exists())
                {
                    fileName = fileName + "f";
                }
                else
                {
                    check = false;
                }
            }
            
            BufferedImage originalImage = ImageIO.read(in);
            int type = originalImage.getType() == 0? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
            BufferedImage resizedImage = null;
            if(imageCategory.equalsIgnoreCase("logo"))
            {
                resizedImage = resizeImage(originalImage, type, IMG_LOGO_WIDTH, IMG_LOGO_HEIGHT);
            }
            if(imageCategory.equalsIgnoreCase("product"))
            {
                resizedImage = resizeImage(originalImage, type, IMG_PRODUCT_WIDTH, IMG_PRODUCT_HEIGHT);
            }
            
            ImageIO.write(resizedImage, "jpg", new File(destination + "/" + newFileName));

/*
            // write the inputStream to a FileOutputStream
            OutputStream out = new FileOutputStream(new File(destination + "/" + newFileName));

            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = in.read(bytes)) != -1)
            {
                out.write(bytes, 0, read);
            }
            in.close();
            out.flush();
            out.close();
*/
         }
         catch (Exception e)
         {
             newFileName = "";
            System.out.println(e.getMessage());
         }
         return newFileName;
    }
    
    public String getNewFileName(String fileName)
    {
        String extValidate = fileName.substring(fileName.indexOf(".") + 1).toLowerCase();
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date();
        String newFileName = dateFormat.format(date) + "." + extValidate;
        return newFileName;
    }
    
    public BufferedImage resizeImage(BufferedImage originalImage, int type, int IMG_WIDTH, int IMG_HEIGHT) throws IOException
    {
        Dimension originalDemension = new Dimension(originalImage.getWidth(), originalImage.getHeight());
        Dimension boundaryDemension = new Dimension(IMG_WIDTH, IMG_HEIGHT);
        
        Dimension newDemension = getScaledDimension(originalDemension, boundaryDemension);
        int width = newDemension.width;
        int height = newDemension.height;
        
        BufferedImage resizedImage = new BufferedImage(width, height, type);
	Graphics2D g = resizedImage.createGraphics();
	g.drawImage(originalImage, 0, 0, width, height, null);
	g.dispose();	
	g.setComposite(AlphaComposite.Src);
 
	g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR); 
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
	g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        return resizedImage;
    }
    
    public Dimension getScaledDimension(Dimension imgSize, Dimension boundary) 
    {

        int original_width = imgSize.width;
        int original_height = imgSize.height;
        int bound_width = boundary.width;
        int bound_height = boundary.height;
        int new_width = 0;
        int new_height = 0;

        // first check if we need to scale width
        if (original_width > bound_width) {
            //scale width to fit
            new_width = bound_width;
            //scale height to maintain aspect ratio
            new_height = (new_width*original_height)/original_width;
        }

        // then check if we need to scale even with the new height
        if (new_height > bound_height) {
            //scale height to fit instead
            new_height = bound_height;
            //scale width to maintain aspect ratio
            new_width = (new_height*original_width)/original_height;
        }

        return new Dimension(new_width, new_height);
    }
    
    public boolean deleteImage(String fileName)
    {
        boolean check = true;
        try
        {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            ExternalContext externalContext = facesContext.getExternalContext();
            String destination = externalContext.getRealPath("/resources/product_images");
            File file = new File(destination + "/" + fileName);
            if(file.exists())
            {
                check = file.delete();
            }
        }
        catch(Exception e)
        {
            check = false;
        }
        return check;
    }
}
