package com.library.steps;

import com.library.pages.BookPage;
import com.library.pages.DashBoardPage;
import com.library.utility.BrowserUtil;
import com.library.utility.DB_Util;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import java.util.Map;

public class bookInformationStepDef {

    DashBoardPage dashBoardPage=new DashBoardPage();
    BookPage bookPage=new BookPage();
    String bookName;

    @Given("the user navigates to {string} page")
    public void the_user_navigates_to_page(String book) {
        dashBoardPage.navigateModule(book);



    }
    @When("the user searches for {string} book")
    public void the_user_searches_for_book(String bookName) {
        BrowserUtil.waitFor(2);
        this.bookName=bookName;
        bookPage.search.sendKeys(bookName);




    }
    @When("the user clicks edit book button")
    public void the_user_clicks_edit_book_button() {
        BrowserUtil.waitFor(2);
        bookPage.editBook(bookName).click();



    }
    @Then("book information must match the Database")
    public void book_information_must_match_the_database() {
        String actualBookName = bookPage.bookName.getAttribute("value");
        String actualAuthorName = bookPage.author.getAttribute("value");
        String actualISBN = bookPage.isbn.getAttribute("value");
        String actualYear = bookPage.year.getAttribute("value");
        DB_Util.runQuery("select * from books where name='"+bookName+"'");
        Map<String,String> Bookinfo=DB_Util.getRowMap(1);
        System.out.println(Bookinfo);
        String expectedBookName = Bookinfo.get("name");
        String expectedAuthorName =Bookinfo.get("author");
        String expectedISBN = Bookinfo.get("isbn");
        String expectedYear = Bookinfo.get("year");

        BrowserUtil.waitFor(3);
        Assert.assertEquals(actualBookName,expectedBookName);
        Assert.assertEquals(actualAuthorName,expectedAuthorName);

        Assert.assertEquals(actualISBN,expectedISBN);
        Assert.assertEquals(actualYear,expectedYear);




    }


}


