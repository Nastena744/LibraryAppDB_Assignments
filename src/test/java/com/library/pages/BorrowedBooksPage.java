package com.library.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class BorrowedBooksPage extends BasePage{


    @FindBy(xpath = "//tbody//td[2]")
    public List<WebElement> allBorrowedBooksName;

    @FindBy(xpath = "//a[@class=\"btn btn-primary btn-sm  \"]")
    public WebElement borrowBookButton;

    @FindBy(xpath = "//*[@id=\"navbarDropdown\"]/span")
    public WebElement accountProfileButtonRight;
}
