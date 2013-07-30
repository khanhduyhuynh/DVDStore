package com.capstone.Controller;

import com.capstone.EJB.CommonEJB;
import com.capstone.Entity.Business;
import com.capstone.Entity.LeasesProduct;
import com.capstone.Entity.SalesProduct;
import com.capstone.Entity.Soundtrack;
import com.capstone.Entity.SoundtrackCategory;
import com.capstone.Entity.SoundtrackDetail;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.RowEditEvent;

public class ManageSoundtrackController 
{
    @EJB
    private CommonEJB commonEJB;
    
    private ManageDataController manageDataController = new ManageDataController();
    private ImageUploadController upload = new ImageUploadController();

    private Soundtrack soundtrack = new Soundtrack();
    private SalesProduct salesProduct = new SalesProduct();
    private LeasesProduct leasesProduct = new LeasesProduct();
    
    private List<Object[]> detailList = new ArrayList<Object[]>();
    private List<SoundtrackDetail> lsSoundtrackDetail = new ArrayList<SoundtrackDetail>();

    private SoundtrackCategory soundtrackCategory = new SoundtrackCategory();
    private List<String> soundtrackCategories = new ArrayList<String>();
    
    private String strQuery;
    private SoundtrackDetail[] selectedSoundtracks;
    private boolean visible;
    private String strAction;
    private String salesProductAvailable;
    private String leasesProductAvailable;
    
    private String username;
    private String imageProduct;
    
    private List<String> lsTitles = new ArrayList<String>();

    @PostConstruct
    public void init()
    {
        username = manageDataController.getUsername();
        
        visible = false;
        salesProductAvailable = "Not Available";
        leasesProductAvailable = "Not Available";

        soundtrackCategories = soundtrackCategory.getLsSoundtrackCategory();

        loadSoundtrack();
    }
    
    public void loadSoundtrack()
    {
        lsSoundtrackDetail.clear();
        
        strQuery = "(select p.PID, p.title, p.category, p.description, p.image, p.format, p.language, p.releasedDate, p.productLink, s.album, s.artist, s.nbrOfTracks, sp.salesPrice, sp.available, lp.leasesPrice, lp.available, p.createdDate from product_table as p, soundtrack_table as s, salesProduct_table as sp, leasesProduct_table as lp, business_table as b where p.PID = s.PID and p.salesProduct_fk = sp.SPID  and p.leasesProduct_fk = lp.LPID and p.businessProduct_fk = b.username and b.username = '" + username + "')";
        strQuery = strQuery + " UNION (select p.PID, p.title, p.category, p.description, p.image, p.format, p.language, p.releasedDate, p.productLink, s.album, s.artist, s.nbrOfTracks, sp.salesPrice, sp.available, 0.0, 0, p.createdDate from product_table as p, soundtrack_table as s, salesProduct_table as sp, business_table as b where p.PID = s.PID and p.salesProduct_fk = sp.SPID  and p.leasesProduct_fk IS NULL and p.businessProduct_fk = b.username and b.username = '" + username + "')";
        strQuery = strQuery + " UNION (select p.PID, p.title, p.category, p.description, p.image, p.format, p.language, p.releasedDate, p.productLink, s.album, s.artist, s.nbrOfTracks, 0.0, 0, lp.leasesPrice, lp.available, p.createdDate from product_table as p, soundtrack_table as s, leasesProduct_table as lp, business_table as b where p.PID = s.PID and p.leasesProduct_fk = lp.LPID  and p.salesProduct_fk IS NULL and p.businessProduct_fk = b.username and b.username = '" + username + "')";
        strQuery = strQuery + " UNION (select p.PID, p.title, p.category, p.description, p.image, p.format, p.language, p.releasedDate, p.productLink, s.album, s.artist, s.nbrOfTracks, 0.0, 0, 0.0, 0, p.createdDate from product_table as p, soundtrack_table as s, business_table as b where p.PID = s.PID and p.leasesProduct_fk IS NULL and p.salesProduct_fk IS NULL and p.businessProduct_fk = b.username and b.username = '" + username + "')";
        strQuery = strQuery + " ORDER BY createdDate DESC";
        detailList = commonEJB.getListInfoNativeQuery(strQuery);
        loadSoundtrackData(detailList);
    }
    
    public void loadSoundtrackData(List<Object[]> lsObjects)
    {
        for(int i = 0; i < lsObjects.size(); i++)
        {
            Object[] obj = lsObjects.get(i);
            Long PID = (Long)obj[0];
            String title = obj[1].toString().trim();
            String category = obj[2].toString().trim();
            String description = obj[3].toString().trim();
            String image = obj[4].toString().trim();
            String format = obj[5].toString().trim();
            String language = obj[6].toString().trim();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy");
            Date releasedDate;
            try 
            {
                releasedDate = dateFormat.parse(obj[7].toString());
            } 
            catch (ParseException ex) 
            {
                releasedDate = new Date();
            }
            String productLink = obj[8].toString().trim();
            String album = obj[9].toString().trim();
            String artist = obj[10].toString().trim();
            Integer nbrOfTracks = Integer.parseInt(obj[11].toString());
            String strSalesPrice = obj[12].toString();
            Double salesPrice = Double.parseDouble(strSalesPrice);

            String salesAvailable = obj[13].toString().trim().toLowerCase();
            if(salesAvailable.equalsIgnoreCase("1"))
            {
                salesAvailable = "Available";
            }
            else
            {
                salesAvailable = "Not Available";
            }
            String strLeasesPrice = obj[14].toString();
            Double leasesPrice = Double.parseDouble(strLeasesPrice);

            String leasesAvailable = obj[15].toString().trim().toLowerCase();
            if(leasesAvailable.equalsIgnoreCase("1"))
            {
                leasesAvailable = "Available";
            }
            else
            {
                leasesAvailable = "Not Available";
            }
            
            SoundtrackDetail sd = new SoundtrackDetail(PID, title, category, description, image, format, language, releasedDate, productLink, album, artist, nbrOfTracks, salesPrice, salesAvailable, leasesPrice, leasesAvailable);
            lsSoundtrackDetail.add(sd);
        }
    }
    
    public void deleteSoundtracks()
    {
        int count = selectedSoundtracks.length;
        if(count > 0)
        {
            boolean check = true;
            for(int i = 0; i < count; i++)
            {
                Long PID = selectedSoundtracks[i].getSTDID();
                Soundtrack s = (Soundtrack)commonEJB.getSingleInfo("select s from Soundtrack s where s.PID = " + PID);
                boolean checkImage = upload.deleteImage(s.getImage().toString().trim());
                if(checkImage == true)
                {
                    boolean checkDelete = commonEJB.deleteData(s);
                    if(checkDelete == false)
                    {
                        check = false;
                        break;
                    }
                }
                else
                {
                    check = false;
                    break;
                }
            }
            if(check == true)
            {
                showMessage("Success", "Selected items have been removed", "info");
                visible = false;
                loadSoundtrack();
            }
            else
            {
                showMessage("Fail", "Selected items can not remove", "error");
            }
        }
        else
        {
            showMessage("Error Message", "Please select items you want to delete", "error");
        }
        
    }
    
    public void quickEditSoundtrack(RowEditEvent event)
    {
        Long PID = Long.parseLong(((SoundtrackDetail) event.getObject()).getSTDID().toString());
        Soundtrack s = (Soundtrack)commonEJB.getSingleInfo("select s from Soundtrack s where s.PID = " + PID);
        s.setTitle(((SoundtrackDetail) event.getObject()).getTitle().toString());
        SalesProduct sp = new SalesProduct();
        if(s.getSalesProduct() != null)
        {
            sp = (SalesProduct)commonEJB.getSingleInfo("select sp from Soundtrack s JOIN s.salesProduct sp where s.PID = " + PID);
        }
        
        sp.setSalesPrice(((SoundtrackDetail) event.getObject()).getSalesPrice());
        if(((SoundtrackDetail) event.getObject()).getSalesAvailable().toString().equalsIgnoreCase("Available"))
        {
            sp.setAvailable(true);
        }
        else
        {
            sp.setAvailable(false);
        }
        LeasesProduct lp = new LeasesProduct();
        if(s.getLeasesProduct() != null)
        {
            lp = (LeasesProduct)commonEJB.getSingleInfo("select lp from Soundtrack s JOIN s.leasesProduct lp where s.PID = " + PID);
        }
        lp.setLeasesPrice(((SoundtrackDetail) event.getObject()).getLeasesPrice());
        if(((SoundtrackDetail) event.getObject()).getLeasesAvailable().toString().equalsIgnoreCase("Available"))
        {
            lp.setAvailable(true);
        }
        else
        {
            lp.setAvailable(false);
        }
        boolean checkSoundtrack = commonEJB.updateData(s);
        boolean checkSalesProduct = commonEJB.updateData(sp);
        boolean checkLeasesProduct = commonEJB.updateData(lp);
        if(checkSoundtrack == true && checkSalesProduct == true && checkLeasesProduct == true)
        {
            showMessage("Success", "Item has been edited", "info");
            visible = false;
        }
        else
        {
            showMessage("Error Message", "Item can not edit", "error");
        }
       
    }
    
    public void displayCreateNew()
    {
        strAction = "Create a new soundtrack";
        imageProduct = "";
        salesProductAvailable = "Not Available";
        leasesProductAvailable = "Not Available";
        soundtrack = new Soundtrack();
        salesProduct = new SalesProduct();
        leasesProduct = new LeasesProduct();
        visible = true;
    }
    
    public void createNewSoundtrack() throws IOException
    {
        if(username == null || username.equals(""))
        {
            showMessage(null, "Session time out, please login again", "error");
            FacesContext.getCurrentInstance().getExternalContext().dispatch("/home.xhtml");

        }
        else
        {           
            if(!imageProduct.equals(""))
            {
                boolean checkSoundtrack = handleAction("create");
                if(checkSoundtrack == true)
                {
                    loadSoundtrack();
                    visible = false;
                    showMessage("Success", "A new soundtrack has created", "info");
                }
                else
                {
                    showMessage("Fail", "Data cannot persist to database", "error");
                }
            }
            else
            {
                showMessage("Error Message", "Image can not be blank !", "error");
            }
        }
    }
    
    public void cancelCreate()
    {
        visible = false;
        boolean checkImage = true;
        if(!imageProduct.equalsIgnoreCase(""))
        {
            checkImage = upload.deleteImage(imageProduct);
        }
        if(checkImage == false)
        {
            showMessage("Error Message", "Cannot delete an image", "error");
        }
    }
    
    public boolean handleAction(String type)
    {
        boolean checkSoundtrack = false;
        boolean checkSalesProduct = true;
        boolean checkLeasesProduct = true;
        
        if(soundtrack.getSalesProduct() == null)
        {
            if(salesProductAvailable.equalsIgnoreCase("Available"))
            {
                salesProduct.setAvailable(true);
                checkSalesProduct = commonEJB.persistData(salesProduct);
                if(checkSalesProduct == true)
                {
                    soundtrack.setSalesProduct(salesProduct);
                }
                else
                {
                    showMessage("Error Message", "Data cannot persist to database", "error");
                }
            }
        }
        else
        {
            if(salesProductAvailable.equalsIgnoreCase("Available"))
            {
                salesProduct.setAvailable(true);
            }
            else
            {
                salesProduct.setAvailable(false);
            }
            checkSalesProduct = commonEJB.updateData(salesProduct);
            if(checkSalesProduct == true)
            {
                soundtrack.setSalesProduct(salesProduct);
            }
            else
            {
                showMessage("Error Message", "Data cannot persist to database", "error");
            }
        }
        
        if(soundtrack.getLeasesProduct() == null)
        {
            if(leasesProductAvailable.equalsIgnoreCase("Available"))
            {
                leasesProduct.setAvailable(true);
                checkLeasesProduct = commonEJB.persistData(leasesProduct);
                if(checkLeasesProduct == true)
                {
                    soundtrack.setLeasesProduct(leasesProduct);
                }
                else
                {
                    showMessage("Error Message", "Data cannot persist to database", "error");
                }
            }
        }
        else
        {
            if(leasesProductAvailable.equalsIgnoreCase("Available"))
            {
                leasesProduct.setAvailable(true);
            }
            else
            {
                leasesProduct.setAvailable(false);
            }
            checkLeasesProduct = commonEJB.updateData(leasesProduct);
            if(checkLeasesProduct == true)
            {
                soundtrack.setLeasesProduct(leasesProduct);
            }
            else
            {
                showMessage("Error Message", "Data cannot persist to database", "error");
            }
        }
        
        if(checkSalesProduct == true && checkLeasesProduct == true)
        {
            if(type.equalsIgnoreCase("create"))
            {
                Business business = (Business)commonEJB.getSingleInfo("select b from Business b where b.username = '" + username + "'");
                soundtrack.setBusiness(business);
            }
            boolean checkImage = true;
            if(type.equalsIgnoreCase("edit"))
            {
                if(!imageProduct.equalsIgnoreCase(soundtrack.getImage().toString().trim()))
                {
                    checkImage = upload.deleteImage(soundtrack.getImage().toString().trim());
                }
            }
            if(checkImage == true)
            {
                soundtrack.setImage(imageProduct);

                if(type.equalsIgnoreCase("create"))
                {
                    checkSoundtrack = commonEJB.persistData(soundtrack);
                }

                if(type.equalsIgnoreCase("edit"))
                {
                    checkSoundtrack = commonEJB.updateData(soundtrack);
                }
            }
        }
        return checkSoundtrack;
    }
    
    public void uploadImage(FileUploadEvent event) throws IOException
    {
        boolean checkImage = true;
        
        if(soundtrack.getImage() != null)
        {
            if(!imageProduct.equalsIgnoreCase(soundtrack.getImage().toString().trim()))
            {
                checkImage = upload.deleteImage(imageProduct);
            }
        }
        else
        {
            if(!imageProduct.equalsIgnoreCase(""))
            {
                checkImage = upload.deleteImage(imageProduct);
            }
        }
        if(checkImage == true)
        {
            String fileName = event.getFile().getFileName();
            boolean checkExtension = upload.checkExtension(fileName);
            if(checkExtension == true)
            {
                imageProduct = upload.copyImage(fileName, event.getFile().getInputstream(), "product");
            }
        }
        else
        {
            showMessage("Error Message", "Cannot upload image", "error");
        }
    }
    
    public void displayEdit(Long PID)
    {
        strAction = "Edit soundtrack details";
        
        soundtrack = (Soundtrack)commonEJB.getSingleInfo("select s from Soundtrack s where s.PID = " + PID);
        if(soundtrack.getSalesProduct() != null)
        {
            salesProduct = (SalesProduct)commonEJB.getSingleInfo("select sp from Soundtrack s JOIN s.salesProduct sp where s.PID = " + PID);
        
        }
        else
        {
            salesProduct = new SalesProduct();
        }
        if(soundtrack.getLeasesProduct() != null)
        {
            leasesProduct = (LeasesProduct)commonEJB.getSingleInfo("select lp from Soundtrack s JOIN s.leasesProduct lp where s.PID = " + PID);
        
        }
        else
        {
            leasesProduct = new LeasesProduct();
        }
        
        boolean salesAvailable = salesProduct.getAvailable();
        if(salesAvailable == true)
        {
            salesProductAvailable = "Available";
        }
        else
        {
            salesProductAvailable = "Not Available";
        }
        
        boolean leasesAvailable = leasesProduct.getAvailable();
        if(leasesAvailable == true)
        {
            leasesProductAvailable = "Available";
        }
        else
        {
            leasesProductAvailable = "Not Available";
        }
        
        imageProduct = soundtrack.getImage().toString();
        visible = true;
    }
    
    public void editSoundtrack()
    {
        boolean checkSoundtrack = handleAction("edit");
        if(checkSoundtrack == true)
        {
            loadSoundtrack();
            visible = false;
            showMessage("Success", "A soundtrack has edited", "info");
        }
        else
        {
            showMessage("Fail", "Data cannot persist to database", "error");
        }
    }
    
    public void cancelEdit()
    {
        visible = false;
        boolean checkImage = true;
        if(!imageProduct.equalsIgnoreCase(soundtrack.getImage().toString().trim()))
        {
            checkImage = upload.deleteImage(imageProduct);
        }
        if(checkImage == false)
        {
            showMessage("Error Message", "Cannot delete an image", "error");
        }
    }
    
    public List<String> completeTitle(String query) 
    {  
        List<String> suggestions = new ArrayList<String>();  
          
        lsTitles = commonEJB.getListInfo("select s.title from Soundtrack s");
        for(String title : lsTitles) 
        {  
            if(title.startsWith(query.toLowerCase()) || title.startsWith(query.toUpperCase()))
            {
                suggestions.add(title);
            }
        }
        return suggestions;  
    }
    
    public void showMessage(String str1, String str2, String type)
    {
        FacesMessage msg = null;
        if(type.equals("info"))
        {
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, str1, str2);
        }
        if(type.equals("error"))
        {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, str1, str2);
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public List<SoundtrackDetail> getLsSoundtrackDetail()
    {
        return lsSoundtrackDetail;
    }
    
    public void setLsSoundtrackDetail()
    {
        loadSoundtrack();
    }
    
    public List<String> getSoundtrackCategories()
    {
        return soundtrackCategories;
    }
   
    public SoundtrackDetail[] getSelectedSoundtracks()
    {
        return selectedSoundtracks;
    }
    
    public void setSelectedSoundtracks(SoundtrackDetail[] selectedSoundtracks)
    {
        this.selectedSoundtracks = selectedSoundtracks;
    }
    
    public boolean getVisible()
    {
        return visible;
    }
    
    public void setVisible(boolean visible)
    {
        this.visible = visible;
    }
    
    public String getStrAction()
    {
        return strAction;
    }
    
    public void setStrAction(String strAction)
    {
        this.strAction = strAction;
    }
    
    public String getSalesProductAvailable()
    {
        return salesProductAvailable;
    }
    
    public void setSalesProductAvailable(String salesProductAvailable)
    {
        this.salesProductAvailable = salesProductAvailable;
    }
    
    public String getLeasesProductAvailable()
    {
        return leasesProductAvailable;
    }
    
    public void setLeasesProductAvailable(String leasesProductAvailable)
    {
        this.leasesProductAvailable = leasesProductAvailable;
    }
    
    public ImageUploadController getUpload()
    {
        return upload;
    }
 
    public void setUpload(ImageUploadController upload) 
    {
        this.upload = upload;
    }
    
    public ManageDataController getManageDataController()
    {
        return manageDataController;
    }
 
    public void setManageDataController(ManageDataController manageDataController) 
    {
        this.manageDataController = manageDataController;
    }

    public Soundtrack getSoundtrack()
    {
        return soundtrack;
    }
    public void setSoundtrack(Soundtrack soundtrack)
    {
        this.soundtrack = soundtrack;
    }
    
    public SalesProduct getSalesProduct()
    {
        return salesProduct;
    }
    public void setSalesProduct(SalesProduct salesProduct)
    {
        this.salesProduct = salesProduct;
    }
    
    public LeasesProduct getLeasesProduct()
    {
        return leasesProduct;
    }
    public void setLeasesProduct(LeasesProduct leasesProduct)
    {
        this.leasesProduct = leasesProduct;
    }
    
    public String getImageProduct()
    {
        return imageProduct;
    }
    public void setImageProduct(String imageProduct)
    {
        this.imageProduct = imageProduct;
    }
    
}