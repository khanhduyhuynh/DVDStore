package com.capstone.Controller;

import com.capstone.EJB.CommonEJB;
import com.capstone.Entity.Business;
import com.capstone.Entity.Game;
import com.capstone.Entity.LeasesProduct;
import com.capstone.Entity.Movie;
import com.capstone.Entity.Product;
import com.capstone.Entity.ProductDetail;
import com.capstone.Entity.ProductTypes;
import com.capstone.Entity.SalesProduct;
import com.capstone.Entity.Soundtrack;
import com.capstone.Entity.SystemRequirement;
import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import org.primefaces.component.menuitem.MenuItem;
import org.primefaces.component.separator.Separator;
import org.primefaces.component.submenu.Submenu;
import org.primefaces.model.MenuModel;

public class CategoryMenuModel implements MenuModel, ActionListener, Serializable 
{
    @EJB
    private CommonEJB commonEJB;
    
    private ProductTypes productTypes = new ProductTypes();
    private List<String> lsTypes = new ArrayList<String>(); 

    private List<UIComponent> contents = new ArrayList<UIComponent>();
    private static UIViewRoot uiViewRoot = new UIViewRoot();
    private List<ProductDetail> lsProducts = new ArrayList<ProductDetail>();
    private List<ProductDetail> lsComparedProducts = new ArrayList<ProductDetail>();
    
    private Product product = new Product();
    private Movie movie = new Movie();
    private Game game = new Game();
    private Soundtrack soundtrack = new Soundtrack();
    private SystemRequirement systemRequirement = new SystemRequirement();
    
    private String level;
    private String selectedType;
    private String selectedCategory;
    private String titleGroup;
    private String categoryGroup;
    private String imageGroup;
    private String minSalesPrice;
    private String minLeasesPrice;
    
    private DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    private String strSearch;
    
    private String strAdvancedSearch;
    private String selectedTypeSearch;
    private String selectedCategorySearch;
    private final static String[] listType;
    static {
        listType = new String[4];
        listType[0] = "All Products";
        listType[1] = "Movie";
        listType[2] = "Game";
        listType[3] = "Soundtrack";
    }
    private List<String> listCategoriesSearch = new ArrayList<String>();
    private String selectedOption;
    private Double minimumSearchPrice;
    private Double maximumSearchPrice;
    
    @PostConstruct
    public void init() throws IOException
    {
        listCategoriesSearch.add("All Categories");
        
        String strQuery = "DROP FUNCTION IF EXISTS jaro_winkler_similarity;";
        Integer check = commonEJB.executeQuery(strQuery);
        if(check == 0)
        {
            strQuery = "CREATE FUNCTION jaro_winkler_similarity(in1 varchar(255),in2 varchar(255)) RETURNS float DETERMINISTIC";
            strQuery = strQuery + " BEGIN";
            strQuery = strQuery + " declare finestra, curString, curSub, maxSub, trasposizioni, prefixlen, maxPrefix int;";
            strQuery = strQuery + " declare char1, char2 char(1);";
            strQuery = strQuery + " declare common1, common2, old1, old2 varchar(255);";
            strQuery = strQuery + " declare trovato boolean;";
            strQuery = strQuery + " declare returnValue, jaro float;";
            strQuery = strQuery + " set maxPrefix=6;";
            strQuery = strQuery + " set common1='';";
            strQuery = strQuery + " set common2='';";
            strQuery = strQuery + " set finestra=(length(in1)+length(in2)-abs(length(in1)-length(in2))) DIV 4 + ((length(in1)+length(in2)-abs(length(in1)-length(in2)))/2) mod 2;";
            strQuery = strQuery + " set old1=in1;";
            strQuery = strQuery + " set old2=in2;";

            strQuery = strQuery + " set curString=1;";
            strQuery = strQuery + " while curString<=length(in1) and (curString<=(length(in2)+finestra)) do";
                strQuery = strQuery + " set curSub=curstring-finestra;";
                strQuery = strQuery + " if (curSub)<1 then";
                    strQuery = strQuery + " set curSub=1;";
                strQuery = strQuery + " end if;";
                strQuery = strQuery + " set maxSub=curstring+finestra;";
                strQuery = strQuery + " if (maxSub)>length(in2) then";
                    strQuery = strQuery + " set maxSub=length(in2);";
                strQuery = strQuery + " end if;";
                strQuery = strQuery + " set trovato = false;";
                strQuery = strQuery + " while curSub<=maxSub and trovato=false do";
                    strQuery = strQuery + " if substr(in1,curString,1)=substr(in2,curSub,1) then";
                        strQuery = strQuery + " set common1 = concat(common1,substr(in1,curString,1));";
                        strQuery = strQuery + " set in2 = concat(substr(in2,1,curSub-1),concat('0',substr(in2,curSub+1,length(in2)-curSub+1)));";
                        strQuery = strQuery + " set trovato=true;";
                    strQuery = strQuery + " end if;";
                    strQuery = strQuery + " set curSub=curSub+1;";
                strQuery = strQuery + " end while;";
                strQuery = strQuery + " set curString=curString+1;";
            strQuery = strQuery + " end while;";

            strQuery = strQuery + " set in2=old2;";
            strQuery = strQuery + " set curString=1;";
            strQuery = strQuery + " while curString<=length(in2) and (curString<=(length(in1)+finestra)) do";
                strQuery = strQuery + " set curSub=curstring-finestra;";
                strQuery = strQuery + " if (curSub)<1 then";
                    strQuery = strQuery + " set curSub=1;";
                strQuery = strQuery + " end if;";
                strQuery = strQuery + " set maxSub=curstring+finestra;";
                strQuery = strQuery + " if (maxSub)>length(in1) then";
                    strQuery = strQuery + " set maxSub=length(in1);";
                strQuery = strQuery + " end if;";
                strQuery = strQuery + " set trovato = false;";
                strQuery = strQuery + " while curSub<=maxSub and trovato=false do";
                    strQuery = strQuery + " if substr(in2,curString,1)=substr(in1,curSub,1) then";
                        strQuery = strQuery + " set common2 = concat(common2,substr(in2,curString,1));";
                        strQuery = strQuery + " set in1 = concat(substr(in1,1,curSub-1),concat('0',substr(in1,curSub+1,length(in1)-curSub+1)));";
                        strQuery = strQuery + " set trovato=true;";
                    strQuery = strQuery + " end if;";
                    strQuery = strQuery + " set curSub=curSub+1;";
                strQuery = strQuery + " end while;";
                strQuery = strQuery + " set curString=curString+1;";
            strQuery = strQuery + " end while;";
            strQuery = strQuery + " set in1=old1;";
            strQuery = strQuery + " if length(common1)<>length(common2)";
                strQuery = strQuery + " then set jaro=0;";
            strQuery = strQuery + " elseif length(common1)=0 or length(common2)=0";
                strQuery = strQuery + " then set jaro=0;";
            strQuery = strQuery + " else";
                strQuery = strQuery + " set trasposizioni=0;";
                strQuery = strQuery + " set curString=1;";
                strQuery = strQuery + " while curString<=length(common1) do";
                    strQuery = strQuery + " if(substr(common1,curString,1)<>substr(common2,curString,1)) then";
                        strQuery = strQuery + " set trasposizioni=trasposizioni+1;";
                    strQuery = strQuery + " end if;";
                    strQuery = strQuery + " set curString=curString+1;";
                strQuery = strQuery + " end while;";
                strQuery = strQuery + " set jaro=(length(common1)/length(in1)+length(common2)/length(in2)+(length(common1)-trasposizioni/2)/length(common1))/3;";
            strQuery = strQuery + " end if;";

            strQuery = strQuery + " set prefixlen=0;";
            strQuery = strQuery + " while (substring(in1,prefixlen+1,1)=substring(in2,prefixlen+1,1)) and (prefixlen<6) do";
            strQuery = strQuery + " set prefixlen= prefixlen+1;";
            strQuery = strQuery + " end while;";

            strQuery = strQuery + " return jaro+(prefixlen*0.1*(1-jaro));";
            strQuery = strQuery + " END";
            
            check = commonEJB.executeQuery(strQuery);
        }
        if(check == 1)
        {
            showMessage("Error Message", "Cannot create myslql function", "error");
        }
        
        String strQuery1 = getStrQuery(1);
        List<Object[]> lsObjects = commonEJB.getListInfoNativeQuery(strQuery1);
        if(lsObjects.isEmpty())
        {
            try
            {
                boolean checkBusiness1 = false;
                boolean checkBusiness2 = false;
                boolean checkBusiness3 = false;
                
                Business b1;
                boolean checkBusinessExist1 = commonEJB.checkExisted("select b from Business b where b.username = 'khanhduy'");
                if(checkBusinessExist1 == false)
                {
                    b1 = new Business("khanhduy", "1234", "Mr", "Duy", "Huynh", "2 Surrey St", "Marrickville", "NSW", "2204", "0406051234", "khanhduy_sn@yahoo.com", "09 009 987 876", "Beat the bomb", "logo1.png");
                    checkBusiness1 = commonEJB.persistData(b1);
                }
                else
                {
                    b1 = (Business)commonEJB.getSingleInfo("select b from Business b where b.username = 'khanhduy'");
                }
                
                Business b2;
                boolean checkBusinessExist2 = commonEJB.checkExisted("select b from Business b where b.username = 'gameland'");
                if(checkBusinessExist2 == false)
                {
                    b2 = new Business("gameland", "1234", "Mr", "Smith", "Steven", "2 Kent St", "CBD", "NSW", "2200", "0406057656", "khanhduy_sn@yahoo.com", "09 009 987 666", "GameLand", "logo2.gif");
                    checkBusiness2 = commonEJB.persistData(b2);
                }
                else
                {
                    b2 = (Business)commonEJB.getSingleInfo("select b from Business b where b.username = 'gameland'");
                }
                
                Business b3;
                boolean checkBusinessExist3 = commonEJB.checkExisted("select b from Business b where b.username = 'abc'");
                if(checkBusinessExist3 == false)
                {
                    b3 = new Business("abc", "1234", "Mr", "John", "David", "200 Kent St", "CBD", "NSW", "2200", "0406057444", "khanhduy_sn@yahoo.com", "09 009 987 444", "ABC Shop", "logo3.png");
                    checkBusiness3 = commonEJB.persistData(b3);
                }
                else
                {
                    b3 = (Business)commonEJB.getSingleInfo("select b from Business b where b.username = 'abc'");
                }
                
                if(checkBusiness1 == true && checkBusiness2 == true && checkBusiness3 == true)
                {
                    boolean checkGame1 = false;
                    boolean checkGame2 = false;
                    boolean checkGame3 = false;
                    boolean checkMovie1 = false;
                    boolean checkMovie2 = false;
                    boolean checkMovie3 = false;
                    boolean checkSoundtrack1 = false;
                    boolean checkSoundtrack2 = false;
                
                    SystemRequirement sys1 = new SystemRequirement();
                    sys1.setCPU("AMD Athlon 64 X2 Dual-Core 4000");
                    sys1.setRAM("1.5GB (Windows XP)");
                    sys1.setVGA("ATI X1800");
                    sys1.setHDD("Approximately 8 GB ");
                    sys1.setOS("Windows XP/Vista/7");
                    sys1.setSound("DirectX® 9.0c Compatible Sound Card");
                    sys1.setNote("Note that Windows® 95, Windows® 98, Windows® 2000, Windows® ME, and Windows® NT 4.0 are not supported.");
                    boolean checkRequirement1 = commonEJB.persistData(sys1);
                    if(checkRequirement1 == true)
                    {
                        Game g1 = new Game("Brave", "Action & Adventure", "Play as Merida, a Will 'o the Wisp and other popular characters..", "001.jpg", "Nintendo DS, Nintendo Wii, Playstation 3, X Box 360", "English", new Date(),"http://www.beatthebomb.com.au/games/wii/brave", "PG", "Electronic Arts Inc.", 1);
                        g1.setSystemRequirement(sys1);
                        SalesProduct sp1 = new SalesProduct(44.95, true);
                        boolean checkSalesProduct1 = commonEJB.persistData(sp1);
                        g1.setSalesProduct(sp1);
                        LeasesProduct lp1 = new LeasesProduct(3.5, true);
                        boolean checkLeasesProduct1 = commonEJB.persistData(lp1);
                        g1.setLeasesProduct(lp1);

                        g1.setBusiness(b1);
                        if(checkSalesProduct1 == true && checkLeasesProduct1 == true)
                        {
                            checkGame1 = commonEJB.persistData(g1);
                        }
                    }

                    SystemRequirement sys2 = new SystemRequirement();
                    sys2.setCPU("CDB MAXXIS 32 X2 Dual-Core 3000");
                    sys2.setRAM("2.5GB (Windows XP)");
                    sys2.setVGA("ATI X1800 or better* /nnVidia 7800");
                    sys2.setHDD("Approximately 8 GB ");
                    sys2.setOS("Windows XP/7");
                    sys2.setSound("DirectX® 8.5c Compatible Sound Card");
                    sys2.setNote("Note that Windows® 95, Windows® 98, Windows® 2000, Windows® ME, and Windows® NT 4.0 are not supported.");
                    boolean checkRequirement2 = commonEJB.persistData(sys2);
                    if(checkRequirement2 == true)
                    {
                        Game g2 = new Game("Captain America Super Soldier", "Action & Adventure", "Players will become Captain America as he faces the Red Skull...", "002.jpg", "Nintendo DS, Nintendo Wii, Playstation 3, X Box 360", "English", new Date(),"http://www.game-lane.com.au/wii-games/4047-captain-america-super-soldier-wii.html?utm_source=getprice&utm_medium=cpc", "M", "Electronic Arts Inc.", 1);
                        g2.setSystemRequirement(sys2);
                        SalesProduct sp2 = new SalesProduct(24.95, true);
                        boolean checkSalesProduct2 = commonEJB.persistData(sp2);
                        g2.setSalesProduct(sp2);

                        g2.setBusiness(b2);
                        if(checkSalesProduct2 == true)
                        {
                            checkGame2 = commonEJB.persistData(g2);
                        }
                    }

                    SystemRequirement sys3 = new SystemRequirement();
                    sys3.setCPU("FDX BRIESK 20 X5 Dual-Core 5000");
                    sys3.setRAM("2.5GB (Windows XP)");
                    sys3.setVGA("ATI X2200");
                    sys3.setHDD("Approximately 8 GB");
                    sys3.setOS("Windows XP/Vista");
                    sys3.setSound("DirectX® 10.0c Compatible Sound Card");
                    sys3.setNote("Note that Windows® 95, Windows® 98, Windows® 2000, Windows® ME, and Windows® NT 4.0 are not supported.");
                    boolean checkRequirement3 = commonEJB.persistData(sys3);
                    if(checkRequirement3 == true)
                    {
                        Game g3 = new Game("Disney Universe", "Action & Adventure", "Disney Universe is an off-the-wall non-stop multiplayer action-adventure..", "003.jpg", "Nintendo Wii, Playstation 3", "English", new Date(),"http://www.game-lane.com.au/ps3-games/3600-disney-universe-ps3.html", "G", "Electronic Arts Inc.", 1);
                        g3.setSystemRequirement(sys3);
                        SalesProduct sp3 = new SalesProduct(34.95, true);
                        boolean checkSalesProduct3 = commonEJB.persistData(sp3);
                        g3.setSalesProduct(sp3);

                        g3.setBusiness(b2);
                        if(checkSalesProduct3 == true)
                        {
                            checkGame3 = commonEJB.persistData(g3);
                        }
                    }

                    Movie m1 = new Movie("Men in Black 3" , "Action", "In Men in Black 3, Agents J and K are back...", "007.jpg", "DVD/Bluray", "English", new Date(), "http://www.beatthebomb.com.au/movies/dvd/men_in_black_3", "M", "Will Smith, Tommy Lee Jones, Josh Brolin", "Barry Sonnenfeld", "106 mins");
                    SalesProduct sp4 = new SalesProduct(17.95, true);
                    boolean checkSalesProduct4 = commonEJB.persistData(sp4);
                    m1.setSalesProduct(sp4);
                    LeasesProduct lp4 = new LeasesProduct(2.5, true);
                    m1.setLeasesProduct(lp4);
                    boolean checkLeasesProduct4 = commonEJB.persistData(lp4);
                    m1.setBusiness(b1);
                    if(checkSalesProduct4 == true && checkLeasesProduct4 == true)
                    {
                        checkMovie1 = commonEJB.persistData(m1);
                    }

                    Movie m2 = new Movie("The Avengers" , "Action", "Marvel Studios presents Marvels The Avengersthe Super..", "008.jpg", "DVD/Bluray", "English", new Date(), "http://www.beatthebomb.com.au/movies/dvd/the_avengers_2012_marvel", "M", "Robert Downey Jr., Gwyneth Paltrow, Samuel L. Jackson, Chris Evans, Scarlett", "Joss Whedon", "143 mins");
                    SalesProduct sp5 = new SalesProduct(34.95, true);
                    boolean checkSalesProduct5 = commonEJB.persistData(sp5);
                    m2.setSalesProduct(sp5);
                    LeasesProduct lp5 = new LeasesProduct(2.5, true);
                    m2.setLeasesProduct(lp5);
                    boolean checkLeasesProduct5 = commonEJB.persistData(lp5);
                    m2.setBusiness(b1);
                    if(checkSalesProduct5 == true && checkLeasesProduct5 == true)
                    {
                        checkMovie2 = commonEJB.persistData(m2);
                    }

                    Movie m3 = new Movie("Stash House" , "Action", "A couple love their new house, bought for a steal out of a foreclosure...", "009.jpg", "DVD/Bluray", "English", new Date(), "http://www.beatthebomb.com.au/movies/dvd/stash_house", "MA15+", "Briana Evigan, Sean Faris, Dolph Lundgren", "Eduardo Rodriguez", "99 mins");
                    SalesProduct sp6 = new SalesProduct(13.95, true);
                    boolean checkSalesProduct6 = commonEJB.persistData(sp6);
                    m3.setSalesProduct(sp6);
                    m3.setBusiness(b1);
                    if(checkSalesProduct6 == true)
                    {
                        checkMovie3 = commonEJB.persistData(m3);
                    }

                    Soundtrack s1 = new Soundtrack("Women of Jazz" ,"Jazz", "Track Listing:Goodnite, Dance Me to the End of Love...", "015.jpg" , "CD", "English", new Date(), "http://shop.abc.net.au/products/women-of-jazz", "Putumayo", "Putumayo" , 20);
                    SalesProduct sp7 = new SalesProduct(19.99, true);
                    boolean checkSalesProduct7 = commonEJB.persistData(sp7);
                    s1.setSalesProduct(sp7);
                    s1.setBusiness(b3);
                    if(checkSalesProduct7 == true)
                    {
                        checkSoundtrack1 = commonEJB.persistData(s1);
                    }

                    Soundtrack s2 = new Soundtrack("Minus" ,"Jazz", "Track Listing:No Disguise ,Evil Woman ,Runaway...", "016.jpg" , "CD", "English", new Date(), "http://shop.abc.net.au/products/minus", "Dukes of Windsor", "Dukes of Windsor" , 25);
                    SalesProduct sp8 = new SalesProduct(19.99, true);
                    boolean checkSalesProduct8 = commonEJB.persistData(sp8);
                    s2.setSalesProduct(sp8);
                    s2.setBusiness(b3);
                    if(checkSalesProduct8 == true)
                    {
                        checkSoundtrack2 = commonEJB.persistData(s2);
                    }
                    
                    if(checkGame1 == true && checkGame2 == true && checkGame3 == true && checkMovie1 == true && checkMovie2 == true && checkMovie3 && checkSoundtrack1 == true && checkSoundtrack2 == true)
                    {
                        lsObjects = commonEJB.getListInfoNativeQuery(strQuery1);
                    }
                }
 
            }
            catch(Exception e)
            {
                showMessage("Data cannot persist to database", "Error Messages", "error");
            }
        }
        loadProductData(lsObjects, 1);
        level = "level1";
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
    
    public void setMenuModel()
    {
        lsTypes = productTypes.getLsTypes();
        
        resetContents();
        
        for(int j = 0; j < lsTypes.size(); j++)
        {
            String type = lsTypes.get(j).toString().trim();
        
            List<String> listProducts = commonEJB.getListInfo("select DISTINCT p.category from Product p, " + type + " sample where p.PID = sample.PID");
            if(listProducts.size() > 0)
            {
                Submenu menuType = new Submenu();
                menuType.setLabel(type + " Categories");
                menuType.setId(uiViewRoot.createUniqueId());
                addSubmenu(menuType);
                for(int i = 0; i < listProducts.size(); i++) 
                {
                    MenuItem subCategoryItem = new MenuItem();
                    subCategoryItem.setValue(listProducts.get(i).toString().trim());
                    subCategoryItem.setId(uiViewRoot.createUniqueId());
                    subCategoryItem.setAjax(true);
                    subCategoryItem.setUpdate("@form");
                    subCategoryItem.getAttributes().put("type", type);
                    subCategoryItem.getAttributes().put("category", listProducts.get(i).toString().trim());
                    subCategoryItem.addActionListener(this);
                    addMenuItem(subCategoryItem);
                }
            }
        }
    }
    
    public void loadProductData(List<Object[]> lsObjects, Integer productDetailType)
    {
        if(productDetailType == 1)
        {
            lsProducts.clear();
        }
        if(productDetailType == 2)
        {
            lsComparedProducts.clear();
        }
        for(int i = 0; i < lsObjects.size(); i++)
        {
            Object[] obj = lsObjects.get(i);
            Long PID = (Long)obj[0];
            String title = obj[1].toString().trim();
            String image = obj[2].toString().trim();
            String salesPrice = "$";
            String salesAvailable = "0";
            if(obj[3] == null)
            {
                salesPrice = salesPrice + "0";
            }
            else
            {
                salesPrice = salesPrice + obj[3];
                salesAvailable = "1";
            }
           
            String leasesPrice = "$";
            String leasesAvailable = "0";
            if(obj[4] == null)
            {
                leasesPrice = leasesPrice + "0";
            }
            else
            {
                leasesPrice = leasesPrice + obj[4];
                leasesAvailable = "1";
            }
            String category = obj[5].toString().trim();
            ProductDetail pd;
            if(productDetailType == 1)
            {
                Integer nbrOfBrands = Integer.parseInt(obj[6].toString());
                pd = new ProductDetail(PID, title, image, salesPrice, salesAvailable, leasesPrice, leasesAvailable, category, nbrOfBrands);
                lsProducts.add(pd);
            }
            if(productDetailType == 2)
            {
                String description = obj[6].toString().trim();
                String productLink = obj[7].toString().trim();
                String businessName = obj[8].toString().trim();
                String businessLogo = obj[9].toString().trim();
                pd = new ProductDetail(PID, title, category, description, image, productLink, businessName, businessLogo, salesPrice, salesAvailable, leasesPrice, leasesAvailable);
                lsComparedProducts.add(pd);

            }
        }
    }
    
    public List<ProductDetail> getLsProducts()
    {
        return lsProducts;
    }
    public void setLsProducts (List<ProductDetail> lsProducts)
    {
        this.lsProducts = lsProducts;
    }
    
    public List<ProductDetail> getLsComparedProducts()
    {
        return lsComparedProducts;
    }
    public void setLsComparedProducts (List<ProductDetail> lsComparedProducts)
    {
        this.lsComparedProducts = lsComparedProducts;
    }
    
    // Gets called when a MenuItem is clicked. The clicked MenuItem becomes the new top level MenuItem.
    @Override
    public void processAction(ActionEvent event) throws AbortProcessingException 
    {
        if(event.getSource().getClass() == MenuItem.class) 
        {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            String currentPage = facesContext.getViewRoot().getViewId();

            boolean isHomePage = (currentPage.lastIndexOf("home.xhtml") > -1);
            if(!isHomePage)
            {
                try 
                {
                    FacesContext.getCurrentInstance().getExternalContext().dispatch("/home.xhtml?faces-redirect=true");
                } 
                catch (IOException ex) 
                {
                    Logger.getLogger(CategoryMenuModel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            MenuItem sourceItem = (MenuItem) event.getSource();
            selectedType = (String) sourceItem.getAttributes().get("type");
            selectedCategory = (String) sourceItem.getAttributes().get("category");
            
            String strQuery = getStrQuery(3);
            List<Object[]> lsObjects = commonEJB.getListInfoNativeQuery(strQuery);
            loadProductData(lsObjects, 1);
           
            level = "level3";
            
        }
    }
    
    public String getStrQuery(Integer level)
    {
        String tableName = "";
        if(level == 2 || level == 3)
        {
            if(selectedType.equalsIgnoreCase("Movie"))
            {
                tableName = "movie_table";
            }
            else if(selectedType.equalsIgnoreCase("Game"))
            {
                tableName = "game_table";
            }
            else
            {
                tableName = "soundtrack_table";
            }
        }
        if(level == 5 && !selectedTypeSearch.equalsIgnoreCase("All Products"))
        {
            if(selectedTypeSearch.equalsIgnoreCase("Movie"))
            {
                tableName = "movie_table";
            }
            else if(selectedTypeSearch.equalsIgnoreCase("Game"))
            {
                tableName = "game_table";
            }
            else
            {
                tableName = "soundtrack_table";
            }
        }
        
        String strQuery = "SELECT a.PID, LEAST(a.title, b.title) titlebase, a.image, min(sp.salesPrice) salesPrice, min(lp.leasesPrice) leasesPrice, a.category, count(*), max(a.createdDate) as createdDates";
	strQuery = strQuery + " FROM product_table a LEFT JOIN salesProduct_table sp ON a.salesProduct_fk = sp.SPID LEFT JOIN leasesProduct_table lp ON a.leasesProduct_fk = lp.LPID, product_table b";
	if(level == 2 || level == 3 || (level == 5 && !selectedTypeSearch.equalsIgnoreCase("All Products")))
        {
            strQuery = strQuery + " ," + tableName + " sample";
        }
        strQuery = strQuery + " WHERE a.PID != b.PID AND a.DTYPE = b.DTYPE AND a.category = b.category";
        strQuery = strQuery + " AND jaro_winkler_similarity( a.title , b.title ) >= 0.95";
	strQuery = strQuery + " AND ( (a.salesProduct_fk IS NOT NULL and sp.available = '1') or (a.leasesProduct_fk IS NOT NULL AND lp.available = '1'))";
	if(level == 2 || level == 3 || (level == 5 && !selectedTypeSearch.equalsIgnoreCase("All Products")))
        {
            strQuery = strQuery + " AND a.PID = sample.PID";
        }
        if(level == 3)
        {
            strQuery = strQuery + " AND a.category = '" + selectedCategory + "'";
        }
        if(level == 4)
        {
            strQuery = strQuery + " AND a.title LIKE '%" + strSearch + "%'";
        }
        if(level == 5)
        {
            strQuery = strQuery + " AND a.title LIKE '%" + strAdvancedSearch + "%'";
        }
        if(level == 5 && !selectedCategorySearch.equalsIgnoreCase("All Categories"))
        {
            strQuery = strQuery + " AND a.category = '" + selectedCategorySearch + "'";
        }
        if(level == 5 && !selectedOption.equalsIgnoreCase("both"))
        {
            if(selectedOption.equalsIgnoreCase("buyDVD"))
            {
                strQuery = strQuery + " AND (a.salesProduct_fk IS NOT NULL and sp.available = '1')";
            }
            else
            {
                strQuery = strQuery + " AND (a.leasesProduct_fk IS NOT NULL and lp.available = '1')";
            }
        }
        if(level == 5 && minimumSearchPrice != null)
        {
            strQuery = strQuery + " AND sp.salesPrice >= " + minimumSearchPrice;
        }
        if(level == 5 && maximumSearchPrice != null)
        {
            strQuery = strQuery + " AND sp.salesPrice <= " + maximumSearchPrice;
        }
        
        strQuery = strQuery + " group by titlebase";
        strQuery = strQuery + " UNION";
        strQuery = strQuery + " Select a.PID, a.title, a.image, sp.salesPrice, lp.leasesPrice, a.category, 1, a.createdDate as createdDates";
        strQuery = strQuery + " FROM product_table a LEFT JOIN salesProduct_table sp ON a.salesProduct_fk = sp.SPID LEFT JOIN leasesProduct_table lp ON a.leasesProduct_fk = lp.LPID";
        if(level == 2 || level == 3 || (level == 5 && !selectedTypeSearch.equalsIgnoreCase("All Products")))
        {
            strQuery = strQuery + " ," + tableName + " sample";
        }
        strQuery = strQuery + " WHERE a.PID NOT IN (";
        strQuery = strQuery + " SELECT DISTINCT(t.PID) from (";
        strQuery = strQuery + " SELECT LEAST(a.title, b.title) titlebase, a.PID, a.category, count(*)";
	strQuery = strQuery + " FROM product_table a LEFT JOIN salesProduct_table sp ON a.salesProduct_fk = sp.SPID LEFT JOIN leasesProduct_table lp ON a.leasesProduct_fk = lp.LPID, product_table b";
	strQuery = strQuery + " WHERE a.PID != b.PID AND a.DTYPE = b.DTYPE AND a.category = b.category";
	strQuery = strQuery + " AND jaro_winkler_similarity( a.title , b.title ) >= 0.95";
	strQuery = strQuery + " AND ( (a.salesProduct_fk IS NOT NULL and sp.available = '1') or (a.leasesProduct_fk IS NOT NULL AND lp.available = '1'))";
        strQuery = strQuery + " GROUP BY titlebase";
        strQuery = strQuery + " ) as sub, product_table t";
        strQuery = strQuery + " WHERE  jaro_winkler_similarity( sub.titlebase , t.title ) >= 0.95";
        strQuery = strQuery + " AND sub.category = t.category)";
        strQuery = strQuery + " AND ( (a.salesProduct_fk IS NOT NULL and sp.available = '1') or (a.leasesProduct_fk IS NOT NULL AND lp.available = '1'))";
        if(level == 2 || level == 3 || (level == 5 && !selectedTypeSearch.equalsIgnoreCase("All Products")))
        {
            strQuery = strQuery + " AND a.PID = sample.PID";
        }
        if(level == 3)
        {
            strQuery = strQuery + " AND a.category = '" + selectedCategory + "'";
        }
        if(level == 4)
        {
            strQuery = strQuery + " AND a.title LIKE '%" + strSearch + "%'";
        }
        if(level == 5)
        {
            strQuery = strQuery + " AND a.title LIKE '%" + strAdvancedSearch + "%'";
        }
        if(level == 5 && !selectedCategorySearch.equalsIgnoreCase("All Categories"))
        {
            strQuery = strQuery + " AND a.category = '" + selectedCategorySearch + "'";
        }
        if(level == 5 && !selectedOption.equalsIgnoreCase("both"))
        {
            if(selectedOption.equalsIgnoreCase("buyDVD"))
            {
                strQuery = strQuery + " AND (a.salesProduct_fk IS NOT NULL and sp.available = '1')";
            }
            else
            {
                strQuery = strQuery + " AND (a.leasesProduct_fk IS NOT NULL and lp.available = '1')";
            }
        }
        if(level == 5 && minimumSearchPrice != null)
        {
            strQuery = strQuery + " AND sp.salesPrice >= " + minimumSearchPrice;
        }
        if(level == 5 && maximumSearchPrice != null)
        {
            strQuery = strQuery + " AND sp.salesPrice <= " + maximumSearchPrice;
        }
        
        strQuery = strQuery + " order by createdDates;";

        return strQuery;
    }
    
    @Override
    public List<UIComponent> getContents() {
        return contents;
    }
 
    @Override
    public void addSubmenu(Submenu submenu) {
        contents.add(submenu);
    }
 
    @Override
    public void addMenuItem(MenuItem menuItem) {
        contents.add(menuItem);
    }
    
    protected void resetContents() {
        contents = new ArrayList<UIComponent>();
    }

    @Override
    public void addSeparator(Separator sprtr) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public String getLevel()
    {
        return level;
    }
    
    public void setLevel(String level)
    {
        this.level = level;
    }
    
    public String getSelectedType()
    {
        return selectedType;
    }
    
    public void setSelectedType(String selectedType)
    {
        this.selectedType = selectedType;
    }
    
    public String getSelectedCategory()
    {
        return selectedCategory;
    }
    
    public void setSelectedCategory(String selectedCategory)
    {
        this.selectedCategory = selectedCategory;
    }
    
    public String getTitleGroup()
    {
        return titleGroup;
    }
    
    public void setTitleGroup(String titleGroup)
    {
        this.titleGroup = titleGroup;
    }
    
    public String getImageGroup()
    {
        return imageGroup;
    }
    
    public void setImageGroup(String imageGroup)
    {
        this.imageGroup = imageGroup;
    }
    
    public String getMinSalesPrice()
    {
        return minSalesPrice;
    }
    
    public void setMinSalesPrice(String minSalesPrice)
    {
        this.minSalesPrice = minSalesPrice;
    }
    
    public String getMinLeasesPrice()
    {
        return minLeasesPrice;
    }
    
    public void setMinLeasesPrice(String minLeasesPrice)
    {
        this.minLeasesPrice = minLeasesPrice;
    }
    
    public Product getProduct()
    {
        return product;
    }
    
    public void setProduct(Product product)
    {
        this.product = product;
    }
    
    public Movie getMovie()
    {
        return movie;
    }
    
    public void setMovie(Movie movie)
    {
        this.movie = movie;
    }
    
    public Game getGame()
    {
        return game;
    }
    
    public void setGame(Game game)
    {
        this.game = game;
    }
    
    public Soundtrack getSoundtrack()
    {
        return soundtrack;
    }
    
    public void setSoundtrack(Soundtrack soundtrack)
    {
        this.soundtrack = soundtrack;
    }
    
    public SystemRequirement getSystemRequirement()
    {
        return systemRequirement;
    }
    
    public void setSystemRequirement(SystemRequirement systemRequirement)
    {
        this.systemRequirement = systemRequirement;
    }
    
    public String getStrSearch()
    {
        return strSearch;
    }
    
    public void setStrSearch(String strSearch)
    {
        this.strSearch = strSearch;
    }
    
    public String getSelectedTypeSearch()
    {
        return selectedTypeSearch;
    }
    
    public void setSelectedTypeSearch(String selectedTypeSearch)
    {
        this.selectedTypeSearch = selectedTypeSearch;
    }
    
    public String getSelectedCategorySearch()
    {
        return selectedCategorySearch;
    }
    
    public void setSelectedCategorySearch(String selectedCategorySearch)
    {
        this.selectedCategorySearch = selectedCategorySearch;
    }
    
    public DateFormat getFormatter()
    {
        return formatter;
    }
    
    public String getStrAdvancedSearch()
    {
        return strAdvancedSearch;
    }
    
    public void setStrAdvancedSearch(String strAdvancedSearch)
    {
        this.strAdvancedSearch = strAdvancedSearch;
    }
    
    public String[] getListType()
    {
        return listType;
    }
    
    public List<String> getListCategoriesSearch()
    {
        return listCategoriesSearch;
    }
    
    public void setListCategoriesSearch(List<String> listCategoriesSearch)
    {
        this.listCategoriesSearch = listCategoriesSearch;
    }
    
    public String getSelectedOption()
    {
        return selectedOption;
    }
    
    public void setSelectedOption(String selectedOption)
    {
        this.selectedOption = selectedOption;
    }
    
    public Double getMinimumSearchPrice()
    {
        return minimumSearchPrice;
    }
    
    public void setMinimumSearchPrice(Double minimumSearchPrice)
    {
        this.minimumSearchPrice = minimumSearchPrice;
    }
    
    public Double getMaximumSearchPrice()
    {
        return maximumSearchPrice;
    }
    
    public void setMaximumSearchPrice(Double maximumSearchPrice)
    {
        this.maximumSearchPrice = maximumSearchPrice;
    }
    
    
    public void changeLevel(Integer levelNbr)
    {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        String currentPage = facesContext.getViewRoot().getViewId();

        boolean isHomePage = (currentPage.lastIndexOf("home.xhtml") > -1);
        if(!isHomePage)
        {
            try 
            {
                FacesContext.getCurrentInstance().getExternalContext().dispatch("home.xhtml");
            } 
            catch (IOException ex) 
            {
                Logger.getLogger(CategoryMenuModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        String strQuery =  "";
        if(levelNbr == 1)
        {
            strQuery = getStrQuery(1);
            level = "level1";
        }
        else
        {
            if(levelNbr == 2)
            {
                strQuery = getStrQuery(2);
                level = "level2";
            }
            else
            {
                if(levelNbr == 3)
                {
                    strQuery = getStrQuery(3);
                    level = "level3";
                }
            }
        }
        List<Object[]> lsObjects = commonEJB.getListInfoNativeQuery(strQuery);
        loadProductData(lsObjects, 1);
    }
    
    
    public String showDetails(Long PID) throws IOException
    {
        if(lsProducts.size() > 0)
        {
            for(int i = 0; i < lsProducts.size(); i++)
            {
                if(lsProducts.get(i).getPID() == PID)
                {
                    categoryGroup = lsProducts.get(i).getCategory().toString().trim();
                    titleGroup = lsProducts.get(i).getTitle().toString().trim();
                    imageGroup = lsProducts.get(i).getImage().toString().trim();
                    minSalesPrice = lsProducts.get(i).getSalesPrice();
                    minLeasesPrice = lsProducts.get(i).getLeasesPrice();
                }
            }
            
        }
        
        String table_name = "";
        product = (Product)commonEJB.getSingleInfo("Select s from Product s where s.PID = " + PID);
        if(commonEJB.checkExisted("Select m from Movie m where m.PID = " + PID))
        {
            movie = (Movie)commonEJB.getSingleInfo("Select m from Movie m where m.PID = " + PID);
            selectedType = "Movie";
            table_name = "movie_table";
        }
        if(commonEJB.checkExisted("Select g from Game g where g.PID = " + PID))
        {
            game = (Game)commonEJB.getSingleInfo("Select g from Game g where g.PID = " + PID);
            selectedType = "Game";
            table_name = "game_table";
            systemRequirement = (SystemRequirement)commonEJB.getSingleInfo("Select sr from Game g JOIN g.systemRequirement sr where g.PID = " + PID);
        }
        if(commonEJB.checkExisted("Select s from Soundtrack s where s.PID = " + PID))
        {
            soundtrack = (Soundtrack)commonEJB.getSingleInfo("Select s from Soundtrack s where s.PID = " + PID);
            selectedType = "Soundtrack";
            table_name = "soundtrack_table";
        }
        
        String strQuery = "Select s.PID, s.title, s.image, sp.salesPrice, lp.leasesPrice, s.category, s.description, s.productLink, b.businessName, b.businessLogo";
        strQuery = strQuery + " From product_table s LEFT JOIN salesProduct_table sp ON s.salesProduct_fk = sp.SPID LEFT JOIN leasesProduct_table lp ON s.leasesProduct_fk = lp.LPID";
        strQuery = strQuery + " LEFT JOIN business_table b ON s.businessProduct_fk = b.username";
        strQuery = strQuery + " Where jaro_winkler_similarity('" + titleGroup + "', s.title) >= 0.95";
	strQuery = strQuery + " AND ( (s.salesProduct_fk IS NOT NULL and sp.available = '1') or (s.leasesProduct_fk IS NOT NULL AND lp.available = '1'))";
        strQuery = strQuery + " AND s.PID IN (Select sample.PID from " + table_name + " sample )";
        strQuery = strQuery + " AND s.category = '" + categoryGroup + "'";
        strQuery = strQuery + " Order by case when sp.salesPrice is null then 1 else 0 end, case when lp.leasesPrice is null then 1 else 0 end, sp.salesPrice, lp.leasesPrice ASC;";
        List<Object[]> lsObjects = commonEJB.getListInfoNativeQuery(strQuery);

        loadProductData(lsObjects, 2);
        
        level = "level2";
        
        return "/productDetails.xhtml?faces-redirect=true";

    }
    
    public String doSearch(Integer type)
    {
        String strReturn = "";
        FacesContext facesContext = FacesContext.getCurrentInstance();
        String currentPage = facesContext.getViewRoot().getViewId();

        boolean isHomePage = (currentPage.lastIndexOf("home.xhtml") > -1);
        if(!isHomePage)
        {
            strReturn = "/home.xhtml?faces-redirect=true";
        }
        String strQuery = "";
        if(type == 1)
        {
            strQuery = getStrQuery(4);
        }
        if(type == 2)
        {
            strQuery = getStrQuery(5);
        }
        try
        {
        List<Object[]> lsObjects = commonEJB.getListInfoNativeQuery(strQuery);
        loadProductData(lsObjects, 1);
        }
        catch(Exception e)
        {
            showMessage("wrong", "wrong", "error");
        }
        
        level = "level1";
        
        return strReturn;
    }
    
    public String search()
    {
        return doSearch(1);
    }
    
    public void changeCategorySearch()
    {
        if(selectedTypeSearch.equalsIgnoreCase("All Products"))
        {
            listCategoriesSearch.clear();
        }
        else if(selectedTypeSearch.equalsIgnoreCase("Movie"))
        {
            listCategoriesSearch = commonEJB.getListInfo("select DISTINCT m.category from Movie m");
        }
        else if(selectedTypeSearch.equalsIgnoreCase("Game"))
        {
            listCategoriesSearch = commonEJB.getListInfo("select DISTINCT g.category from Game g");
        }
        else
        {
            listCategoriesSearch = commonEJB.getListInfo("select DISTINCT s.category from Soundtrack s");
        }
        listCategoriesSearch.add(0, "All Categories");
    }
    
    public void resetAdvancedSearch()
    {
        strAdvancedSearch = null;
        selectedTypeSearch = "All Products";
        selectedCategorySearch = "All Categories";
        selectedOption = "both";
        minimumSearchPrice = null;
        maximumSearchPrice = null;
    }
    
    public String searchAdvanced()
    {
        return doSearch(2);
    }

}