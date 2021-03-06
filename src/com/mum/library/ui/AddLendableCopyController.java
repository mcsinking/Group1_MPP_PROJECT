package com.mum.library.ui;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import com.mum.library.dataaccess.DataAccess;
import com.mum.library.dataaccess.DataAccessFacade;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import com.mum.library.business.*;

import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class AddLendableCopyController{

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<Book, String> titleColumn;

    @FXML
    private TextField numberOfCopyText;

    @FXML
    private TextField isbnText;

    @FXML
    private TableView<Book> bookTableView;

    @FXML
    private TableColumn<Book, String> isbnColumn;

    @FXML
    private TableColumn<Book, String> numberOfCopyColumn;
    
    @FXML
    private Label titleLabel;

    private ObservableList<Book> bookData;
    
    
    @FXML
    void searchClick(ActionEvent event) throws Exception {
    	
    	String isbn = isbnText.getText();
    	if (!isbn.isEmpty()) {
    		
    		if (isBookExist(isbn)) {
    			Book book = searchBook(isbn);
            	
        		titleLabel.setText(book.getTitle());
        		numberOfCopyText.setText("1");
        	
    		}else {
    			showAlert("ISBN book not found!");
    		}
    		
    	}else {
    		showAlert("Please input ISBN.");
    	}
    	
    }
    
    
    @FXML 
    void myRowClick(MouseEvent event) {
    	
    	String isbn = bookData.get(bookTableView.getSelectionModel().getSelectedIndex()).getIsbn();
    	DataAccess data = new DataAccessFacade();
    	HashMap<String, Book> map = data.readBooksMap();
  
    	if (map.containsKey(isbn)) {
			//System.out.println("ID " + isbn + " not found");
			Book book = map.get(isbn);
			isbnText.setText(book.getIsbn());
			titleLabel.setText(book.getTitle());
			numberOfCopyText.setText("1");
		}
    }
    
    @FXML
    void addCopy(ActionEvent event) throws Exception {
    	
    	String isbn = isbnText.getText();
    	String numberOfCopyString = numberOfCopyText.getText();
    	
    	if (!isbn.isEmpty() && !numberOfCopyString.isEmpty()) 
    	{
    		if (isBookExist(isbn)) {
    			if (isbn.isEmpty() || numberOfCopyText.getText().isEmpty())
        			throw new Exception("All the fields is required!");
            	
            	int numberOfCopy = Integer.parseInt(numberOfCopyString);
            	
            	for (int i=0; i<numberOfCopy; i++) {
            		addCopyBook(isbn);
            	}
            	
            	showAlert("This copy of book is added successfully to system");
    		}else {
    			showAlert("No book found!");
    		}
    		
    	}else {
    		showAlert("Please input all the required fields!");
    	}
    }
    
    
    void showAlert(String s) {
    	Alert alert = new Alert(AlertType.INFORMATION);
		alert.setContentText(s);
		alert.setTitle("Library System");
		alert.setHeaderText(null);
		alert.showAndWait();
    }
    
    boolean addCopyBook(String isbn) throws Exception {
    	
    	Book book = searchBook(isbn);
		if (book == null || isbn == "" || numberOfCopyText.getText() == "")
			throw new Exception("No book with isbn " + isbn
					+ " is in the library collection!");
		
		DataAccess da = new DataAccessFacade();
		HashMap<String, Book> map = da.readBooksMap();
		map.get(isbn).addCopy();
		List<Book> allBooks = new ArrayList<Book>();
		for (Book value : map.values()) {
			allBooks.add(value);
		}		
		DataAccessFacade.saveBookMap(allBooks);
		clearForm();
		loadTableData();
		
		return true;
    }
    
    
    void clearForm() {
    	isbnText.clear();
    	numberOfCopyText.clear();
    	titleLabel.setText("");
    }
    
    boolean isBookExist(String isbn) {
    	DataAccess data = new DataAccessFacade();
    	HashMap<String, Book> map = data.readBooksMap();
    	
    	if (!map.containsKey(isbn)) {
			return false;
		}
    	
    	return true;
    }
    
    Book searchBook(String isbn) throws Exception {
    	DataAccess data = new DataAccessFacade();
    	HashMap<String, Book> map = data.readBooksMap();
    	
    	if (map.containsKey(isbn)) {
			Book book = map.get(isbn);
			return book;
		}else {
			showAlert("Book not found!");
			throw new Exception("No book found!");
		}
    }
    
    @FXML
    void initialize() {
        assert titleColumn != null : "fx:id=\"titleColumn\" was not injected: check your FXML file 'AddLendableCopy.fxml'.";
        assert numberOfCopyText != null : "fx:id=\"numberOfCopyText\" was not injected: check your FXML file 'AddLendableCopy.fxml'.";
        assert isbnText != null : "fx:id=\"isbnText\" was not injected: check your FXML file 'AddLendableCopy.fxml'.";
        assert bookTableView != null : "fx:id=\"bookTableView\" was not injected: check your FXML file 'AddLendableCopy.fxml'.";
        assert isbnColumn != null : "fx:id=\"isbnColumn\" was not injected: check your FXML file 'AddLendableCopy.fxml'.";
        assert numberOfCopyColumn != null : "fx:id=\"numberOfCopyColumn\" was not injected: check your FXML file 'AddLendableCopy.fxml'.";
        loadTableData();
    }
    
    private void loadTableData() {
    	
		bookData = FXCollections.observableArrayList();
		DataAccess da = new DataAccessFacade();
		HashMap<String, Book> map = da.readBooksMap();
		Iterator it = map.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, Book> book = (Map.Entry) it.next();
			bookData.add(book.getValue());
		}
		
		bookTableView.setItems(bookData);
		isbnColumn.setCellValueFactory(new PropertyValueFactory<Book, String>("isbn"));
		titleColumn.setCellValueFactory(new PropertyValueFactory<Book, String>("title"));
		numberOfCopyColumn.setCellValueFactory(new PropertyValueFactory<Book, String>("copiesNumString"));
		
    }
}
