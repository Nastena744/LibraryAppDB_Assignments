package com.library.steps;

import com.library.pages.BasePage;
import com.library.pages.BookPage;
import com.library.pages.LoginPage;
import com.library.utility.BrowserUtil;
import com.library.utility.DB_Util;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import java.util.List;
import java.util.Map;

public class FatimaStepDef extends BasePage {
    LoginPage loginPage = new LoginPage();
    Map<String, String> actualBookinfo;
    String expectedAuthor;
    String expectedCategory;
    String expectedBookName;
    String expectedISBN;
    String expectedYear;

    @Given("the {string} on the home page")
    public void the_on_the_home_page(String string) {
        loginPage.login(string);
    }

    @Given("the user navigates to {string} page")
    public void the_user_navigates_to_page(String string) {
        navigateModule("Books");
    }

    BookPage bookPage = new BookPage();

    @When("the librarian click to add book")
    public void the_librarian_click_to_add_book() {

        BrowserUtil.waitFor(1);
        bookPage.addBook.click();
    }

    @When("the librarian enter book name {string}")
    public void the_librarian_enter_book_name(String BookName) {

        bookPage.bookName.sendKeys(BookName);
        expectedBookName = BookName;

    }

    @When("the librarian enter ISBN {string}")
    public void the_librarian_enter_isbn(String isbn) {

        BrowserUtil.waitFor(3);
        bookPage.isbn.sendKeys(isbn);
        expectedISBN = isbn;
    }

    @When("the librarian enter year {string}")
    public void the_librarian_enter_year(String Year) {
        bookPage.year.sendKeys(Year);
        expectedYear = Year ;
    }

    @When("the librarian enter author {string}")
    public void the_librarian_enter_author(String AuthorName) {

        bookPage.author.sendKeys(AuthorName);
        expectedAuthor = AuthorName;

    }

    @When("the librarian choose the book category {string}")
    public void the_librarian_choose_the_book_category(String categoryDropdown) {
        bookPage.categoryDropdown.sendKeys(categoryDropdown);
        expectedCategory = categoryDropdown;
    }

    @When("the librarian click to save changes")
    public void the_librarian_click_to_save_changes() {
        BrowserUtil.waitFor(2);
        bookPage.saveChanges.click();
    }

    @Then("verify {string} message is displayed")
    public void verify_message_is_displayed(String expectedResult) {

        String actualResult=  bookPage.toastMessage.getText();
        Assert.assertEquals(expectedResult,actualResult);
    }

    @Then("verify {string} information must match with DB")
    public void verify_information_must_match_with_db(String string) {

        DB_Util.runQuery("select b.name , isbn , year , author , bc.name as CategoryName from books b left join book_categories bc on b.book_category_id = bc.id where b.name = '"+string+"'");


        List<String> actualBookInfos = DB_Util.getRowDataAsList(1);
        System.out.println(DB_Util.getRowDataAsList(1));
        String actualBookName = actualBookInfos.get(0);
        String actualIsbn =  actualBookInfos.get(1);
        String actualYear = actualBookInfos.get(2);
        String actualAuthor =  actualBookInfos.get(3);
        String actualCategory = actualBookInfos.get(4);



        Assert.assertEquals(expectedBookName,actualBookName);
        Assert.assertEquals(expectedISBN,actualIsbn);
        Assert.assertEquals(expectedYear,actualYear);
        Assert.assertEquals(expectedAuthor,actualAuthor);
        Assert.assertEquals(expectedCategory,actualCategory);

    }

}
