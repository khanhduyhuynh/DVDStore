package com.capstone.Controller;

import com.capstone.EJB.CommonEJB;
import com.capstone.Entity.Business;
import com.capstone.Entity.LeasesProduct;
import com.capstone.Entity.Movie;
import com.capstone.Entity.MovieCategory;
import com.capstone.Entity.MovieClassification;
import com.capstone.Entity.MovieDetail;
import com.capstone.Entity.SalesProduct;
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

public class ManageMovieController 
{
    @EJB
    private CommonEJB commonEJB;
    
    private ManageDataController manageDataController = new ManageDataController();
    private ImageUploadController upload = new ImageUploadController();

    private Movie movie = new Movie();
    private SalesProduct salesProduct = new SalesProduct();
    private LeasesProduct leasesProduct = new LeasesProduct();
    
    private List<Object[]> detailList = new ArrayList<Object[]>();
    private List<MovieDetail> lsMovieDetail = new ArrayList<MovieDetail>();

    private MovieClassification movieClassification = new MovieClassification();
    private List<String> movieClassifications = new ArrayList<String>();
    private MovieCategory movieCategory = new MovieCategory();
    private List<String> movieCategories = new ArrayList<String>();
    
    private String strQuery;
    private MovieDetail[] selectedMovies;
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
        
        movieClassifications = movieClassification.getLsMovieClassification();
        movieCategories = movieCategory.getLsMovieCategory();

        loadMovie();
    }
    
    public void loadMovie()
    {
        lsMovieDetail.clear();
        strQuery = "(select p.PID, p.title, p.category, p.description, p.image, p.format, p.language, p.releasedDate, p.productLink, m.classification, m.castName, m.director, m.runtime, sp.salesPrice, sp.available, lp.leasesPrice, lp.available, p.createdDate from product_table as p, movie_table as m, salesProduct_table as sp, leasesProduct_table as lp, business_table as b where p.PID = m.PID and p.salesProduct_fk = sp.SPID  and p.leasesProduct_fk = lp.LPID and p.businessProduct_fk = b.username and b.username = '" + username + "')";
        strQuery = strQuery + " UNION (select p.PID, p.title, p.category, p.description, p.image, p.format, p.language, p.releasedDate, p.productLink, m.classification, m.castName, m.director, m.runtime, sp.salesPrice, sp.available, 0.0, 0, p.createdDate from product_table as p, movie_table as m, salesProduct_table as sp, business_table as b where p.PID = m.PID and p.salesProduct_fk = sp.SPID  and p.leasesProduct_fk IS NULL and p.businessProduct_fk = b.username and b.username = '" + username + "')";
        strQuery = strQuery + " UNION (select p.PID, p.title, p.category, p.description, p.image, p.format, p.language, p.releasedDate, p.productLink, m.classification, m.castName, m.director, m.runtime, 0.0, 0, lp.leasesPrice, lp.available, p.createdDate from product_table as p, movie_table as m, leasesProduct_table as lp, business_table as b where p.PID = m.PID and p.leasesProduct_fk = lp.LPID  and p.salesProduct_fk IS NULL and p.businessProduct_fk = b.username and b.username = '" + username + "')";
        strQuery = strQuery + " UNION (select p.PID, p.title, p.category, p.description, p.image, p.format, p.language, p.releasedDate, p.productLink, m.classification, m.castName, m.director, m.runtime, 0.0, 0, 0.0, 0, p.createdDate from product_table as p, movie_table as m, business_table as b where p.PID = m.PID and p.leasesProduct_fk IS NULL and p.salesProduct_fk IS NULL and p.businessProduct_fk = b.username and b.username = '" + username + "')";
        strQuery = strQuery + " ORDER BY createdDate DESC";
        detailList = commonEJB.getListInfoNativeQuery(strQuery);
        loadMovieData(detailList);
    }
    
    public void loadMovieData(List<Object[]> lsObjects)
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
            String castName = obj[10].toString().trim();
            String director = obj[11].toString().trim();
            String runtime = obj[12].toString().trim();
            String strSalesPrice = obj[13].toString();
            Double salesPrice = Double.parseDouble(strSalesPrice);

            String salesAvailable = obj[14].toString().trim().toLowerCase();
            if(salesAvailable.equalsIgnoreCase("1"))
            {
                salesAvailable = "Available";
            }
            else
            {
                salesAvailable = "Not Available";
            }
            String strLeasesPrice = obj[15].toString();
            Double leasesPrice = Double.parseDouble(strLeasesPrice);
            
            String leasesAvailable = obj[16].toString().trim().toLowerCase();
            if(leasesAvailable.equalsIgnoreCase("true"))
            {
                leasesAvailable = "Available";
            }
            else
            {
                leasesAvailable = "Not Available";
            }
            
            MovieDetail md = new MovieDetail(PID, title, category, description, image, format, language, releasedDate, productLink, classification, castName, director, runtime, salesPrice, salesAvailable, leasesPrice, leasesAvailable);
            lsMovieDetail.add(md);
        }
    }
    
    public void deleteMovies()
    {
        int count = selectedMovies.length;
        if(count > 0)
        {
            boolean check = true;
            for(int i = 0; i < count; i++)
            {
                Long PID = selectedMovies[i].getMDID();
                Movie m = (Movie)commonEJB.getSingleInfo("select m from Movie m where m.PID = " + PID);
                boolean checkImage = upload.deleteImage(m.getImage().toString().trim());
                if(checkImage == true)
                {
                    boolean checkDelete = commonEJB.deleteData(m);
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
                loadMovie();
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
    
    public void quickEditMovie(RowEditEvent event)
    {
        Long PID = Long.parseLong(((MovieDetail) event.getObject()).getMDID().toString());
        Movie m = (Movie)commonEJB.getSingleInfo("select m from Movie m where m.PID = " + PID);
        m.setTitle(((MovieDetail) event.getObject()).getTitle().toString());
        SalesProduct sp = new SalesProduct();
        if(m.getSalesProduct() != null)
        {
            sp = (SalesProduct)commonEJB.getSingleInfo("select sp from Movie m JOIN m.salesProduct sp where m.PID = " + PID);
        }
        sp.setSalesPrice(((MovieDetail) event.getObject()).getSalesPrice());
        if(((MovieDetail) event.getObject()).getSalesAvailable().toString().equalsIgnoreCase("Available"))
        {
            sp.setAvailable(true);
        }
        else
        {
            sp.setAvailable(false);
        }
        LeasesProduct lp = new LeasesProduct();
        if(m.getLeasesProduct() != null)
        {
            lp = (LeasesProduct)commonEJB.getSingleInfo("select lp from Movie m JOIN m.leasesProduct lp where m.PID = " + PID);
        }
        lp.setLeasesPrice(((MovieDetail) event.getObject()).getLeasesPrice());
        if(((MovieDetail) event.getObject()).getLeasesAvailable().toString().equalsIgnoreCase("Available"))
        {
            lp.setAvailable(true);
        }
        else
        {
            lp.setAvailable(false);
        }
        boolean checkMovie = commonEJB.updateData(m);
        boolean checkSalesProduct = commonEJB.updateData(sp);
        boolean checkLeasesProduct = commonEJB.updateData(lp);
        if(checkMovie == true && checkSalesProduct == true && checkLeasesProduct == true)
        {
            visible = false;
            showMessage("Success", "Item has been edited", "info");
        }
        else
        {
            showMessage("Error Message", "Item can not edit", "error");
        }
       
    }
    
    public void displayCreateNew()
    {
        strAction = "Create a new movie";
        imageProduct = "";
        salesProductAvailable = "Not Available";
        leasesProductAvailable = "Not Available";
        movie = new Movie();
        salesProduct = new SalesProduct();
        leasesProduct = new LeasesProduct();
        visible = true;
    }
    
    public void createNewMovie() throws IOException
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
                boolean checkMovie = handleAction("create");
                if(checkMovie == true)
                {
                    loadMovie();
                    visible = false;
                    showMessage("Success", "A new movie has created", "info");
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
        boolean checkMovie = false;
        boolean checkSalesProduct = true;
        boolean checkLeasesProduct = true;
        
        if(movie.getSalesProduct() == null)
        {
            if(salesProductAvailable.equalsIgnoreCase("Available"))
            {
                salesProduct.setAvailable(true);
                checkSalesProduct = commonEJB.persistData(salesProduct);
                if(checkSalesProduct == true)
                {
                    movie.setSalesProduct(salesProduct);
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
                movie.setSalesProduct(salesProduct);
            }
            else
            {
                showMessage("Error Message", "Data cannot persist to database", "error");
            }
        }
        
        if(movie.getLeasesProduct() == null)
        {
            if(leasesProductAvailable.equalsIgnoreCase("Available"))
            {
                leasesProduct.setAvailable(true);
                checkLeasesProduct = commonEJB.persistData(leasesProduct);
                if(checkLeasesProduct == true)
                {
                    movie.setLeasesProduct(leasesProduct);
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
                movie.setLeasesProduct(leasesProduct);
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
                movie.setBusiness(business);
            }
            boolean checkImage = true;
            if(type.equalsIgnoreCase("edit"))
            {
                if(!imageProduct.equalsIgnoreCase(movie.getImage().toString().trim()))
                {
                    checkImage = upload.deleteImage(movie.getImage().toString().trim());
                }
            }
            if(checkImage == true)
            {
                movie.setImage(imageProduct);

                if(type.equalsIgnoreCase("create"))
                {
                    checkMovie = commonEJB.persistData(movie);
                }

                if(type.equalsIgnoreCase("edit"))
                {
                    checkMovie = commonEJB.updateData(movie);
                }
            }
        }
        return checkMovie;
    }
    
    public void uploadImage(FileUploadEvent event) throws IOException
    {
        boolean checkImage = true;
        
        if(movie.getImage() != null)
        {
            if(!imageProduct.equalsIgnoreCase(movie.getImage().toString().trim()))
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
        strAction = "Edit movie details";
        
        movie = (Movie)commonEJB.getSingleInfo("select m from Movie m where m.PID = " + PID);
        if(movie.getSalesProduct() != null)
        {
            salesProduct = (SalesProduct)commonEJB.getSingleInfo("select sp from Movie m JOIN m.salesProduct sp where m.PID = " + PID);
        
        }
        else
        {
            salesProduct = new SalesProduct();
        }
        if(movie.getLeasesProduct() != null)
        {
            leasesProduct = (LeasesProduct)commonEJB.getSingleInfo("select lp from Movie m JOIN m.leasesProduct lp where m.PID = " + PID);
        
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
        
        imageProduct = movie.getImage().toString();
        visible = true;
    }
    
    public void editMovie()
    {
        boolean checkMovie = handleAction("edit");
        if(checkMovie == true)
        {
            loadMovie();
            visible = false;
            showMessage("Success", "A movie has edited", "info");
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
        if(!imageProduct.equalsIgnoreCase(movie.getImage().toString().trim()))
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
          
        lsTitles = commonEJB.getListInfo("select m.title from Movie m");
        for(String title : lsTitles) 
        {  
            if(title.toLowerCase().startsWith(query.toLowerCase()))
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

    public List<String> getMovieClassifications()
    {
        return movieClassifications;
    }
    
    public List<String> getMovieCategories()
    {
        return movieCategories;
    }
    
    public List<MovieDetail> getLsMovieDetail()
    {
        return lsMovieDetail;
    }
    
    public void setLsMovieDetail()
    {
        loadMovie();
    }
   
    public MovieDetail[] getSelectedMovies()
    {
        return selectedMovies;
    }
    
    public void setSelectedMovies(MovieDetail[] selectedMovies)
    {
        this.selectedMovies = selectedMovies;
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

    public Movie getMovie()
    {
        return movie;
    }
    
    public void setMovie(Movie movie)
    {
        this.movie = movie;
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