package subbook.subbook;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;


/**
 * Created by minci on 2018-02-04.
 * class Subscription manages single subscription and various methods relating to the subscription
 */

public class Subscription implements Serializable {

    private String name;
    private float price;
    private String date;
    private String comment;

    /* initialize class */
    public Subscription(){

        this.name = null;
        this.price = 0;
        this.date = null;
    }

    /* constructor */
    public Subscription(String name, float price, String date, String comment){
        setName(name);
        setPrice(price);
        setDate(date);
        setComment(comment);
    }

    /* method to set name for subscription */
    public void setName(String name){
        this.name = name;
    }

    /* method to set date for subscription */
    public void setDate(String date){
        this.date = date;
    }

    /* method to set price for subscription */
    public void setPrice(float price){
        this.price = price;
    }

    /* method to set comment,if there's one, for subscription */
    public void setComment(String comment){
        this.comment = comment;
    }

    /* method to get subscription name */
    public String getName() {
        return name;
    }

    /* method to get start date */
    public String getDate() {
        return date;
    }

    /* method to get monthly charge */
    public float getPrice() {
        return price;
    }

    /* method to get comment */
    public String getComment() {
        return comment;
    }

    /* Shows single subscription in formatted string on listview */
    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        return  "Name:\t\t" + this.name + "\n"   +
                "Date:    "+  this.date + "\t\t" + "Price: " + df.format(price) + " CAD";
    }
}
