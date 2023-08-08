package com.library.steps;

import com.library.pages.BookPage;
import com.library.pages.LoginPage;
import com.library.utility.BrowserUtil;
import com.library.utility.ConfigurationReader;
import com.library.utility.DB_Util;
import com.library.utility.Driver;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import java.time.Duration;
import java.util.List;

import java.awt.print.Book;
import java.util.ArrayList;

public class AddingBookStepDef_Mariya {
    LoginPage loginPage = new LoginPage();
    BookPage bookPage = new BookPage();
    String bookName,isbn,year,author,bookCategory;

    @Given("the {string} on the home page")
    public void the_on_the_home_page(String string) {
    loginPage.emailBox.sendKeys(ConfigurationReader.getProperty(string+"_username"));
    loginPage.passwordBox.sendKeys(ConfigurationReader.getProperty(string+"_password"));
    loginPage.loginButton.click();
    }
    @Given("the user navigates to {string} page")
    public void the_user_navigates_to_page(String page) {
       BrowserUtil.waitForPageToLoad(5);
        bookPage.navigateModule(page);
    }
    @When("the librarian click to add book")
    public void the_librarian_click_to_add_book() {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(),10);
        wait.until(ExpectedConditions.visibilityOf(bookPage.addBook));
        bookPage.addBook.click();
    }
    @When("the librarian enter book name {string}")
    public void the_librarian_enter_book_name(String bookName) {
        this.bookName=bookName;
    bookPage.bookName.sendKeys(bookName);
    }
    @When("the librarian enter ISBN {string}")
    public void the_librarian_enter_isbn(String isbn) {
        this.isbn=isbn;
    bookPage.isbn.sendKeys(isbn);
    }
    @When("the librarian enter year {string}")
    public void the_librarian_enter_year(String year) {
        this.year= year;
    bookPage.year.sendKeys(year);
    }
    @When("the librarian enter author {string}")
    public void the_librarian_enter_author(String author) {
        this.author = author;
    bookPage.author.sendKeys(author);
    }
    @When("the librarian choose the book category {string}")
    public void the_librarian_choose_the_book_category(String bookCategory) {
        this.bookCategory = bookCategory;
    Select drpDwn = new Select(bookPage.categoryDropdown);
    drpDwn.selectByVisibleText(bookCategory);
    }
    @When("the librarian click to save changes")
    public void the_librarian_click_to_save_changes() {
    bookPage.saveChanges.click();
    }
    @Then("verify {string} message is displayed")
    public void verify_message_is_displayed(String message) {
      /*  WebDriverWait wait = new WebDriverWait(Driver.getDriver(),10);
        wait.until(ExpectedConditions.visibilityOf(bookPage.toastMessage));*/
       // BrowserUtil.waitFor(5);
        Assert.assertEquals(bookPage.toastMessage.getText(),message);
    }
    @Then("verify {string} information must match with DB")
    public void verify_information_must_match_with_db(String string) {
       DB_Util.createConnection(ConfigurationReader.getProperty("library2.db.url"), ConfigurationReader.getProperty("library2.db.username"), ConfigurationReader.getProperty("library2.db.password"));

        DB_Util.runQuery("select b.name,isbn,year, author,bc.name\n" +
                "from books b\n" +
                "join book_categories bc on b.book_category_id = bc.id\n" +
                "where b.name = '"+string+"'\n" +
                "order by b.id desc;\n");

        List<String> dataList = new ArrayList<>();
        dataList=DB_Util.getRowDataAsList(1);

        Assert.assertEquals(bookName,dataList.get(0));
        Assert.assertEquals(isbn,dataList.get(1));
        Assert.assertEquals(year,dataList.get(2));
        Assert.assertEquals(author,dataList.get(3));
        Assert.assertEquals(bookCategory,dataList.get(4));

        DB_Util.destroy();


    }

}
