package com.capstone.Controller;

import com.capstone.EJB.CommonEJB;
import com.capstone.Entity.Business;
import com.capstone.Entity.Game;
import com.capstone.Entity.GameCategory;
import com.capstone.Entity.GameClassification;
import com.capstone.Entity.GameDetail;
import com.capstone.Entity.LeasesProduct;
import com.capstone.Entity.SalesProduct;
import com.capstone.Entity.SystemRequirement;
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

public class ManageGameController 
{
    @EJB
    private CommonEJB commonEJB;
    
    private ManageDataController manageDataController = new ManageDataController();
    private ImageUploadController upload = new ImageUploadController();

    private Game game = new Game();
    private SystemRequirement systemRequirement = new SystemRequirement();
    private SalesProduct salesProduct = new SalesProduct();
    private LeasesProduct leasesProduct = new LeasesProduct();
    
    private List<Object[]> detailList = new ArrayList<Object[]>();
    private List<GameDetail> lsGameDetail = new ArrayList<GameDetail>();

    private GameClassification gameClassification = new GameClassification();
    private List<String> gameClassifications = new ArrayList<String>();
    private GameCategory gameCategory = new GameCategory();
    private List<String> gameCategories = new ArrayList<String>();
    
    private String strQuery;
    private GameDetail[] selectedGames;
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

        gameClassifications = gameClassification.getLsGameClassification();
        gameCategories = gameCategory.getLsGameCategory();

        loadGame();
    }
    
    public void loadGame()
    {
        lsGameDetail.clear();
        
        strQuery = "(select p.PID, p.title, p.category, p.description, p.image, p.format, p.language, p.releasedDate, p.productLink, g.classification, g.manufacturer, g.nbrOfPlayers, sr.CPU, sr.RAM, sr.VGA, sr.HDD, sr.OS, sr.sound, sr.note, sp.salesPrice, sp.available, lp.leasesPrice, lp.available, p.createdDate from product_table as p, game_table as g, systemRequirement_table as sr, salesProduct_table as sp, leasesProduct_table as lp, business_table as b where p.PID = g.PID and g.systemRequirement_fk = sr.SRID and p.salesProduct_fk = sp.SPID and p.leasesProduct_fk = lp.LPID and p.businessProduct_fk = b.username and b.username = '" + username + "')";
        strQuery = strQuery + " UNION (select p.PID, p.title, p.category, p.description, p.image, p.format, p.language, p.releasedDate, p.productLink, g.classification, g.manufacturer, g.nbrOfPlayers, sr.CPU, sr.RAM, sr.VGA, sr.HDD, sr.OS, sr.sound, sr.note, sp.salesPrice, sp.available, 0.0, 0, p.createdDate from product_table as p, game_table as g, systemRequirement_table as sr, salesProduct_table as sp, business_table as b where p.PID = g.PID and g.systemRequirement_fk = sr.SRID and p.salesProduct_fk = sp.SPID  and p.leasesProduct_fk IS NULL and p.businessProduct_fk = b.username and b.username = '" + username + "')";
        strQuery = strQuery + " UNION (select p.PID, p.title, p.category, p.description, p.image, p.format, p.language, p.releasedDate, p.productLink, g.classification, g.manufacturer, g.nbrOfPlayers, sr.CPU, sr.RAM, sr.VGA, sr.HDD, sr.OS, sr.sound, sr.note, 0.0, 0, lp.leasesPrice, lp.available, p.createdDate from product_table as p, game_table as g, systemRequirement_table as sr, leasesProduct_table as lp, business_table as b where p.PID = g.PID and g.systemRequirement_fk = sr.SRID and p.leasesProduct_fk = lp.LPID  and p.salesProduct_fk IS NULL and p.businessProduct_fk = b.username and b.username = '" + username + "')";
        strQuery = strQuery + " UNION (select p.PID, p.title, p.category, p.description, p.image, p.format, p.language, p.releasedDate, p.productLink, g.classification, g.manufacturer, g.nbrOfPlayers, sr.CPU, sr.RAM, sr.VGA, sr.HDD, sr.OS, sr.sound, sr.note, 0.0, 0, 0.0, 0, p.createdDate from product_table as p, game_table as g, systemRequirement_table as sr, business_table as b where p.PID = g.PID and g.systemRequirement_fk = sr.SRID and p.leasesProduct_fk IS NULL and p.salesProduct_fk IS NULL and p.businessProduct_fk = b.username and b.username = '" + username + "')";
        strQuery = strQuery + " ORDER BY createdDate DESC";
        detailList = commonEJB.getListInfoNativeQuery(strQuery);
        loadGameData(detailList);
    }
    
    public void loadGameData(List<Object[]> lsObjects)
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
            String classification = obj[9].toString().trim();
            String manufacturer = obj[10].toString().trim();
            Integer nbrOfPlayers = Integer.parseInt(obj[11].toString());
            String CPU = obj[12].toString().trim();
            String RAM = obj[13].toString().trim();
            String VGA = obj[14].toString().trim();
            String HDD = obj[15].toString().trim();
            String OS = obj[16].toString().trim();
            String sound = obj[17].toString().trim();
            String note = obj[18].toString().trim();
            
            String strSalesPrice = obj[19].toString();
            Double salesPrice = Double.parseDouble(strSalesPrice);

            String salesAvailable = obj[20].toString().trim().toLowerCase();
            if(salesAvailable.equalsIgnoreCase("1"))
            {
                salesAvailable = "Available";
            }
            else
            {
                salesAvailable = "Not Available";
            }
            String strLeasesPrice = obj[21].toString();
            Double leasesPrice = Double.parseDouble(strLeasesPrice);

            String leasesAvailable = obj[22].toString().trim().toLowerCase();
            if(leasesAvailable.equalsIgnoreCase("1"))
            {
                leasesAvailable = "Available";
            }
            else
            {
                leasesAvailable = "Not Available";
            }
            
            GameDetail sd = new GameDetail(PID, title, category, description, image, format, language, releasedDate, productLink, classification, manufacturer, nbrOfPlayers, CPU, RAM, VGA, HDD, OS, sound, note, salesPrice, salesAvailable, leasesPrice, leasesAvailable);
            lsGameDetail.add(sd);
        }
    }
    
    public void deleteGames()
    {
        int count = selectedGames.length;
        if(count > 0)
        {
            boolean check = true;
            for(int i = 0; i < count; i++)
            {
                Long PID = selectedGames[i].getGDID();
                Game g = (Game)commonEJB.getSingleInfo("select g from Game g where g.PID = " + PID);
                boolean checkImage = upload.deleteImage(g.getImage().toString().trim());
                if(checkImage == true)
                {
                    boolean checkDelete = commonEJB.deleteData(g);
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
                loadGame();
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
    
    public void quickEditGame(RowEditEvent event)
    {
        Long PID = Long.parseLong(((GameDetail) event.getObject()).getGDID().toString());
        Game g = (Game)commonEJB.getSingleInfo("select g from Game g where g.PID = " + PID);
        g.setTitle(((GameDetail) event.getObject()).getTitle().toString());
        SalesProduct sp = new SalesProduct();
        if(g.getSalesProduct() != null)
        {
            sp = (SalesProduct)commonEJB.getSingleInfo("select sp from Game g JOIN g.salesProduct sp where g.PID = " + PID);
        }
        
        sp.setSalesPrice(((GameDetail) event.getObject()).getSalesPrice());
        if(((GameDetail) event.getObject()).getSalesAvailable().toString().equalsIgnoreCase("Available"))
        {
            sp.setAvailable(true);
        }
        else
        {
            sp.setAvailable(false);
        }
        LeasesProduct lp = new LeasesProduct();
        if(g.getLeasesProduct() != null)
        {
            lp = (LeasesProduct)commonEJB.getSingleInfo("select lp from Game g JOIN g.leasesProduct lp where g.PID = " + PID);
        }
        lp.setLeasesPrice(((GameDetail) event.getObject()).getLeasesPrice());
        if(((GameDetail) event.getObject()).getLeasesAvailable().toString().equalsIgnoreCase("Available"))
        {
            lp.setAvailable(true);
        }
        else
        {
            lp.setAvailable(false);
        }
        boolean checkGame = commonEJB.updateData(g);
        boolean checkSalesProduct = commonEJB.updateData(sp);
        boolean checkLeasesProduct = commonEJB.updateData(lp);

        if(checkGame == true && checkSalesProduct == true && checkLeasesProduct == true)
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
        strAction = "Create a new game";
        imageProduct = "";
        salesProductAvailable = "Not Available";
        leasesProductAvailable = "Not Available";
        game = new Game();
        salesProduct = new SalesProduct();
        leasesProduct = new LeasesProduct();
        systemRequirement = new SystemRequirement();
        visible = true;
    }
    
    public void createNewGame() throws IOException
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
                boolean checkGame = handleAction("create");
                if(checkGame == true)
                {
                    loadGame();
                    visible = false;
                    showMessage("Success", "A new game has created", "info");
                }
                else
                {
                    showMessage("Fail", "Data cannot persist to database 666666666666666", "error");
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
        boolean checkGame = false;
        boolean checkSystemRequirement = false;
        boolean checkSalesProduct = true;
        boolean checkLeasesProduct = true;
        
        if(game.getSalesProduct() == null)
        {
            if(salesProductAvailable.equalsIgnoreCase("Available"))
            {
                salesProduct.setAvailable(true);
                checkSalesProduct = commonEJB.persistData(salesProduct);
                if(checkSalesProduct == true)
                {
                    game.setSalesProduct(salesProduct);
                }
                else
                {
                    showMessage("Error Message", "Data cannot persist to database 22222222222222", "error");
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
                game.setSalesProduct(salesProduct);
            }
            else
            {
                showMessage("Error Message", "Data cannot persist to database", "error");
            }
        }
        
        if(game.getLeasesProduct() == null)
        {
            if(leasesProductAvailable.equalsIgnoreCase("Available"))
            {
                leasesProduct.setAvailable(true);
                checkLeasesProduct = commonEJB.persistData(leasesProduct);
                if(checkLeasesProduct == true)
                {
                    game.setLeasesProduct(leasesProduct);
                }
                else
                {
                    showMessage("Error Message", "Data cannot persist to database 33333333333333", "error");
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
                game.setLeasesProduct(leasesProduct);
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
                game.setBusiness(business);
            }
            boolean checkImage = true;
            if(type.equalsIgnoreCase("edit"))
            {
                if(!imageProduct.equalsIgnoreCase(game.getImage().toString().trim()))
                {
                    checkImage = upload.deleteImage(game.getImage().toString().trim());
                }
            }
            if(checkImage == true)
            {
                game.setImage(imageProduct);
                if(type.equalsIgnoreCase("create"))
                {
                    checkSystemRequirement = commonEJB.persistData(systemRequirement);
                }

                if(type.equalsIgnoreCase("edit"))
                {
                    checkSystemRequirement = commonEJB.updateData(systemRequirement);
                }
                
                if(checkSystemRequirement == true)
                {
                    game.setSystemRequirement(systemRequirement);

                    if(type.equalsIgnoreCase("create"))
                    {
                        checkGame = commonEJB.persistData(game);
                    }

                    if(type.equalsIgnoreCase("edit"))
                    {
                        checkGame = commonEJB.updateData(game);
                    }
                }
            }
        }
        return checkGame;
    }
    
    public void uploadImage(FileUploadEvent event) throws IOException
    {
        boolean checkImage = true;
        
        if(game.getImage() != null)
        {
            if(!imageProduct.equalsIgnoreCase(game.getImage().toString().trim()))
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
        strAction = "Edit game details";
        
        game = (Game)commonEJB.getSingleInfo("select g from Game g where g.PID = " + PID);
        if(game.getSystemRequirement() != null)
        {
            systemRequirement = (SystemRequirement)commonEJB.getSingleInfo("select sr from Game g JOIN g.systemRequirement sr where g.PID = " + PID);
        }
        else
        {
            systemRequirement = new SystemRequirement();
        }
        if(game.getSalesProduct() != null)
        {
            salesProduct = (SalesProduct)commonEJB.getSingleInfo("select sp from Game g JOIN g.salesProduct sp where g.PID = " + PID);
        }
        else
        {
            salesProduct = new SalesProduct();
        }
        if(game.getLeasesProduct() != null)
        {
            leasesProduct = (LeasesProduct)commonEJB.getSingleInfo("select lp from Game g JOIN g.leasesProduct lp where g.PID = " + PID);
        
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
        
        imageProduct = game.getImage().toString();
        visible = true;
    }
    
    public void editGame()
    {
        boolean checkGame = handleAction("edit");
        if(checkGame == true)
        {
            loadGame();
            visible = false;
            showMessage("Success", "A game has edited", "info");
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
        if(!imageProduct.equalsIgnoreCase(game.getImage().toString().trim()))
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
          
        lsTitles = commonEJB.getListInfo("select g.title from Game g");
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

    public List<GameDetail> getLsGameDetail()
    {
        return lsGameDetail;
    }
    
    public void setLsGameDetail()
    {
        loadGame();
    }
    
    public List<String> getGameClassifications()
    {
        return gameClassifications;
    }
    
    public List<String> getGameCategories()
    {
        return gameCategories;
    }
   
    public GameDetail[] getSelectedGames()
    {
        return selectedGames;
    }
    
    public void setSelectedGames(GameDetail[] selectedGames)
    {
        this.selectedGames = selectedGames;
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

    public Game getGame()
    {
        return game;
    }
    public void setGame(Game game)
    {
        this.game = game;
    }
    
    public SystemRequirement getSystemRequirement()
    {
        return systemRequirement;
    }
    public void setSystemRequirement(SystemRequirement systemRequirement)
    {
        this.systemRequirement = systemRequirement;
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