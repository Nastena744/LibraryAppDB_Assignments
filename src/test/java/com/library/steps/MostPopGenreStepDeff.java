package com.library.steps;

import com.library.utility.DB_Util;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

public class MostPopGenreStepDeff {

    String actualMostPopBookGenre;

    @When("Verify user can execute query to find most popular book genre")
    public void verifyUserCanExecuteQueryToFindMostPopularBookGenre() {
        DB_Util.runQuery("select bc.name,count(*) from book_borrow bb\n" +
                "inner  join books b on bb.book_id = b.id\n" +
                "inner join book_categories bc on b.book_category_id=bc.id\n" +
                "group by name order by 2 desc\n" +
                "limit 1");
        actualMostPopBookGenre = DB_Util.getFirstRowFirstColumn();
        System.out.println("actualMostPopBookGenre = " + actualMostPopBookGenre);
    }

    @Then("Verify user can see {string} as the most popular book genre.")
    public void verifyUserCanSeeAsTheMostPopularBookGenre(String expectedMostPopBookGenre) {
        System.out.println("expectedMostPopBookGenre = " + expectedMostPopBookGenre);
        Assert.assertEquals(expectedMostPopBookGenre, actualMostPopBookGenre);
    }
}
