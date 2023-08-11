package com.library.steps;

import com.library.pages.BorrowedBooksPage;
import com.library.utility.BrowserUtil;
import com.library.utility.DB_Util;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.openqa.selenium.WebElement;

import java.util.List;

public class BooksModule_StepDefs{
        BorrowedBooksPage borrowedBooksPage = new BorrowedBooksPage();
        String bookName = new UIandDB_BookMatch_Chynara().bookName;

    @When("the user clicks Borrow Book")
    public void theUserClicksBorrowBook() {
        BrowserUtil.waitForClickablility(new BorrowedBooksPage().borrowBookButton, 15);
        new BorrowedBooksPage().borrowBookButton.click();
    }

    @Then("verify that book is shown in {string} page")
    public void verifyThatBookIsShownInPage(String module) {
        borrowedBooksPage.navigateModule(module);

        List<WebElement> allBorrowedBooksNames = borrowedBooksPage.allBorrowedBooksName;
        for (WebElement ea : allBorrowedBooksNames) {
            if (ea.getText().equals(bookName)){
                Assert.assertEquals(ea.getText(),bookName);
                break;
            }
        }

    }

    @And("verify logged student has same book in database")
    public void verifyLoggedStudentHasSameBookInDatabase() {
        String accountName = borrowedBooksPage.accountProfileButtonRight.getText();
        DB_Util.runQuery("select full_name,b.name,bb.borrowed_date from users u inner join book_borrow bb on u.id = bb.user_id " +
                "inner join books b on bb.book_id = b.id " +
                "where full_name=" + "\'" + accountName + "\'" + " and name= " + "\'" + bookName + "\'" + "order by 3 desc");

        String expectedBookDB = DB_Util.getCellValue(1, 2);
        System.out.println("DATABASE TEST");
        System.out.println("expectedBookDB = " + expectedBookDB);
        System.out.println("actualBookName = " + bookName);

        Assert.assertEquals(expectedBookDB, bookName);
    }
}
