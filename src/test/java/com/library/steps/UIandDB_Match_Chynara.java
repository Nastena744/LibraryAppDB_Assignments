package com.library.steps;

import com.library.pages.BookPage;
import com.library.pages.DashBoardPage;
import com.library.pages.LoginPage;
import com.library.utility.BrowserUtil;
import com.library.utility.DB_Util;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UIandDB_Match_Chynara {
    String bookName;
    String actualDescription;
    BookPage bookPage=new BookPage();
    LoginPage loginPage=new LoginPage();

    DashBoardPage dashBoardPage=new DashBoardPage();

//    @Given("the {string} on the home page")
//    public void the_on_the_home_page(String string) {
//        loginPage.login(string);
//    }
//    @Given("the user navigates to {string} page")
//    public void the_user_navigates_to_page(String string) {
//       BrowserUtil.waitFor(1);
//        dashBoardPage.navigateModule(string);
//    }
    @When("the user searches for {string} book")
    public void the_user_searches_for_book(String name) {
        bookPage.search.click();
        bookPage.search.sendKeys(name);
        bookName=name;
    }
    @When("the user clicks edit book button")
    public void the_user_clicks_edit_book_button() {
        bookPage.editBook(bookName).click();

    }
    @Then("book information must match the Database")
    public void book_information_must_match_the_database() {

        DB_Util.runQuery("select * from books where name='Clean Code'");
        //get UI bookName, ISBN, year, Author, BookCategory, Description
        //DB exmaple:3809,Clean Code,09112021,2021,Robert C.Martin,10,Try it is free

        String actualBookName = bookPage.bookName.getAttribute("value");
        String actualISBN = bookPage.isbn.getAttribute("value");
        String actualYear = bookPage.year.getAttribute("value");
        String actualAuthor = bookPage.author.getAttribute("value");
        Select select = new Select(bookPage.categoryDropdown);
        String actualCategory = select.getFirstSelectedOption().getText();
        actualDescription = bookPage.description.getText();


        ArrayList<String> actualBookInfo = new ArrayList<>();
        actualBookInfo.addAll(Arrays.asList(actualBookName, actualISBN, actualYear, actualAuthor, actualCategory, actualDescription));
        System.out.println("actualBookInfo = " + actualBookInfo);
        //bookName
        // String query1="select name,isbn, year, author, book_category_id, description from books where name='Clean Code'";

        DB_Util.runQuery("select books.name,isbn,year,author,bb.name as \"genre\",books.description" +
                " from books inner join book_categories bb on books.book_category_id = bb.id " +
                "where books.name= " + "\'" + bookName + "\'and books.description= " + "\'" + actualDescription + "\'");

        List<String> expectedBookInfo = DB_Util.getRowDataAsList(1);
        System.out.println("expectedBookInfo = " + expectedBookInfo);
        Assert.assertEquals(expectedBookInfo, actualBookInfo);

    }
}
