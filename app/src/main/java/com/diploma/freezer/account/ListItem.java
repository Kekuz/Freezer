package com.diploma.freezer.account;

public class ListItem {
    private String listItemFirst;
    private String listItemSecond;

    public ListItem(){}//Это убирать запрещено!!!

    public ListItem(String listItemFirst, String listItemSecond) {
        this.listItemFirst = listItemFirst;
        this.listItemSecond = listItemSecond;
    }


    public String getListItemFirst() {
        return listItemFirst;
    }

    public void setListItemFirst(String listItemFirst) {
        this.listItemFirst = listItemFirst;
    }

    public String getListItemSecond() {
        return listItemSecond;
    }

    public void setListItemSecond(String listItemSecond) {
        this.listItemSecond = listItemSecond;
    }
}
