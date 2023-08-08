package com.library.steps;

import com.library.utility.BrowserUtil;
import com.library.utility.DB_Util;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

public class GenreOfBooksStepDef {
    String actualMostPopularGenre;

    @When("I execute query to find most popular book genre_ana")
    public void i_execute_query_to_find_most_popular_book_genre_ana() {
        DB_Util.runQuery("select bc.name, count(*) from book_borrow bb inner join books b on bb.book_id = b.id inner join book_categories bc on b.book_category_id = bc.id group by name order by 2 desc");
        BrowserUtil.waitFor(2);
        actualMostPopularGenre = DB_Util.getFirstRowFirstColumn();
    }
    @Then("verify {string} is the most popular book genre_ana")
    public void verify_is_the_most_popular_book_genre_ana(String mpBook) {
        String expectedMostPopularGenre = mpBook;
        System.out.println("expectedMostPopularGenre = " + expectedMostPopularGenre);
        System.out.println("actualMostPopularGenre = " + actualMostPopularGenre);
        Assert.assertEquals(expectedMostPopularGenre, actualMostPopularGenre);


    }

}
